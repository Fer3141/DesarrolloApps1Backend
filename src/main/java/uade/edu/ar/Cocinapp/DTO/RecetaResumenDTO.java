package uade.edu.ar.Cocinapp.DTO;

// dto usado para mostrar un resumen de cada receta en listados o cards del frontend
// incluye solo los datos necesarios: id, nombre, imagen y autor

public class RecetaResumenDTO {
    public Long idReceta;              // id de la receta
    public String nombreReceta;        // nombre del plato
    public String fotoPrincipal;       // url de la imagen
    public String nombreUsuario;       // nickname del autor

    public RecetaResumenDTO(Long id, String nombre, String foto, String usuario) {
        this.idReceta = id;
        this.nombreReceta = nombre;
        this.fotoPrincipal = foto;
        this.nombreUsuario = usuario;
    }
}
