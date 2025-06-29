package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.edu.ar.Cocinapp.DTO.CursoDisponibleDTO;
import uade.edu.ar.Cocinapp.DTO.CursoInscriptoDTO;
import uade.edu.ar.Cocinapp.Entidades.Alumno;
import uade.edu.ar.Cocinapp.Entidades.CronogramaCurso;
import uade.edu.ar.Cocinapp.Entidades.Curso;
import uade.edu.ar.Cocinapp.Entidades.InscripcionCurso;
import uade.edu.ar.Cocinapp.Repositorios.AlumnoRepository;
import uade.edu.ar.Cocinapp.Repositorios.CronogramaCursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.CursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.InscripcionCursoRepository;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    // devuelve todos los cursos disponibles con datos de curso, sede, fechas y promo
    public List<CursoDisponibleDTO> obtenerCursosDisponibles() {
        List<CronogramaCurso> cronos = cronogramaRepo.findAll();

        List<CursoDisponibleDTO> resultado = new ArrayList<>();
        for (CronogramaCurso c : cronos) {
            CursoDisponibleDTO dto = new CursoDisponibleDTO();
            dto.idCronograma = c.getIdCronograma();
            dto.descripcionCurso = c.getCurso().getDescripcion();
            dto.modalidad = c.getCurso().getModalidad();
            dto.sede = c.getSede().getNombreSede();
            dto.direccion = c.getSede().getDireccionSede();
            dto.fechaInicio = c.getFechaInicio();
            dto.fechaFin = c.getFechaFin();
            dto.vacantes = c.getVacantesDisponibles();

            // aplicar descuento de la sede si tiene promoción
            double base = c.getCurso().getPrecio();
            double descuento = c.getSede().getPromocionCursos(); // porcentaje
            dto.precioFinal = base - (base * descuento / 100);

            resultado.add(dto);
        }

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
        
        public void generarQRCode(String texto, String nombreArchivo) {
            try {
                String ruta = "qr-codes/" + nombreArchivo + ".png";
                int width = 300;
                int height = 300;

                BitMatrix matrix = new MultiFormatWriter()
                        .encode(texto, BarcodeFormat.QR_CODE, width, height);

                Path path = FileSystems.getDefault().getPath(ruta);
                MatrixToImageWriter.writeToPath(matrix, "PNG", path);

                System.out.println("QR generado en: " + path.toAbsolutePath());
            } catch (Exception e) {
                throw new RuntimeException("Error al generar QR", e);
            }
        }
        
        
        @Transactional
        public Curso crearCurso(Curso curso, CronogramaCurso crono) {
        	generarQRCode("curso-id:" + curso.getIdCurso(), "qr-curso-" + crono.getIdCronograma());
            return cursoRepo.save(curso);
        }

        
}

