package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.Utilizado;

public interface UtilizadoRepository extends JpaRepository<Utilizado, Integer> {
    List<Utilizado> findByReceta_IdReceta(Long idReceta);

    void deleteByReceta_IdReceta(Long idReceta);
}