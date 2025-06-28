package uade.edu.ar.Cocinapp.DTO;

import java.util.List;

public class RecetaDetalleDTO {
    public Long idReceta;
    public String nombreReceta;
    public String descripcionReceta;
    public String fotoPrincipal;
    public int porciones;
    public int cantidadPersonas;
    public String nombreUsuario;
    public int idTipo;

    public List<IngredienteDTO> ingredientes;
    public List<PasoCompletoDTO> pasos;
}

