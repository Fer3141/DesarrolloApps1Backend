package uade.edu.ar.Cocinapp.DTO;

// dto para mostrar una calificacion, por ej cuando se abre una receta se tiene que ver abajo toda la seccion de comentarios y valroaciones
public class CalificacionVistaDTO {
    public String aliasUsuario;
    public int calificacion;
    public String comentario;
    private Boolean aprobado;
    private String nombreReceta;
    private Long idCalificacion;

    public CalificacionVistaDTO(String aliasUsuario, int calificacion, String comentario,
            boolean aprobado, String nombreReceta, Long idCalificacion) {
	this.aliasUsuario = aliasUsuario;
	this.calificacion = calificacion;
	this.comentario = comentario;
	this.aprobado = aprobado;
	this.nombreReceta = nombreReceta;
	this.idCalificacion = idCalificacion;
	}
    
    public CalificacionVistaDTO(String aliasUsuario, int calificacion, String comentario, boolean aprobado) {
        this.aliasUsuario = aliasUsuario;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.aprobado = aprobado;
    }
	
	// Getters
	public String getAliasUsuario() { return aliasUsuario; }
	public int getCalificacion() { return calificacion; }
	public String getComentario() { return comentario; }
	public boolean isAprobado() { return aprobado; }
	public String getNombreReceta() { return nombreReceta; }
	public Long getIdCalificacion() { return idCalificacion; }
	}




