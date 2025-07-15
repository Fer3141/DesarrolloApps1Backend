package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.AsistenciaCurso;

import java.time.LocalDateTime;
import java.util.List;

public interface AsistenciaCursoRepository extends JpaRepository<AsistenciaCurso, Long> {
    List<AsistenciaCurso> findByAlumno_IdUsuario(Long idUsuario);
    boolean existsByAlumno_IdUsuarioAndCronograma_IdCronograma(Long idAlumno, Long idCronograma);
    boolean existsByAlumno_IdUsuarioAndCronograma_IdCronogramaAndFechaHoraBetween(
    	    Long idAlumno,
    	    Long idCronograma,
    	    LocalDateTime inicio,
    	    LocalDateTime fin
    	);
}

