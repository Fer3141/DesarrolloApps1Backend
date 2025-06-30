package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.edu.ar.Cocinapp.DTO.*;
import uade.edu.ar.Cocinapp.Entidades.*;
import uade.edu.ar.Cocinapp.Repositorios.*;

import java.time.LocalDate;

@Service
public class AdminService {

    @Autowired
    private SedeRepository sedeRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private CronogramaCursoRepository cronogramaRepo;

    // guarda una sede nueva
    public void crearSede(SedeNuevaDTO dto) {
        Sede sede = new Sede();
        sede.setNombreSede(dto.nombreSede);
        sede.setDireccionSede(dto.direccionSede);
        sede.setTelefonoSede(dto.telefonoSede);
        sede.setMailSede(dto.mailSede);
        sede.setWhatsApp(dto.whatsApp);
        sede.setTipoBonificacion(dto.tipoBonificacion);
        sede.setBonificacionCursos(dto.bonificacionCursos);
        sede.setTipoPromocion(dto.tipoPromocion);
        sede.setPromocionCursos(dto.promocionCursos);
        sedeRepo.save(sede);
        System.out.println("sede guardada");
    }

    // guarda un curso nuevo
    public void crearCurso(CursoNuevoDTO dto) {
        Curso curso = new Curso();
        curso.setDescripcion(dto.descripcion);
        curso.setContenidos(dto.contenidos);
        curso.setRequerimientos(dto.requerimientos);
        curso.setDuracion(dto.duracion);
        curso.setPrecio(dto.precio);
        curso.setModalidad(dto.modalidad);
        cursoRepo.save(curso);
        System.out.println("curso guardado");
    }

    // guarda un cronograma para un curso en una sede
    public void crearCronograma(CronogramaNuevoDTO dto) {
        Sede sede = sedeRepo.findById(dto.idSede).orElseThrow(() -> new RuntimeException("sede no encontrada"));
        Curso curso = cursoRepo.findById(dto.idCurso).orElseThrow(() -> new RuntimeException("curso no encontrado"));

        CronogramaCurso cronograma = new CronogramaCurso();
        cronograma.setSede(sede);
        cronograma.setCurso(curso);
        cronograma.setFechaInicio(LocalDate.parse(dto.fechaInicio));
        cronograma.setFechaFin(LocalDate.parse(dto.fechaFin));
        cronograma.setVacantesDisponibles(dto.vacantesDisponibles);
        cronogramaRepo.save(cronograma);
        System.out.println("cronograma guardado");
    }
}
