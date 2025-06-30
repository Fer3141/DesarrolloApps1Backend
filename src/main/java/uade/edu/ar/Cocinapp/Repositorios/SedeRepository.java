package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.edu.ar.Cocinapp.Entidades.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {
	List<Sede> findByNombreSede(String nombre);
	Optional<Sede> findById(Long id);
}