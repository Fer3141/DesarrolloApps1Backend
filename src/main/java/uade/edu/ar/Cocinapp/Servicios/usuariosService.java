package uade.edu.ar.Cocinapp.Servicios;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import uade.edu.ar.Cocinapp.Entidades.Alumno;
import uade.edu.ar.Cocinapp.Entidades.RegistroPendiente;
import uade.edu.ar.Cocinapp.Entidades.Usuario;
import uade.edu.ar.Cocinapp.Repositorios.AlumnoRepository;
import uade.edu.ar.Cocinapp.Repositorios.RegistroPendienteRepository;
import uade.edu.ar.Cocinapp.Repositorios.UsuarioRepository;

@Service
public class usuariosService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private RegistroPendienteRepository registroPendienteRepository;

    // metodo para verificar si un email ya está registrado
    public boolean existeMail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // login simple
    public void login(String email, String password) {
        email = email.trim().toLowerCase(); // ✅ normalizar

        Optional<Usuario> u = usuarioRepository.findByEmail(email);
        if (u.isEmpty()) {
            throw new RuntimeException("usuario no encontrado");
        }

        Usuario usuario = u.get();

        if (!usuario.isHabilitado()) {
            throw new RuntimeException("usuario no habilitado");
        }

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("contraseña incorrecta");
        }
    }

    // modificar contraseña
    public void modificarPass(String email, String nuevaPass) {
        Usuario u = usuarioRepository.findByEmail(email).orElseThrow(
            () -> new RuntimeException("usuario no encontrado"));
        u.setPassword(nuevaPass);
        usuarioRepository.save(u);
    }

    // registro final (paso 3 del circuito)
    public void TerminarRegistro(String email,
                                  boolean esAlumno,
                                  String nombre,
                                  String apellido,
                                  String password,
                                  String fotoDniFrente,
                                  String fotoDniDorso,
                                  String nroTramiteDni,
                                  String cuentaCorrienteStr) {

        // buscamos el registro pendiente con ese email
        RegistroPendiente registro = registroPendienteRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("registro pendiente no encontrado"));

        if (esAlumno) {
            Alumno a = new Alumno();
            a.setEmail(registro.getEmail());
            a.setAlias(registro.getAlias());
            a.setNombre(nombre);
            a.setPassword(password);
            a.setHabilitado(true);

            a.setFotoDniFrente(fotoDniFrente);
            a.setFotoDniDorso(fotoDniDorso);
            a.setNroTramiteDni(nroTramiteDni);

            try {
                a.setCuentaCorriente(Float.parseFloat(cuentaCorrienteStr));
            } catch (NumberFormatException e) {
                a.setCuentaCorriente(0f);
            }

            alumnoRepository.save(a);

        } else {
            Usuario u = new Usuario();
            u.setEmail(registro.getEmail());
            u.setAlias(registro.getAlias());
            u.setNombre(nombre);
            u.setPassword(password);
            u.setHabilitado(true);
            usuarioRepository.save(u);
        }

        // eliminamos el registro pendiente
        registroPendienteRepository.delete(registro);
    }

    public Usuario loginYDevolver(String email, String password) {
        email = email.trim().toLowerCase();
        Optional<Usuario> u = usuarioRepository.findByEmail(email);
        if (u.isEmpty()) throw new RuntimeException("usuario no encontrado");

        Usuario usuario = u.get();

        if (!usuario.isHabilitado()) throw new RuntimeException("usuario no habilitado");
        if (!usuario.getPassword().equals(password)) throw new RuntimeException("contraseña incorrecta");

        return usuario;
    }

        public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("nombre", usuario.getNombre())
                .claim("id", usuario.getIdUsuario())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 día
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256)) //  clave para el token
                .compact();
    }
}
