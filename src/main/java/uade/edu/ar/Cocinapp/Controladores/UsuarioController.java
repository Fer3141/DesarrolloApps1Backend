package uade.edu.ar.Cocinapp.Controladores;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import uade.edu.ar.Cocinapp.DTO.DatosAlumnoDTO;
import uade.edu.ar.Cocinapp.DTO.LoginResponseDTO;
import uade.edu.ar.Cocinapp.DTO.PerfilDTO;
import uade.edu.ar.Cocinapp.DTO.RegistroInicialRequest;
import uade.edu.ar.Cocinapp.Entidades.RegistroPendiente;
import uade.edu.ar.Cocinapp.Entidades.Rol;
import uade.edu.ar.Cocinapp.Entidades.Usuario;
import uade.edu.ar.Cocinapp.Repositorios.RegistroPendienteRepository;
import uade.edu.ar.Cocinapp.Repositorios.UsuarioRepository;
import uade.edu.ar.Cocinapp.Servicios.TokenBlacklistService;
import uade.edu.ar.Cocinapp.Servicios.CorreoService;
import uade.edu.ar.Cocinapp.Servicios.RegistroCodigoService;
import uade.edu.ar.Cocinapp.Servicios.usuariosService;
import org.json.JSONObject;


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
    private CorreoService correo;

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
        try {
            var usuario = us.loginYDevolver(is.getMail(), is.getPassword());

            String token = us.generarToken(usuario); 
            return ResponseEntity.ok(new LoginResponseDTO(token)); // devuelve el token
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
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
        correo.enviarCodigoVerificacion(email, codigo);
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
             codigoService.generarYGuardarCodigoRecuperacion(r.getMail());
            return ResponseEntity.ok("código enviado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe ese mail");
    }

    @PostMapping("/auth/verificar-codigo-recuperacion")
    public ResponseEntity<?> verificarCodigoRecuperacion(@RequestBody CodigoVerificacionRequest request) {
        boolean valido = codigoService.verificarCodigoRecuperacion(request.getMail(), request.getCodigo());

        if (valido) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("código inválido o expirado");
        }
    }

    // reseteo de contraseña - paso 2
    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest r) {
        if (!us.existeMail(r.getMail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no existe ese mail");
        }

        us.modificarPass(r.getMail(), r.getPass());
        return ResponseEntity.ok("contraseña cambiada");
}
    
    @PostMapping("/crear-admin")
    public ResponseEntity<?> crearAdmin() {
        if (usuarioRepository.findByEmail("admin@admin.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setEmail("admin@admin.com");
            admin.setAlias("admin");
            admin.setNombre("Administrador");
            admin.setPassword("admin");
            admin.setHabilitado(true);
            admin.setRol(Rol.ADMIN);
            usuarioRepository.save(admin);
            return ResponseEntity.ok("Admin creado");
        }
        return ResponseEntity.ok("Ya existe un admin");
    }


    @PutMapping("/editar-biografia")
    public ResponseEntity<?> editarBiografia(@RequestParam Long idUsuario,
                                            @RequestParam String biografia) {
        try {
            us.editarBiografia(idUsuario, biografia);
            return ResponseEntity.ok("Biografía actualizada con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping(value = "/obtener-perfil", produces = "application/json")
    public ResponseEntity<?> obtenerBiografia(@RequestParam Long idUsuario) {
        try {
            Usuario usuario = us.obtenerUsuario(idUsuario);

            PerfilDTO dto = new PerfilDTO();
            dto.setNombre(usuario.getNombre());
            dto.setBiografia(usuario.getBiografia());
            dto.setAlias(usuario.getAlias());

            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }


    
    @PutMapping("/hacer-alumno") // NO ANDA :(
    public ResponseEntity<String> convertirEnAlumno(@RequestParam Long idUsuario,
                                                    @RequestBody DatosAlumnoDTO datos) {
        try {
            // Obtener el usuario actual
            Usuario usuario = us.obtenerUsuario(idUsuario);

            // Copiar campos comunes del usuario al DTO
            datos.setAlias(usuario.getAlias());
            datos.setEmail(usuario.getEmail());
            datos.setPassword(usuario.getPassword());
            datos.setNombre(usuario.getNombre());
            datos.setDireccion(usuario.getDireccion());
            datos.setAvatar(usuario.getAvatar());
            datos.setBiografia(usuario.getBiografia());

            // Llamar al service
            us.convertirEnAlumno(idUsuario, datos);

            return ResponseEntity.ok("Ahora sos alumno");

        } catch (Exception e) {
            System.out.println("⚠️ Error en /hacer-alumno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
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
    private String codigo;

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
}