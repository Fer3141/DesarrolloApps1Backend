package uade.edu.ar.Cocinapp.Entidades;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "recetas")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReceta")
    private Long idReceta;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    private String nombreReceta;

    private String descripcionReceta;

    private String fotoPrincipal;

    private int porciones;

    private int cantidadPersonas;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoReceta tipo;

    // campos para manejar la probacion de la creacion de recetas

    @Column(name = "aprobada", nullable = false)
    private boolean aprobada = false;

    @Column(name = "rechazada", nullable = false)
    private boolean rechazada = false;

    @Column(name = "motivo_rechazo")
    private String motivoRechazo;


    @OneToMany(mappedBy="receta", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<pasos> pasos;

    @OneToMany(mappedBy="receta", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Ingrediente> ingredientes;




    // Getters y setters

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public TipoReceta getTipo() {
        return tipo;
    }

    public void setTipo(TipoReceta tipo) {
        this.tipo = tipo;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    public boolean isRechazada() {
        return rechazada;
    }

    public void setRechazada(boolean rechazada) {
        this.rechazada = rechazada;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Receta() {
    }
}
