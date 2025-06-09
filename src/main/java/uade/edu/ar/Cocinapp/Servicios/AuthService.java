package uade.edu.ar.Cocinapp.Servicios;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import uade.edu.ar.Cocinapp.DTO.RegistroInicialRequest;
import uade.edu.ar.Cocinapp.Entidades.RegistroPendiente;
import uade.edu.ar.Cocinapp.Repositorios.RegistroPendienteRepository;
import uade.edu.ar.Cocinapp.Repositorios.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RegistroPendienteRepository registroPendienteRepository;

    // método para iniciar el registro (paso 1)
    public String iniciarRegistro(RegistroInicialRequest request) {
        String email = request.getEmail();
        String alias = request.getAlias();

        if (usuarioRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "el email ya está registrado.");
        }

        if (registroPendienteRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ya hay un registro pendiente con este email.");
        }

        if (usuarioRepository.existsByAlias(alias) || registroPendienteRepository.existsByAlias(alias)) {
            String sugerencias = alias + "123, " + alias + "_ok, " + alias + "_2025";
            throw new ResponseStatusException(HttpStatus.CONFLICT, "alias ya en uso. probá con: " + sugerencias);
        }

        String codigo = generarCodigoVerificacion();

        RegistroPendiente rp = new RegistroPendiente();
        rp.setEmail(email);
        rp.setAlias(alias);
        rp.setCodigoVerificacion(codigo);
        rp.setFechaExpiracion(LocalDateTime.now().plusHours(24));
        registroPendienteRepository.save(rp);

        // simulamos envío de código por consola
        System.out.println("→ Código de verificación para " + email + ": " + codigo);

        return "registro iniciado. revisá tu email.";
    }

    private String generarCodigoVerificacion() {
        return String.valueOf((int)(Math.random() * 900000) + 100000); // genera 6 dígitos aleatorios
    }
}
