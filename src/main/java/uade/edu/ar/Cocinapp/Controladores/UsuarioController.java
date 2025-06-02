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
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
    private usuariosService us;

    @Autowired
    private RegistroCodigoService codigoService;

    @PostMapping("/auth/verificar")
    public ResponseEntity<?> verificarDisponibilidadYEnviarCodigo(@RequestBody RegistroRequest request) {
    	System.out.println("→ LLEGO solicitud desde Android");
    	System.out.println("→ MAIL: " + request.getMail());
    	System.out.println("→ NICKNAME: " + request.getNickname());

    	if (us.existeMailONickname(request.getMail(), request.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mail o alias ya están en uso.");
        }

        codigoService.generarYEnviarCodigo(request.getMail());
        return ResponseEntity.ok("Código enviado al mail.");
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<?> verificarCodigo(@RequestBody CodigoVerificacionRequest request) {
        System.out.println("→ Verificando código para: " + request.getMail());
        String resultado = codigoService.verificarCodigo(request.getMail(), request.getCodigo());

        switch (resultado) {
            case "EXPIRADO":
                return ResponseEntity.status(HttpStatus.GONE).body("⏰ Código expirado.");
            case "INVALIDO":
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Código incorrecto.");
            case "NO_EXISTE":
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ No se ha solicitado un código para este mail.");
            case "VALIDO":
                return ResponseEntity.ok("✅ Código válido. Continuar con el registro.");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado.");
        }
    }



    
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
    	System.out.println("→ LLEGO solicitud desde Android: PONG");
        return ResponseEntity.ok("pong2");
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
