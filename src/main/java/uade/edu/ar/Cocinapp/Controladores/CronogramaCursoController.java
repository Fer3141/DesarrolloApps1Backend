package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uade.edu.ar.Cocinapp.DTO.CronogramaDTO;
import uade.edu.ar.Cocinapp.Servicios.CronogramaCursoService;

@RestController
@RequestMapping("/cronogramas")
public class CronogramaCursoController {

    @Autowired
    private CronogramaCursoService cronogramaService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CronogramaDTO dto) {
        try {
            cronogramaService.crearCronograma(dto);
            return ResponseEntity.ok("Cronograma creado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
