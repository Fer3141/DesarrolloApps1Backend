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
	
	
	//Para inicio de sesion
	public String login(String email, String password) {
        Optional<usuarios> optionalUsuario = ur.findByMail(email);

        if (optionalUsuario.isEmpty()) {
            return "El usuario no está registrado.";
        }

        usuarios usuario = optionalUsuario.get();

        if (!"Si".equalsIgnoreCase(usuario.getHabilitado())) {
            return "La cuenta no está habilitada.";
        }
        
        if (!usuario.getPassword().equals(password)) {
            return "Contraseña incorrecta.";
        }

        return "Login exitoso. Bienvenido " + usuario.getNickname();
    }
	
	
	//Para verificar que un usuario o contraseña ya existe al querer un usuario registrarse
	public boolean existeMailONickname(String mail, String nickname) {
        return ur.existsByMail(mail) || ur.existsByNickname(nickname);
    }

    public String verificarDisponibilidad(String mail, String nickname) {
        if (ur.existsByMail(mail)) {
            return "El mail ya está registrado.";
        }

        if (ur.existsByNickname(nickname)) {
            // Sugerencia: devolver sugerencias de alias disponibles también si querés
            return "El alias ya está en uso.";
        }

        return "Disponible";
    }
    
    
    //Para habilitar usuario una vez se envie el codigo correcto
    public usuarios habilitarUsuario(String mail, String user) {
        Optional<usuarios> optionalUsuario = ur.findByMail(mail);
        usuarios u = new usuarios();
        u.setMail(mail);
        u.setNickname(user);
        u.setHabilitado("SI");
        return ur.save(u);
    }

    public void TerminarRegistro(Long id, boolean a, String nomb, String pass, String dni1, String dni2, String tramite, String tarjeta) {
    	usuarios u = ur.findById(id).get();
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

}
