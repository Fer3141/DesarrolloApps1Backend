package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.edu.ar.Cocinapp.Entidades.alumnos;

@Repository
public interface alumnosRepo extends JpaRepository<alumnos, Long>{

}
