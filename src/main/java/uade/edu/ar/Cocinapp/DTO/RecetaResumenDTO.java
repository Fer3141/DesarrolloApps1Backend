package uade.edu.ar.Cocinapp.DTO;

// dto usado para mostrar un resumen de cada receta en listados o cards del frontend
// incluye solo los datos necesarios: id, nombre, imagen y autor

public class RecetaResumenDTO {
    public Long idReceta;              // id de la receta
    public String nombreReceta;        // nombre del plato
    public String fotoPrincipal;       // url de la imagen
    public String nombreUsuario;       // nickname del autor
    public double promedio; // nuevo campo para que moestremos el promedio de las valoraciones en el feed
    public String estado; // es opcional porque solo lo usamos en el metodo para traer las recetas del usuario, y que pueda ver en que estado estan

    // Constructor original (usado en feed )
    public RecetaResumenDTO(Long idReceta, String nombreReceta, String fotoPrincipal,
                            String nombreUsuario, double promedio) {
        this(idReceta, nombreReceta, fotoPrincipal, nombreUsuario, promedio, null);
    }
    
    // contructor para mis recetas
    public RecetaResumenDTO(Long id, String nombre, String foto, String usuario, double promedio, String estado) {
        this.idReceta = id;
        this.nombreReceta = nombre;
        this.fotoPrincipal = foto;
        this.nombreUsuario = usuario;
        this.promedio = promedio;
        this.estado = estado;
    }
}
