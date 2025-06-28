package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.Multimedia;

public interface MultimediaRepository extends JpaRepository<Multimedia, Integer> {
    List<Multimedia> findByPaso_IdPaso(Long idPaso);
}