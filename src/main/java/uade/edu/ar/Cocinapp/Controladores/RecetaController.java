package uade.edu.ar.Cocinapp.Controladores;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.DTO.RecetaDTO;
import uade.edu.ar.Cocinapp.DTO.RecetaDetalleDTO;
import uade.edu.ar.Cocinapp.DTO.RecetaResumenDTO;
import uade.edu.ar.Cocinapp.Entidades.TipoReceta;
import uade.edu.ar.Cocinapp.Servicios.recetasService;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private recetasService recetaService;


    @GetMapping("/ultimas")
    public List<RecetaResumenDTO> getUltimas() {
        return recetaService.obtenerUltimas3();
    }

    @GetMapping("/mejores")
    public List<RecetaResumenDTO> getMejores() {
        return recetaService.obtenerMejores();
    }

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

    // se agrega la receta a la lista de favoritos del usuario
    @PostMapping("/favoritos")
    public ResponseEntity<?> agregarAFavoritos(@RequestParam Long idUsuario, @RequestParam Long idReceta) {
        try {
            recetaService.agregarAFavoritos(idUsuario, idReceta);
            return ResponseEntity.ok("agregado a favoritos");
        } catch (Exception e) {
            System.out.println("error al agregar favorito: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //se obtienen los favoritos del usuario
    @GetMapping("/favoritos")
    public ResponseEntity<?> verFavoritos(@RequestParam Long usuario) {
        try {
            return ResponseEntity.ok(recetaService.obtenerFavoritos(usuario));
        } catch (Exception e) {
            System.out.println("error al obtener favoritos: " + e.getMessage());
            return ResponseEntity.status(500).body("error interno");
        }
    }

    //eliminar una receta de favoritos
    @DeleteMapping("/favoritos")
    public ResponseEntity<?> eliminarFavorito(@RequestParam Long idUsuario, @RequestParam Long idReceta) {
        try {
            recetaService.eliminarFavorito(idUsuario, idReceta);
            return ResponseEntity.ok("receta eliminada de favoritos");
        } catch (Exception e) {
            System.out.println("error al eliminar favorito: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // verificamos que el usuario no tenga una receta creada con el mismo nombre
    @GetMapping("/verificar-nombre")
    public ResponseEntity<?> verificarNombreReceta(@RequestParam Long idUsuario,
                                                    @RequestParam String nombre) {
        try {
            Optional<RecetaResumenDTO> dto = recetaService.verificarExistenciaReceta(idUsuario, nombre);

            if (dto.isPresent()) {
                return ResponseEntity.ok(Map.of("existe", true, "receta", dto.get()));
            } else {
                return ResponseEntity.ok(Map.of("existe", false));
            }

        } catch (Exception e) {
            System.out.println("error al verificar receta: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarReceta(@RequestParam Long idUsuario, @RequestParam Long idReceta) {
        try {
            recetaService.eliminarReceta(idUsuario, idReceta);
            return ResponseEntity.ok("Receta eliminada correctamente");
        } catch (Exception e) {
            System.out.println("Error al eliminar receta: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editarReceta(@RequestBody RecetaDetalleDTO dto) {
        try {
            recetaService.editarReceta(dto);
            return ResponseEntity.ok("Receta editada correctamente y reenviada para aprobación");
        } catch (Exception e) {
            System.out.println("Error al editar receta: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/mis-recetas")
    public ResponseEntity<?> getRecetasDelUsuario(@RequestParam Long idUsuario) {
        try {
            List<RecetaResumenDTO> resultado = recetaService.obtenerRecetasDelUsuario(idUsuario);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.out.println("Error al obtener recetas del usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/por-tipo")
    public ResponseEntity<?> getRecetasPorTipo(@RequestParam String tipo) {
        try {
            TipoReceta tipoEnum = TipoReceta.valueOf(tipo.toUpperCase());
            List<RecetaResumenDTO> resultado = recetaService.buscarRecetasPorTipo(tipoEnum);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Tipo de receta inválido. Debe ser: ENTRADA, PLATO_PRINCIPAL o POSTRE");
        } catch (Exception e) {
            System.out.println("Error al buscar recetas por tipo: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/por-usuario")
    public ResponseEntity<?> getRecetasPorUsuario(@RequestParam String alias) {
        try {
            List<RecetaResumenDTO> resultado = recetaService.buscarRecetasPorUsuario(alias);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.out.println("Error al buscar recetas por usuario: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/por-ingrediente")
    public ResponseEntity<?> getRecetasPorIngrediente(@RequestParam String nombre) {
        try {
            List<RecetaResumenDTO> resultado = recetaService.buscarRecetasPorIngrediente(nombre);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.out.println("Error al buscar recetas por ingrediente: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/sin-ingrediente")
    public ResponseEntity<?> getRecetasSinIngrediente(@RequestParam String nombre) {
        try {
            List<RecetaResumenDTO> resultado = recetaService.buscarRecetasSinIngrediente(nombre);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            System.out.println("Error al buscar recetas sin ingrediente: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
