package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistroCodigoService {

    // Almacena códigos temporales: mail → código + expiración
    private final Map<String, CodigoRegistro> codigos = new ConcurrentHashMap<>();
    
    @Autowired
    private CorreoService correoService;

    // Genera y guarda un código temporalmente, y lo "envía" (acá simulado)
    public void generarYEnviarCodigo(String mail) {
        String codigo = String.valueOf((int) (Math.random() * 900_000 + 100_000)); // Código de 6 dígitos
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(10);

        codigos.put(mail, new CodigoRegistro(codigo, expiracion));

        // Simula envío de correo (reemplazá esto por tu servicio real de mail)
        correoService.enviarCodigoVerificacion(mail, codigo);
    }

    // Verifica si el código ingresado es válido
    public boolean verificarCodigo(String mail, String codigoIngresado) {
        CodigoRegistro registro = codigos.get(mail);
        if (registro == null) return false;

        if (registro.expiracion.isBefore(LocalDateTime.now())) {
            codigos.remove(mail); // elimina si expiró
            return false;
        }

        boolean esValido = registro.codigo.equals(codigoIngresado);
        if (esValido) codigos.remove(mail); // elimina si se usa correctamente

        return esValido;
    }

    // Clase interna para agrupar código + expiración
    private static class CodigoRegistro {
        private final String codigo;
        private final LocalDateTime expiracion;

        public CodigoRegistro(String codigo, LocalDateTime expiracion) {
            this.codigo = codigo;
            this.expiracion = expiracion;
        }
    }
}

