package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.DTO.*;
import uade.edu.ar.Cocinapp.Servicios.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/sedes")
    public ResponseEntity<?> crearSede(@RequestBody SedeNuevaDTO dto) {
        adminService.crearSede(dto);
        return ResponseEntity.ok("sede creada");
    }

    @PostMapping("/cursos")
    public ResponseEntity<?> crearCurso(@RequestBody CursoNuevoDTO dto) {
        adminService.crearCurso(dto);
        return ResponseEntity.ok("curso creado");
    }

    @PostMapping("/cronogramas")
    public ResponseEntity<?> crearCronograma(@RequestBody CronogramaNuevoDTO dto) {
        adminService.crearCronograma(dto);
        return ResponseEntity.ok("cronograma creado");
    }
}
