package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "alumnos")
public class Alumno extends Usuario {

    
    @Column(name = "foto_dni_frente")
    private String fotoDniFrente;

    
    @Column(name = "foto_dni_dorso")
    private String fotoDniDorso;

    @Column(name = "nro_tramite_dni")
    private String nroTramiteDni;

    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;

    @Column(name = "cuenta_corriente")
    private Float cuentaCorriente;

    // getters y setters

    public String getFotoDniFrente() {
        return fotoDniFrente;
    }

    public void setFotoDniFrente(String fotoDniFrente) {
        this.fotoDniFrente = fotoDniFrente;
    }

    public String getFotoDniDorso() {
        return fotoDniDorso;
    }

    public void setFotoDniDorso(String fotoDniDorso) {
        this.fotoDniDorso = fotoDniDorso;
    }

    public String getNroTramiteDni() {
        return nroTramiteDni;
    }

    public void setNroTramiteDni(String nroTramiteDni) {
        this.nroTramiteDni = nroTramiteDni;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public Float getCuentaCorriente() {
        return cuentaCorriente;
    }

    public void setCuentaCorriente(Float cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }
}
