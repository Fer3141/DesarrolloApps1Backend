package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alumnos")
public class alumnos {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAlumno;
	
	private String numeroTarjeta;
	
	private String dniFrente;
	
	private String dniFondo;
	
	private String tramite;
	
	private float cuentaCorriente;

	public int getIdAlumno() {
		return idAlumno;
	}

	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getDniFrente() {
		return dniFrente;
	}

	public void setDniFrente(String dniFrente) {
		this.dniFrente = dniFrente;
	}

	public String getDniFondo() {
		return dniFondo;
	}

	public void setDniFondo(String dniFondo) {
		this.dniFondo = dniFondo;
	}

	public String getTramite() {
		return tramite;
	}

	public void setTramite(String tramite) {
		this.tramite = tramite;
	}

	public float getCuentaCorriente() {
		return cuentaCorriente;
	}

	public void setCuentaCorriente(float cuentaCorriente) {
		this.cuentaCorriente = cuentaCorriente;
	}

	public alumnos(int idAlumno, String numeroTarjeta, String dniFrente, String dniFondo, String tramite,
			float cuentaCorriente) {
		super();
		this.idAlumno = idAlumno;
		this.numeroTarjeta = numeroTarjeta;
		this.dniFrente = dniFrente;
		this.dniFondo = dniFondo;
		this.tramite = tramite;
		this.cuentaCorriente = cuentaCorriente;
	}

	public alumnos() {
		
	}
	
	
	
}
