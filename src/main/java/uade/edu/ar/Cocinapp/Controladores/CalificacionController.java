package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.DTO.CalificacionDTO;
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
}
