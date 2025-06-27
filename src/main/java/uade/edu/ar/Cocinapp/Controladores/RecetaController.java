package uade.edu.ar.Cocinapp.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.DTO.RecetaDTO;
import uade.edu.ar.Cocinapp.DTO.RecetaDetalleDTO;
import uade.edu.ar.Cocinapp.DTO.RecetaResumenDTO;
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

    // endpoint para buscar una receta segun el nombre
    @GetMapping
    public ResponseEntity<?> buscarPorNombre(@RequestParam(required = false) String nombre) {
        try {
            List<RecetaResumenDTO> resultados = recetaService.buscarRecetasPorNombre(nombre);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            System.out.println("error al buscar recetas: " + e.getMessage());
            return ResponseEntity.status(500).body("error interno");
        }
    }

    // obtener toda la info cuando se hace click en una receta
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDetalle(@PathVariable Long id) {
        try {
            RecetaDetalleDTO detalle = recetaService.obtenerDetallePorId(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            System.out.println("error al buscar detalle de receta: " + e.getMessage());
            return ResponseEntity.status(404).body("receta no encontrada");
        }
    }

}
