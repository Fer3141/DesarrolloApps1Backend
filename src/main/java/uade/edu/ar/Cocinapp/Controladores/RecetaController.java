package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.DTO.RecetaDTO;
import uade.edu.ar.Cocinapp.Servicios.recetasService;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private recetasService recetaService;

    // endpoint para carga unificada de receta
    @PostMapping
    public ResponseEntity<?> crearReceta(@RequestBody RecetaDTO recetaDTO) {
        try {
            recetaService.guardarRecetaCompleta(recetaDTO);
            return ResponseEntity.ok("receta creada correctamente");
        } catch (Exception e) {
            System.out.println("error al crear receta: " + e.getMessage());
            return ResponseEntity.status(500).body("error al guardar la receta");
        }
    }
}
