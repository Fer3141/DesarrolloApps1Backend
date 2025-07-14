package uade.edu.ar.Cocinapp.Entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pasos")
public class Paso {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPaso")
	private Long idPaso;
	
	@ManyToOne
	@JoinColumn(name = "idReceta")
	private Receta receta;
	
	private int nroPaso;
	
	private String texto;

	/**
     * Relación con Multimedia: al borrar un Paso
     * también se borran automáticamente sus registros de multimedia.
     */
    @OneToMany(
      mappedBy = "paso",
      cascade = CascadeType.ALL,
      orphanRemoval = true
    )
    private List<Multimedia> multimedia;


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

	public Paso() {

	}
	
	

}
