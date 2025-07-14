package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InscripcionCurso { //no estaba en el SQL, la agregamos 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInscripcion;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idCronograma")
    private CronogramaCurso cronograma;
    
    @ManyToOne
    @JoinColumn(name = "idAlumno")
    private Alumno alumno;
}
