package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uade.edu.ar.Cocinapp.Entidades.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    
}
