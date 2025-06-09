package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Long id;

    @Column(name = "mail", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String alias;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "habilitado")
    private boolean habilitado;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "avatar")
    private String avatar;

    // getters y setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isHabilitado() { return habilitado; }
    public void setHabilitado(boolean habilitado) { this.habilitado = habilitado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
