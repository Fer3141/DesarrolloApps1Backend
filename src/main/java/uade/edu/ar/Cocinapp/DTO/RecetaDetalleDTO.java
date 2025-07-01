package uade.edu.ar.Cocinapp.DTO;

import java.util.List;

import uade.edu.ar.Cocinapp.Entidades.TipoReceta;

public class RecetaDetalleDTO {
    public Long idReceta;
    public String nombreReceta;
    public String descripcionReceta;
    public String fotoPrincipal;
    public int porciones;
    public int cantidadPersonas;
    public String nombreUsuario;
    public TipoReceta tipo;

    public List<IngredienteDTO> ingredientes;
    public List<PasoCompletoDTO> pasos;
}

