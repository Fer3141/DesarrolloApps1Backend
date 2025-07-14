package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idIngrediente;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idReceta", nullable = false)
    private Receta receta;
}