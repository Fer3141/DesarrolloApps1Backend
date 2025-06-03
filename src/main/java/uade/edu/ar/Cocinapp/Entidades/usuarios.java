package uade.edu.ar.Cocinapp.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class usuarios {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private Long idUsuario;
	
	@Column(name = "mail")
	private String mail;
	
	private String nickname;
	
	private String password;
	
	private String habilitado;
	
	private String nombre;
	
	private String direccion;
	
	private String avatar;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = (long) idUsuario;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(String habilitado) {
		this.habilitado = habilitado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public usuarios(String mail, String nickname, String password, String habilitado, String nombre,
			String direccion, String avatar) {
		super();
		this.mail = mail;
		this.nickname = nickname;
		this.password = password;
		this.habilitado = habilitado;
		this.nombre = nombre;
		this.direccion = direccion;
		this.avatar = avatar;
	}

	public usuarios() {
		// TODO Auto-generated constructor stub
	}

}
