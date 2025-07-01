package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCodigoVerificacion(String destinatario, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Código de verificación");
        mensaje.setText("Tu código de verificación es: " + codigo);
        mailSender.send(mensaje);
    }

    //enviar mail cuando le rechazan la receta
    public void enviarNotificacionRechazo(String destinatario, String nombreReceta, String motivo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Tu receta fue rechazada en Cocinapp");
        mensaje.setText("Hola!\n\nTu receta \"" + nombreReceta + "\" fue rechazada por el siguiente motivo:\n\n"
                + motivo + "\n\nPodés editarla desde la app y volver a enviarla para aprobación.\n\nGracias por compartir en Cocinapp.");
        mailSender.send(mensaje);
    }

    public void enviarNotificacionAprobacion(String destinatario, String nombreReceta) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("¡Tu receta fue aprobada en Cocinapp!");
        mensaje.setText("Hola!\n\nTu receta \"" + nombreReceta + "\" fue aprobada y ya está disponible en el feed de Cocinapp.\n\n"
                + "Gracias por compartir tus recetas con la comunidad.\n\n¡Seguí cocinando!");
        mailSender.send(mensaje);
    }
}
