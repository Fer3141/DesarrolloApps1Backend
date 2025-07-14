package uade.edu.ar.Cocinapp.Entidades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uade.edu.ar.Cocinapp.DTO.CronogramaDTO;

@Entity
@Getter
@Setter
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCurso;

    // descripcion corta del curso
    private String descripcion;

    // temario o contenidos que se van a ver
    private String contenidos;

    // qué tiene que traer el alumno o qué se necesita
    private String requerimientos;

    // duración en cantidad de clases (en int)
    private int duracion;

    // precio base del curso
    private double precio;


    //Nota de Antonia: Para mí la modalidad se deberia establecer en el cronograma
    // modalidad: presencial, remoto o virtual
    @Column(columnDefinition = "varchar(20)")
    private String modalidad;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY) 
    private List<CronogramaCurso> cronogramas;


	
}
