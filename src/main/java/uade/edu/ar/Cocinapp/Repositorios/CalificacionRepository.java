package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.Calificacion;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
	
	
	List<Calificacion> findByAprobadoFalse();
	
}
