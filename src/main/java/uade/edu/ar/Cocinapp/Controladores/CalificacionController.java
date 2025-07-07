package uade.edu.ar.Cocinapp.Controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.DTO.CalificacionDTO;
import uade.edu.ar.Cocinapp.DTO.CalificacionVistaDTO;
import uade.edu.ar.Cocinapp.Entidades.Calificacion;
import uade.edu.ar.Cocinapp.Servicios.CalificacionService;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    // creo una nueva calificacion
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CalificacionDTO dto) {
        calificacionService.guardarCalificacion(dto);
        return ResponseEntity.ok("calificacion creada");
    }

    // obtengo las calificaciones de la receta
    @GetMapping
    public ResponseEntity<?> verPorReceta(@RequestParam Long idReceta) {
        return ResponseEntity.ok(calificacionService.obtenerPorReceta(idReceta));
    }
    
    
    @PutMapping("/comentarios/{id}/aprobar")
    public ResponseEntity<?> aprobarComentario(@PathVariable Long id) {
        Optional<Calificacion> optional = calificacionService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentario no encontrado");
        }

        Calificacion comentario = optional.get();
        comentario.setAprobado(true);
        calificacionService.save(comentario);

        return ResponseEntity.ok("Comentario aprobado");
    }
    
    
    @DeleteMapping("/comentarios/{id}/rechazar")
    public ResponseEntity<?> rechazarComentario(@PathVariable Long id) {
        Optional<Calificacion> optional = calificacionService.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentario no encontrado");
        }

        calificacionService.delete(optional.get());

        return ResponseEntity.ok("Comentario eliminado");
    }

    
    @GetMapping("/comentarios/pendientes")
    public ResponseEntity<?> verComentariosPendientes() {
        List<CalificacionVistaDTO> pendientes = calificacionService.obtenerPendientesDTO();
        return ResponseEntity.ok(pendientes);
    }


}
