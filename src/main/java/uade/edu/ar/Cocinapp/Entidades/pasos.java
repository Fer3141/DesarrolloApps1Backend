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
@Table(name = "pasos")
public class pasos {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPaso")
	private Long idPaso;
	
	@ManyToOne
	@JoinColumn(name = "idReceta")
	private Receta receta;
	
	private int nroPaso;
	
	private String texto;

	public Long getIdPaso() {
		return idPaso;
	}

	public void setIdPaso(Long idPaso) {
		this.idPaso = idPaso;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	public int getNroPaso() {
		return nroPaso;
	}

	public void setNroPaso(int nroPaso) {
		this.nroPaso = nroPaso;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String text) {
		this.texto = text;
	}

	public pasos() {

	}
	
	

}
