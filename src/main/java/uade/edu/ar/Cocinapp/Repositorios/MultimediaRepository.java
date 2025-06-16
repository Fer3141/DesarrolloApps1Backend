package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.Multimedia;

public interface MultimediaRepository extends JpaRepository<Multimedia, Integer> {
}