package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sedes")
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSede;

    private String nombreSede;
    private String direccionSede;
    private String telefonoSede;
    private String mailSede;
    private String whatsApp;

    // tipo de bonificación (por ejemplo descuento o promo)
    private String tipoBonificacion;
    private double bonificacionCursos;

    private String tipoPromocion;
    private double promocionCursos;
}

