package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.Unidad;

import java.util.Optional;

public interface UnidadRepository extends JpaRepository<Unidad, Integer> {
    Optional<Unidad> findByDescripcionIgnoreCase(String descripcion);
}