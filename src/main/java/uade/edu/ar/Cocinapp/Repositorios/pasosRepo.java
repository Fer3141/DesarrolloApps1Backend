package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uade.edu.ar.Cocinapp.Entidades.Paso;

@Repository
public interface pasosRepo extends JpaRepository<Paso, Long>{
    List<Paso> findByReceta_IdRecetaOrderByNroPasoAsc(Long idReceta);

    void deleteByReceta_IdReceta(Long idReceta);
}
