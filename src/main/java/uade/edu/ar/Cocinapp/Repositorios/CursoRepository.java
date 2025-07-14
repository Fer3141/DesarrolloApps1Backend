package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uade.edu.ar.Cocinapp.Entidades.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	// maria 
	@Query("SELECT c FROM Curso c LEFT JOIN FETCH c.cronogramas WHERE c.idCurso = :id")
    Curso buscarCursoConCronogramas(@Param("id") Long id);
}
