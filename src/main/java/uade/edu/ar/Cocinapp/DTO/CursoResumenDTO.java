package uade.edu.ar.Cocinapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CursoResumenDTO {
    private Long idCurso;
    private String descripcion;
    private String modalidad;
    private int duracion;
    private double precio;
}
