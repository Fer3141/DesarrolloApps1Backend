package uade.edu.ar.Cocinapp.Controladores;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uade.edu.ar.Cocinapp.DTO.RegistroInicialRequest;
import uade.edu.ar.Cocinapp.Entidades.RegistroPendiente;
import uade.edu.ar.Cocinapp.Repositorios.RegistroPendienteRepository;
import uade.edu.ar.Cocinapp.Repositorios.UsuarioRepository;
import uade.edu.ar.Cocinapp.Servicios.TokenBlacklistService;
import uade.edu.ar.Cocinapp.Servicios.RegistroCodigoService;
import uade.edu.ar.Cocinapp.Servicios.usuariosService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private usuariosService us;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroPendienteRepository registroPendienteRepository;

    @Autowired
    private RegistroCodigoService codigoService;

    @Autowired
    private TokenBlacklistService tk;

    // ping de prueba
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    // login simple
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InicioSesionRequest is) {
        us.login(is.getMail(), is.getPassword());
        return ResponseEntity.ok("login exitoso");
    }

    // logout (simulado con blacklist)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        tk.invalidarToken(token);
        return ResponseEntity.ok("logout exitoso");
    }

    // paso 1: registro inicial con email y alias
    @PostMapping("/registro-inicial")
    public ResponseEntity<?> registroInicial(@RequestBody RegistroInicialRequest request) {
        String email = request.getEmail();
        String alias = request.getAlias();

        if (usuarioRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("el email ya está registrado.");
        }

        if (registroPendienteRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ya hay un registro pendiente con este email.");
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
        registroPendienteRepository.save(rp);

        System.out.println("→ Código de verificación para " + email + ": " + codigo);

        return ResponseEntity.ok("registro iniciado. revisá tu email.");
    }

    // paso 2: confirmación del código
    @PostMapping("/confirmar-codigo")
    public ResponseEntity<?> confirmarCodigo(@RequestBody CodigoVerificacionRequest request) {
        String email = request.getMail();
        String codigo = request.getCodigo();

        RegistroPendiente pendiente = registroPendienteRepository.findByEmail(email).orElse(null);
        if (pendiente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontró registro pendiente");
        }

        if (!pendiente.getCodigoVerificacion().equals(codigo)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("código incorrecto");
        }

        if (pendiente.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body("código expirado");
        }

        return ResponseEntity.ok("código válido, podés continuar con el registro");
    }

    // paso 3: registro final con todos los datos
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroFinalRequest r) {
        us.TerminarRegistro(
            r.getEmail(), r.isAlumno(), r.getNombre(), r.getApellido(),
            r.getPassword(), r.getFotoDniFrente(), r.getFotoDniDorso(),
            r.getNroTramiteDni(), r.getCuentaCorriente()
        );
        return ResponseEntity.ok("registro finalizado");
    }

    // recuperación de contraseña - paso 1
    @PostMapping("/auth/recover-password")
    public ResponseEntity<?> recoverPassword(@RequestBody EmailRequest r) {
        if (us.existeMail(r.getMail())) {
            codigoService.generarYEnviarCodigo(r.getMail());
            return ResponseEntity.ok("código enviado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe ese mail");
    }

    // reseteo de contraseña - paso 2
    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest r) {
        if (us.existeMail(r.getMail())) {
            us.modificarPass(r.getMail(), r.getPass());
            return ResponseEntity.ok("contraseña cambiada");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe ese mail");
    }

    // ------------------- CLASES INTERNAS (recomendado mover a un paquete DTO después) -------------------

    public static class InicioSesionRequest {
        private String mail;
        private String password;
        public String getMail() { return mail; }
        public void setMail(String mail) { this.mail = mail; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class CodigoVerificacionRequest {
        private String mail;
        private String codigo;
        public String getMail() { return mail; }
        public void setMail(String mail) { this.mail = mail; }
        public String getCodigo() { return codigo; }
        public void setCodigo(String codigo) { this.codigo = codigo; }
    }

    public static class RegistroFinalRequest {
        private String email;
        private boolean alumno;
        private String nombre;
        private String apellido;
        private String password;
        private String fotoDniFrente;
        private String fotoDniDorso;
        private String nroTramiteDni;
        private String cuentaCorriente;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public boolean isAlumno() { return alumno; }
        public void setAlumno(boolean alumno) { this.alumno = alumno; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getApellido() { return apellido; }
        public void setApellido(String apellido) { this.apellido = apellido; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getFotoDniFrente() { return fotoDniFrente; }
        public void setFotoDniFrente(String fotoDniFrente) { this.fotoDniFrente = fotoDniFrente; }
        public String getFotoDniDorso() { return fotoDniDorso; }
        public void setFotoDniDorso(String fotoDniDorso) { this.fotoDniDorso = fotoDniDorso; }
        public String getNroTramiteDni() { return nroTramiteDni; }
        public void setNroTramiteDni(String nroTramiteDni) { this.nroTramiteDni = nroTramiteDni; }
        public String getCuentaCorriente() { return cuentaCorriente; }
        public void setCuentaCorriente(String cuentaCorriente) { this.cuentaCorriente = cuentaCorriente; }
    }

    public static class EmailRequest {
        private String mail;
        public String getMail() { return mail; }
        public void setMail(String mail) { this.mail = mail; }
    }

    public static class ResetPasswordRequest {
        private String mail;
        private String pass;
        public String getMail() { return mail; }
        public void setMail(String mail) { this.mail = mail; }
        public String getPass() { return pass; }
        public void setPass(String pass) { this.pass = pass; }
    }
}
