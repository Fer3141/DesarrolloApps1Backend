package uade.edu.ar.Cocinapp.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uade.edu.ar.Cocinapp.Entidades.usuarios;
import uade.edu.ar.Cocinapp.Entidades.alumnos;
import uade.edu.ar.Cocinapp.Repositorios.alumnosRepo;
import uade.edu.ar.Cocinapp.Repositorios.usuariosRepo;

@Service
public class usuariosService {
	
	@Autowired
    private usuariosRepo ur;
	
	@Autowired
    private alumnosRepo ar;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	//Para inicio de sesion
	public String login(String email, String password) {
	    Optional<usuarios> optionalUsuario = ur.findByMail(email);

	    if (optionalUsuario.isEmpty()) {
	        return "ERROR: El usuario no está registrado.";
	    }

	    usuarios usuario = optionalUsuario.get();

	    if (!"Si".equalsIgnoreCase(usuario.getHabilitado())) {
	        return "ERROR: La cuenta no está habilitada.";
	    }

	    if (!usuario.getPassword().equals(password)) {
	        return "ERROR: Contraseña incorrecta.";
	    }

	    String token = jwtUtil.generarToken(email);
	    return token; 
	}
	
	
	//Para verificar que un usuario o contraseña ya existe al querer un usuario registrarse
	public boolean existeMailONickname(String mail, String nickname) {
        return ur.existsByMail(mail) || ur.existsByNickname(nickname);
    }
	
	public boolean existeMail(String mail) {
        return ur.existsByMail(mail);
    }

    public Long guardarUsuario(String mail, String nickname) {
        if (ur.existsByMail(mail)) {
            return (long) 0;
        }

        if (ur.existsByNickname(nickname)) {
            // Sugerencia: devolver sugerencias de alias disponibles también si querés
            return (long) 0;
        }
        usuarios u = new usuarios();
        u.setMail(mail);
        u.setNickname(nickname);
        u.setHabilitado("NO");
        ur.save(u);
        return u.getIdUsuario();
    }
    
    
    //Para habilitar usuario una vez se envie el codigo correcto
    public usuarios habilitarUsuario(String mail) {
        Optional<usuarios> optionalUsuario = ur.findByMail(mail);
        usuarios u = new usuarios();
        u.setMail(mail);
        u.setHabilitado("SI");
        return ur.save(u);
    }

    public void TerminarRegistro(String id, boolean a, String nomb, String pass, String dni1, String dni2, String tramite, String tarjeta) {
    	usuarios u = ur.findByMail(id).get();
    	u.setNombre(nomb);
    	u.setPassword(pass);
    	ur.save(u);
    	if (a==true){
    		alumnos al = new alumnos();
    		al.setDniFrente(dni1);
    		al.setDniFondo(dni2);
    		al.setTramite(tramite);
    		al.setNumeroTarjeta(tarjeta);
    		ar.save(al);
    	}
    	
    }
    
    public int modificarPass (String mail, String pass) {
    	Optional<usuarios> u = ur.findByMail(mail);
    	if(!u.isEmpty()) {
    		return 0;
    	}
    	usuarios user = u.get();
    	user.setPassword(pass);
    	ur.save(user);
    	return 1;
    }

}
