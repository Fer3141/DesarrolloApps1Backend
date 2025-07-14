package uade.edu.ar.Cocinapp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CronogramaDTO {
	
   
	private Long idCronograma;
    private Long idCurso;
    private Long idSede;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int vacantesDisponibles;
    
    private String qrId;
    private String nombreSede;
    private String direccionSede;
    private String horarioSede;
    private String tipoPromocion;
    private String promocionCursos;
    
    public CronogramaDTO(Long idCronograma, LocalDate fechaInicio, LocalDate fechaFin,
            String nombreSede, String direccionSede, int vacantesDisponibles, String qrId) {
		this.idCronograma = idCronograma;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.nombreSede = nombreSede;
		this.direccionSede = direccionSede;
		this.vacantesDisponibles = vacantesDisponibles;
		this.qrId = qrId;
}


    
    
    


}
