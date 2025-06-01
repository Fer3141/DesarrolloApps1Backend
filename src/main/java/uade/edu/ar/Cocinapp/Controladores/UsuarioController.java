package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uade.edu.ar.Cocinapp.Entidades.usuarios;
import uade.edu.ar.Cocinapp.Servicios.RegistroCodigoService;
import uade.edu.ar.Cocinapp.Servicios.usuariosService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	@Autowired
    private usuariosService us;

    @Autowired
    private RegistroCodigoService codigoService;

    @GetMapping("/verificar-disponibilidad")
    public ResponseEntity<?> verificarDisponibilidadYEnviarCodigo(@RequestBody RegistroRequest request) {
        if (us.existeMailONickname(request.getMail(), request.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mail o alias ya están en uso.");
        }

        codigoService.generarYEnviarCodigo(request.getMail());
        return ResponseEntity.ok("Código enviado al mail.");
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<?> verificarCodigo(@RequestBody CodigoVerificacionRequest request) {
        boolean esValido = codigoService.verificarCodigo(request.getMail(), request.getCodigo());

        if (!esValido) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código incorrecto o expirado.");
        }

        usuarios usuario = us.habilitarUsuario(request.getMail());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        return ResponseEntity.ok("Usuario verificado y habilitado.");
    }
    
    public static class RegistroRequest {
        private String mail;
        private String nickname;
        
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
        
        
    }

    public static class CodigoVerificacionRequest {
        private String mail;
        private String codigo;
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		public String getCodigo() {
			return codigo;
		}
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
    }

}
