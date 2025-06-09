package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uade.edu.ar.Cocinapp.Entidades.usuarios;
import uade.edu.ar.Cocinapp.Servicios.RegistroCodigoService;
import uade.edu.ar.Cocinapp.Servicios.usuariosService;
import uade.edu.ar.Cocinapp.Servicios.TokenBlacklistService;

@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
    private usuariosService us;

    @Autowired
    private RegistroCodigoService codigoService;
    
    @Autowired
    private TokenBlacklistService tk;
    
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody inicioSesion is) {
        us.login(is.getMail(), is.getPassword());
        return ResponseEntity.ok(200);
    }
<<<<<<< Updated upstream
    
=======

    // login simple
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InicioSesionRequest is) {
        try {
            var usuario = us.loginYDevolver(is.getMail(), is.getPassword());
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(401);
        }
    }

    // logout (simulado con blacklist)
>>>>>>> Stashed changes
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        tk.invalidarToken(token);
        return ResponseEntity.ok(200);
    }


<<<<<<< Updated upstream
    @PostMapping("/auth/verificar")
    public ResponseEntity<?> verificarDisponibilidadYEnviarCodigo(@RequestBody RegistroRequest request) {
    	//System.out.println("→ LLEGO solicitud desde Android");
    	//System.out.println("→ MAIL: " + request.getMail());
    	//System.out.println("→ NICKNAME: " + request.getNickname());

    	if (us.existeMailONickname(request.getMail(), request.getNickname())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(201);
        }
    	
        codigoService.generarYEnviarCodigo(request.getMail());
        us.guardarUsuario(request.getMail(), request.getNickname());
        return ResponseEntity.ok(409);
=======
        if (usuarioRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(200);
        }

        if (registroPendienteRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(202);
        }

        if (usuarioRepository.existsByAlias(alias) || registroPendienteRepository.existsByAlias(alias)) {
            String sugerencias = alias + "123, " + alias + "_ok, " + alias + "_2025";
            return ResponseEntity.status(HttpStatus.CONFLICT).body("alias en uso. probá con: " + sugerencias);
        }

        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
        RegistroPendiente rp = new RegistroPendiente();
        rp.setEmail(email);
        rp.setAlias(alias);
        rp.setCodigoVerificacion(codigo);
        rp.setFechaExpiracion(LocalDateTime.now().plusHours(24));
        correo.enviarCodigoVerificacion(email, codigo);
        registroPendienteRepository.save(rp);
        
        System.out.println("→ Código de verificación para " + email + ": " + codigo);

        return ResponseEntity.ok("registro iniciado. revisá tu email.");
>>>>>>> Stashed changes
    }
    

    @PostMapping("/confirm")
    public ResponseEntity<?> verificarCodigo(@RequestBody CodigoVerificacionRequest request) {
        System.out.println("→ Verificando código para: " + request.getMail());
        String resultado = codigoService.verificarCodigo(request.getMail(), request.getCodigo());

        switch (resultado) {
            case "EXPIRADO":
                return ResponseEntity.status(HttpStatus.GONE).body("400");
            case "INVALIDO":
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("409");
            case "NO_EXISTE":
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("400");
            case "VALIDO":
            	us.habilitarUsuario(request.getMail());
                return ResponseEntity.ok("200");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado.");
        }
<<<<<<< Updated upstream
=======

        if (!pendiente.getCodigoVerificacion().equals(codigo)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(409);
        }

        if (pendiente.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body(400);
        }

        return ResponseEntity.ok(200);
>>>>>>> Stashed changes
    }
    
    @PutMapping("/register")
    public ResponseEntity<?> register(@RequestBody Registro r){
    	us.TerminarRegistro(r.getId(), r.isAlumno(), r.getNombre(), r.getPassword(), r.getFotoDniFrente(), r.getFotoDniDorso(), r.getNroTramiteDni(), r.getCuentaCorriente());
    	return ResponseEntity.ok(200);
    }
    
    
    @PostMapping("/auth/recover-password")
<<<<<<< Updated upstream
    public ResponseEntity<?> RecoverPassword(@RequestBody recuMail r) {
    	if (us.existeMail(r.getMail())) {
    	codigoService.generarYEnviarCodigo(r.getMail());
    	return ResponseEntity.ok("200");
    	}
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404");
    }
    
    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> ResetPassword(@RequestBody resetPass r) {
    	if (us.existeMail(r.getMail())) {
    	us.modificarPass(r.getMail(), r.getPass());
    	return ResponseEntity.ok("200");
    	}
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404");
    }

    
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
    	System.out.println("→ LLEGO solicitud desde Android: PONG");
        return ResponseEntity.ok("pong2");
    }
=======
    public ResponseEntity<?> recoverPassword(@RequestBody EmailRequest r) {
        if (us.existeMail(r.getMail())) {
             codigoService.generarYGuardarCodigoRecuperacion(r.getMail());
            return ResponseEntity.ok(200);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(404);
    }

    @PostMapping("/auth/verificar-codigo-recuperacion")
    public ResponseEntity<?> verificarCodigoRecuperacion(@RequestBody CodigoVerificacionRequest request) {
        boolean valido = codigoService.verificarCodigoRecuperacion(request.getMail(), request.getCodigo());

        if (valido) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(409);
        }
    }

    // reseteo de contraseña - paso 2
    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest r) {
        if (!us.existeMail(r.getMail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(400);
        }

        us.modificarPass(r.getMail(), r.getPass());
        return ResponseEntity.ok(200);
}
>>>>>>> Stashed changes

    
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
    
    public static class Registro{
    	
    	private String id;
    	private boolean alumno;
    	private String nombre;
    	private String apellido;
    	private String password;
    	private String fotoDniFrente;
    	private String fotoDniDorso;
    	private String nroTramiteDni;
    	private String cuentaCorriente;
    	
		public boolean isAlumno() {
			return alumno;
		}
		public void setAlumno(boolean alumno) {
			this.alumno = alumno;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getApellido() {
			return apellido;
		}
		public void setApellido(String apellido) {
			this.apellido = apellido;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
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
		public String getCuentaCorriente() {
			return cuentaCorriente;
		}
		public void setCuentaCorriente(String cuentaCorriente) {
			this.cuentaCorriente = cuentaCorriente;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
    	
    }
    
    private static class inicioSesion {
    	private String mail;
    	private String password;
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
    }
    
    private static class recuMail{
    	private String mail;

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}
    }
    
    private static class resetPass{
    	private String mail;
    	private String pass;

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
		}
    }

}
