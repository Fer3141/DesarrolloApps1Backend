package uade.edu.ar.Cocinapp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CronogramaDTO {
    private Long idCurso;
    private Long idSede;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int vacantesDisponibles;
}
