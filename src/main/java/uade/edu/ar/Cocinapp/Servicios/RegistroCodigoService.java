package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uade.edu.ar.Cocinapp.Entidades.CodigoRecuperacion;
import uade.edu.ar.Cocinapp.Repositorios.CodigoRecuperacionRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RegistroCodigoService {

    // Para verificación de registro (en memoria)
    private final Map<String, CodigoRegistro> codigos = new ConcurrentHashMap<>();

    @Autowired
    private CorreoService correoService;

    @Autowired
    private CodigoRecuperacionRepository codigoRecuperacionRepository;

    /**
     * Usado en REGISTRO: genera código, lo guarda en memoria y lo "envía"
     */
    public void generarYEnviarCodigo(String mail) {
        String codigo = generarCodigo();
        LocalDateTime expiracion = LocalDateTime.now().plusMinutes(30);

        codigos.put(mail, new CodigoRegistro(codigo, expiracion));
        correoService.enviarCodigoVerificacion(mail, codigo);
        System.out.println("✔ Código generado para " + mail + ": " + codigo);
    }

    /**
     * Usado en REGISTRO: valida código en memoria
     */
    public String verificarCodigo(String mail, String codigoIngresado) {
        CodigoRegistro registro = codigos.get(mail);
        System.out.println("→ Buscando código para: " + mail);

        if (registro == null) {
            return "NO_EXISTE";
        }

        if (registro.expiracion.isBefore(LocalDateTime.now())) {
            codigos.remove(mail);
            return "EXPIRADO";
        }

        if (!registro.codigo.equals(codigoIngresado)) {
            return "INVALIDO";
        }

        codigos.remove(mail);
        return "VALIDO";
    }

    /**
     * Usado en RECUPERAR CONTRASEÑA: genera código y lo guarda en la base
     */
    @Transactional
    public void generarYGuardarCodigoRecuperacion(String mail) {
        String codigo = generarCodigo();

        // eliminar si ya había uno
        codigoRecuperacionRepository.deleteByEmail(mail);

        CodigoRecuperacion cr = new CodigoRecuperacion();
        cr.setEmail(mail);
        cr.setCodigo(codigo);
        cr.setFechaExpiracion(LocalDateTime.now().plusMinutes(30));
        codigoRecuperacionRepository.save(cr);

        correoService.enviarCodigoVerificacion(mail, codigo);
        System.out.println("✔ Código de recuperación para " + mail + ": " + codigo);
    }

    /**
     * Usado en RECUPERAR CONTRASEÑA: valida el código de la base
     */
    public boolean verificarCodigoRecuperacion(String mail, String codigoIngresado) {
        Optional<CodigoRecuperacion> cr = codigoRecuperacionRepository.findByEmail(mail);
        if (cr.isEmpty()) {
            System.out.println("❌ No se encontró código en base para: " + mail);
            return false;
        }

        CodigoRecuperacion c = cr.get();
        System.out.println("→ Código en base: [" + c.getCodigo() + "]");
        System.out.println("→ Ingresado: [" + codigoIngresado + "]");

        // normalizamos
        String esperado = c.getCodigo().trim();
        String ingresado = codigoIngresado.trim();

        if (!esperado.equals(ingresado)) {
            System.out.println("❌ Códigos no coinciden");
            return false;
        }

        if (c.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            codigoRecuperacionRepository.delete(c);
            System.out.println("❌ Código expirado");
            return false;
        }

        codigoRecuperacionRepository.delete(c);
        System.out.println("✅ Código válido y eliminado");
        return true;
    }


    // código aleatorio de 6 cifras
    private String generarCodigo() {
        return String.valueOf((int)(Math.random() * 900_000 + 100_000));
    }

    // clase para código temporal en memoria (registro)
    private static class CodigoRegistro {
        private final String codigo;
        private final LocalDateTime expiracion;

        public CodigoRegistro(String codigo, LocalDateTime expiracion) {
            this.codigo = codigo;
            this.expiracion = expiracion;
        }
    }
}
