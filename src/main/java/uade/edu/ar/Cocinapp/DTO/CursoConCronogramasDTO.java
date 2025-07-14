package uade.edu.ar.Cocinapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoConCronogramasDTO {
    private Long idCurso;
    private String descripcion;
    private String contenidos;
    private String requerimientos;
    private int duracion;
    private double precio;
    private String modalidad;
    private List<CronogramaDTO> cronogramas;
}
