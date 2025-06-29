package uade.edu.ar.Cocinapp.DTO;

public class PerfilDTO {

	private String nombre;
    private String biografia;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getBiografia() {
		return biografia;
	}
	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
    
	public PerfilDTO(String nombre, String biografia) {
        this.nombre = nombre;
        this.biografia = biografia;
    }
	public PerfilDTO() {
		super();
	}
    
	
}
