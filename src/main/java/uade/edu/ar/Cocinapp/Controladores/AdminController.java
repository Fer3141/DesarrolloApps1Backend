package uade.edu.ar.Cocinapp.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.DTO.*;
import uade.edu.ar.Cocinapp.Entidades.Rol;
import uade.edu.ar.Cocinapp.Entidades.Usuario;
import uade.edu.ar.Cocinapp.Repositorios.UsuarioRepository;
import uade.edu.ar.Cocinapp.Servicios.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private recetasService recetaService;

    // agregar nueva sede
    @PostMapping("/sedes")
    public ResponseEntity<?> crearSede(@RequestBody SedeNuevaDTO dto) {
        adminService.crearSede(dto);
        return ResponseEntity.ok("sede creada");
    }

    // agregar un nuevo curso
    @PostMapping("/cursos")
    public ResponseEntity<?> crearCurso(@RequestBody CursoNuevoDTO dto) {
        adminService.crearCurso(dto);
        return ResponseEntity.ok("curso creado");
    }
    
    // agregar un nuevo cronograma
    @PostMapping("/cronogramas")
    public ResponseEntity<?> crearCronograma(@RequestBody CronogramaNuevoDTO dto) {
        adminService.crearCronograma(dto);
        return ResponseEntity.ok("cronograma creado");
    }

    // traer todas las recetas pendientes de aprobacion
    @GetMapping("/recetas/pendientes")
    public ResponseEntity<?> getRecetasPendientes(@RequestParam Long idUsuario) {
        try {
            Usuario u = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            if (u.getRol() != Rol.ADMIN) {
                throw new RuntimeException("No tenés permisos para acceder a este recurso");
            }

            List<RecetaResumenDTO> resultado = recetaService.obtenerRecetasPendientes();
            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            System.out.println("Error al obtener recetas pendientes: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
