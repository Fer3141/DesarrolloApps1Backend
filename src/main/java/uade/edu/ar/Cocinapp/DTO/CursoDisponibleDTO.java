package uade.edu.ar.Cocinapp.DTO;

import java.time.LocalDate;

public class CursoDisponibleDTO {
    public Long idCronograma;
    public String descripcionCurso;
    public String modalidad;
    public String sede;
    public String direccion;
    public LocalDate fechaInicio;
    public LocalDate fechaFin;
    public int vacantes;
    public double precioFinal; // con promo incluida
}

