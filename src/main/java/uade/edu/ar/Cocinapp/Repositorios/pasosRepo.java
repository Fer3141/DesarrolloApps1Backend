package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uade.edu.ar.Cocinapp.Entidades.pasos;

@Repository
public interface pasosRepo extends JpaRepository<pasos, Long>{
    List<pasos> findByReceta_IdRecetaOrderByNroPasoAsc(Long idReceta);
}
