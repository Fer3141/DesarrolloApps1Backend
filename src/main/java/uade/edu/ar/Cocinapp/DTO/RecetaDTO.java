package uade.edu.ar.Cocinapp.DTO;

import java.util.List;

public class RecetaDTO {
    public String nombreReceta; // nombre del plato
    public String descripcionReceta; // descripcion general
    public int porciones; // cantidad de porciones
    public int cantidadPersonas; // cuantas personas rinde
    public int idTipo; // id de tipo de receta (ya predefinidos en la tabla tiposReceta)
    public String fotoPrincipal; // url de la imagen principal
    public int idUsuario; // id del usuario que la crea

    public List<IngredienteDTO> ingredientes; // lista de ingredientes usados
    public List<PasoCompletoDTO> pasos; // pasos con multimedia
}
