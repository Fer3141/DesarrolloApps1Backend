package uade.edu.ar.Cocinapp.Servicios;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import uade.edu.ar.Cocinapp.Entidades.Alumno;
import uade.edu.ar.Cocinapp.Entidades.CronogramaCurso;
import uade.edu.ar.Cocinapp.Entidades.Curso;
import uade.edu.ar.Cocinapp.Entidades.AsistenciaCurso;
import uade.edu.ar.Cocinapp.Repositorios.AlumnoRepository;
import uade.edu.ar.Cocinapp.Repositorios.AsistenciaCursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.CronogramaCursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.CursoRepository;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciaCursoRepository asistenciaRepo;
    @Autowired
    private AlumnoRepository alumnoRepo;
    @Autowired
    private CursoRepository cursoRepo;
    @Autowired
    private CronogramaCursoRepository cronogramaRepo;

    @Transactional
    public void marcarAsistencia(Long idAlumno, Long idCurso, Long idCronograma) {
        Alumno alumno = alumnoRepo.findById(idAlumno)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        Curso curso = cursoRepo.findById(idCurso)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        CronogramaCurso cronograma = cronogramaRepo.findById(idCronograma)
            .orElseThrow(() -> new RuntimeException("Cronograma no encontrado"));

        AsistenciaCurso asistencia = new AsistenciaCurso();
        asistencia.setAlumno(alumno);
        asistencia.setCurso(curso);
        asistencia.setCronograma(cronograma);
        asistencia.setFechaHora(LocalDateTime.now());

        asistenciaRepo.save(asistencia);
    }
}

