package uade.edu.ar.Cocinapp.DTO;

// dto para mostrar una calificacion, por ej cuando se abre una receta se tiene que ver abajo toda la seccion de comentarios y valroaciones
public class CalificacionVistaDTO {
    public String aliasUsuario;
    public int calificacion;
    public String comentario;

    public CalificacionVistaDTO(String aliasUsuario, int calificacion, String comentario) {
        this.aliasUsuario = aliasUsuario;
        this.calificacion = calificacion;
        this.comentario = comentario;
    }
}
