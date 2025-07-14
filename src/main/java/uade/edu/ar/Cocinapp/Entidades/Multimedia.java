package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContenido;

    @ManyToOne
    @JoinColumn(name = "idPaso")
    private Paso paso;

    private String tipo_contenido;
    private String extension;
    private String urlContenido;
}