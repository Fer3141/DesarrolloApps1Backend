package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.edu.ar.Cocinapp.DTO.CursoDisponibleDTO;
import uade.edu.ar.Cocinapp.DTO.CursoInscriptoDTO;
import uade.edu.ar.Cocinapp.Entidades.Alumno;
import uade.edu.ar.Cocinapp.Entidades.AsistenciaCurso;
import uade.edu.ar.Cocinapp.Entidades.CronogramaCurso;
import uade.edu.ar.Cocinapp.Entidades.Curso;
import uade.edu.ar.Cocinapp.Entidades.InscripcionCurso;
import uade.edu.ar.Cocinapp.Entidades.Sede;
import uade.edu.ar.Cocinapp.Repositorios.AlumnoRepository;
import uade.edu.ar.Cocinapp.Repositorios.AsistenciaCursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.CronogramaCursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.CursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.InscripcionCursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.MultimediaRepository;
import uade.edu.ar.Cocinapp.Repositorios.SedeRepository;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@Service
public class CursoService {
	
	@Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private CronogramaCursoRepository cronogramaRepo;

    @Autowired
    private AlumnoRepository alumnoRepo;

    @Autowired
    private InscripcionCursoRepository inscripcionRepo;
    
    
    @Autowired
    private SedeRepository sedeRepository;
    
    
    @Autowired
    private AsistenciaCursoRepository acr;
    

    // devuelve todos los cursos disponibles con datos de curso, sede, fechas y promo
    public List<CursoDisponibleDTO> obtenerCursosDisponibles() {
        List<CronogramaCurso> cronos = cronogramaRepo.findAllWithCursoAndSede();

        List<CursoDisponibleDTO> resultado = new ArrayList<>();
        for (CronogramaCurso c : cronos) {
            if (c.getCurso() == null || c.getSede() == null) {
                System.out.println("⚠️ Cronograma sin curso o sede: id=" + c.getIdCronograma());
                continue; // salta este cronograma
            }

            CursoDisponibleDTO dto = new CursoDisponibleDTO();
            dto.idCronograma = c.getIdCronograma();
            dto.descripcionCurso = c.getCurso().getDescripcion();
            dto.modalidad = c.getCurso().getModalidad();
            dto.sede = c.getSede().getNombreSede();
            dto.direccion = c.getSede().getDireccionSede();
            dto.fechaInicio = c.getFechaInicio();
            dto.fechaFin = c.getFechaFin();
            dto.vacantes = c.getVacantesDisponibles();

            double base = c.getCurso().getPrecio();
            double descuento = c.getSede().getPromocionCursos(); // %
            dto.precioFinal = base - (base * descuento / 100);

            resultado.add(dto);
        }

        System.out.println("Cursos disponibles:");
        resultado.forEach(c -> System.out.println(c.descripcionCurso + " - " + c.sede));
        return resultado;
    }


    @Transactional
    public void inscribirseACurso(Long idAlumno, Long idCronograma) {
        if (inscripcionRepo.existsByAlumno_IdUsuarioAndCronograma_IdCronograma(idAlumno, idCronograma)) {
            throw new RuntimeException("el alumno ya está inscripto en ese curso");
        }

        Alumno alumno = alumnoRepo.findById(idAlumno).orElseThrow(() -> new RuntimeException("alumno no encontrado"));
        CronogramaCurso cronograma = cronogramaRepo.findById(idCronograma).orElseThrow(() -> new RuntimeException("cronograma no encontrado"));

        if (cronograma.getVacantesDisponibles() <= 0) {
            throw new RuntimeException("no hay vacantes disponibles");
        }

        // crear inscripción
        InscripcionCurso inscripcion = new InscripcionCurso();
        inscripcion.setAlumno(alumno);
        inscripcion.setCronograma(cronograma);

        inscripcionRepo.save(inscripcion);

        // descontar una vacante
        cronograma.setVacantesDisponibles(cronograma.getVacantesDisponibles() - 1);
        cronogramaRepo.save(cronograma);
        }

