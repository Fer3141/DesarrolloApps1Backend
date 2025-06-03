package uade.edu.ar.Cocinapp.DTO;

import java.util.List;

public class RecetaDTO {
	
	private Long idUsuario;
    private String nombre;
    private String descripcion;
    private String fotoPrincipal;
    private int porciones;
    private int cantidadPersonas;
    private int idTipo;
    private List<PasoDTO> pasos;
    
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFotoPrincipal() {
		return fotoPrincipal;
	}
	public void setFotoPrincipal(String fotoPrincipal) {
		this.fotoPrincipal = fotoPrincipal;
	}
	public int getPorciones() {
		return porciones;
	}
	public void setPorciones(int porciones) {
		this.porciones = porciones;
	}
	public int getCantidadPersonas() {
		return cantidadPersonas;
	}
	public void setCantidadPersonas(int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}
	public int getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
	public List<PasoDTO> getPasos() {
		return pasos;
	}
	public void setPasos(List<PasoDTO> pasos) {
		this.pasos = pasos;
	}
    
    

}
