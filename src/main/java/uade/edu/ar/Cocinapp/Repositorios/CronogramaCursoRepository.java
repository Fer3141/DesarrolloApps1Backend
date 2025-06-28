package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.CronogramaCurso;

import java.util.List;

public interface CronogramaCursoRepository extends JpaRepository<CronogramaCurso, Long> {

    // devuelve todos los cronogramas disponibles en una sede específica
    List<CronogramaCurso> findBySede_IdSede(Long idSede);
}