        public List<CursoInscriptoDTO> obtenerCursosInscripto(Long idAlumno) {
        List<InscripcionCurso> inscripciones = inscripcionRepo.findByAlumno_IdUsuario(idAlumno);

        List<CursoInscriptoDTO> resultado = new ArrayList<>();
        for (InscripcionCurso i : inscripciones) {
            CronogramaCurso c = i.getCronograma();

            CursoInscriptoDTO dto = new CursoInscriptoDTO();
            dto.descripcionCurso = c.getCurso().getDescripcion();
            dto.modalidad = c.getCurso().getModalidad();
            dto.requerimientos = c.getCurso().getRequerimientos();
            dto.fechaInicio = c.getFechaInicio();
            dto.fechaFin = c.getFechaFin();
            dto.sede = c.getSede().getNombreSede();
            dto.direccion = c.getSede().getDireccionSede();

            // aplicar promo si la sede tiene
            double base = c.getCurso().getPrecio();
            double promo = c.getSede().getPromocionCursos();
            dto.precioFinal = base - (base * promo / 100);

            resultado.add(dto);
        }

        return resultado;
    }
        
        public Path generarQRCode(String texto, String nombreArchivo) {
            try {
                String ruta = "qr-codes/" + nombreArchivo + ".png";
                int width = 300;
                int height = 300;

                BitMatrix matrix = new MultiFormatWriter()
                        .encode(texto, BarcodeFormat.QR_CODE, width, height);

                Path path = FileSystems.getDefault().getPath(ruta);
                MatrixToImageWriter.writeToPath(matrix, "PNG", path);

                return path.toAbsolutePath();
            } catch (Exception e) {
                throw new RuntimeException("Error al generar QR", e);
            }
        }
        
        
        @Transactional
        public Curso crearCurso(Curso curso) {
            return cursoRepo.save(curso);
        }
        
        
        @Transactional
        public CronogramaCurso crearCronograma(Long idCurso, Long idSede, LocalDate fechaInicio, LocalDate fechaFin, int vacantes) {
            Curso curso = cursoRepo.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
            Sede sede = sedeRepository.findById(idSede)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
            
            CronogramaCurso cronograma = new CronogramaCurso();
            cronograma.setCurso(curso);
            cronograma.setSede(sede);
            cronograma.setFechaInicio(fechaInicio);
            cronograma.setFechaFin(fechaFin);
            cronograma.setVacantesDisponibles(vacantes);
            
         // ahora se genera el QR correspondiente
            String textoQR = "curso:" + curso.getIdCurso() + ",cronograma:" + cronograma.getIdCronograma();
            String nombreArchivo = "qr-curso-" + curso.getIdCurso() + "-crono-" + cronograma.getIdCronograma();
            Path p = generarQRCode(textoQR, nombreArchivo);
            
            //Acá añadir linea donde se pasa el objeto al web service y se obtiene el url
            
            byte[] bytes = null;
			try {
				bytes = Files.readAllBytes(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String base64 = Base64.getEncoder().encodeToString(bytes);
            cronograma.setQRid(p.toString());
            //Después ese URL se agrega en multimedia y se obtiene el nuevo ID
            //Long idMultimedia = mr.save(qr).getID();
            
            cronogramaRepo.save(cronograma);

            return cronograma;
        }

        @Transactional
        public void marcarAsistenciaPorQR(Long idAlumno, String qrContenido) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Integer> data = mapper.readValue(qrContenido, Map.class);

                Long idCurso = data.get("idCurso").longValue();
                Long idCronograma = data.get("idCronograma").longValue();

                // Verificar si está inscripto
                boolean inscripto = inscripcionRepo.existsByAlumno_IdUsuarioAndCronograma_IdCronograma(idAlumno, idCronograma);
                if (!inscripto) {
                    throw new RuntimeException("El alumno no está inscripto a ese curso/cronograma");
                }

                Alumno alumno = alumnoRepo.findById(idAlumno)
                    .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
                Curso curso = cursoRepo.findById(idCurso)
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
                CronogramaCurso cronograma = cronogramaRepo.findById(idCronograma)
                    .orElseThrow(() -> new RuntimeException("Cronograma no encontrado"));

                // Guardar asistencia
                AsistenciaCurso asistencia = new AsistenciaCurso();
                asistencia.setAlumno(alumno);
                asistencia.setCurso(curso);
                asistencia.setCronograma(cronograma);
                asistencia.setFechaHora(LocalDateTime.now());

                acr.save(asistencia);
            } catch (Exception e) {
                throw new RuntimeException("Error procesando el QR: " + e.getMessage(), e);
            }
        }
        
        

        
}

