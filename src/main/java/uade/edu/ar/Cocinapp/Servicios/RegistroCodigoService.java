package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistroCodigoService {

    // Map que almacena códigos por mail, con fecha de expiración
    private final Map<String, CodigoRegistro> codigos = new ConcurrentHashMap<>();

    @Autowired
    private CorreoService correoService;

    /**
     * Genera un código aleatorio, lo guarda y simula el envío por correo.
     */
    public void generarYEnviarCodigo(String mail) {
        String codigo = generarCodigo();
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(30);

        codigos.put(mail, new CodigoRegistro(codigo, expiracion));
        correoService.enviarCodigoVerificacion(mail, codigo);

        System.out.println("✔ Código generado para " + mail + ": " + codigo);
    }

    /**
     * Verifica si el código ingresado es correcto y no ha expirado.
     */
    public String verificarCodigo(String mail, String codigoIngresado) {
        CodigoRegistro registro = codigos.get(mail);
        System.out.println("→ Buscando código para: " + mail);

        if (registro == null) {
            System.out.println("❌ No hay código generado para ese mail.");
            return "NO_EXISTE";
        }

        if (registro.expiracion.isBefore(LocalDateTime.now())) {
            System.out.println("❌ Código expirado para " + mail + ". Expiraba a: " + registro.expiracion);
            codigos.remove(mail);
            return "EXPIRADO";
        }

        if (!registro.codigo.equals(codigoIngresado)) {
            System.out.println("❌ Código incorrecto para " + mail);
            System.out.println("→ Código esperado: " + registro.codigo);
            System.out.println("→ Código ingresado: " + codigoIngresado);
            return "INVALIDO";
        }

        // Código correcto
        codigos.remove(mail);
        System.out.println("✅ Código verificado correctamente para " + mail + ". Código: " + codigoIngresado);
        return "VALIDO";
    }




    private String generarCodigo() {
        int random = (int) (Math.random() * 900_000 + 100_000); // 6 dígitos
        return String.valueOf(random);
    }

    /**
     * Clase interna que representa un código temporal con su vencimiento.
     */
    private static class CodigoRegistro {
        private final String codigo;
        private final LocalDateTime expiracion;

        public CodigoRegistro(String codigo, LocalDateTime expiracion) {
            this.codigo = codigo;
            this.expiracion = expiracion;
        }
    }
}
