package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Utilizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUtilizado;

    @ManyToOne
    @JoinColumn(name = "idReceta")
    private recetas receta;

    @ManyToOne
    @JoinColumn(name = "idIngrediente")
    private Ingrediente ingrediente;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "idUnidad")
    private Unidad unidad;

    private String observaciones;
}