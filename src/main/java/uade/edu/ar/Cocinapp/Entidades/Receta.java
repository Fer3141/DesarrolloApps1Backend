package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recetas")
public class Receta {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceta")
	private Long idReceta;
	
	@ManyToOne
	@JoinColumn(name = "idUsuario") // este es el nombre de la columna en la tabla
	private Usuario usuario;
	
	private String nombreReceta;
	
	private String descripcionReceta;
	
	private String fotoPrincipal;
	
	private int porciones;
	
	private int cantidadPersonas;
	
	private int idTipo;

	public Long getIdReceta() {
		return idReceta;
	}

	public void setIdReceta(Long idReceta) {
		this.idReceta = idReceta;
	}


	public String getNombreReceta() {
		return nombreReceta;
	}

	public void setNombreReceta(String nombreReceta) {
		this.nombreReceta = nombreReceta;
	}

	public String getDescripcionReceta() {
		return descripcionReceta;
	}

	public void setDescripcionReceta(String descripcionReceta) {
		this.descripcionReceta = descripcionReceta;
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

	public Receta() {
		
	}

	public Usuario getUsuario() {
    return usuario;
}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
