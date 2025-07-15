package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.InscripcionCurso;

import java.util.List;
import java.util.Optional;

public interface InscripcionCursoRepository extends JpaRepository<InscripcionCurso, Long> {
    List<InscripcionCurso> findByAlumno_IdUsuario(Long idUsuario);
    boolean existsByAlumno_IdUsuarioAndCronograma_IdCronograma(Long idUsuario, Long idCronograma);
    Optional<InscripcionCurso> findByAlumno_IdUsuarioAndCronograma_IdCronograma(Long idAlumno, Long idCronograma);
    List<InscripcionCurso> findByAlumno_IdUsuarioAndCronograma_Curso_IdCurso(Long idAlumno, Long idCurso);


}
