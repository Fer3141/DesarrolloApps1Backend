package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.Servicios.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // devuelve la lista de cursos disponibles con info de sede, fechas y promociones
    @GetMapping
    public ResponseEntity<?> listarCursos() {
        try {
            return ResponseEntity.ok(cursoService.obtenerCursosDisponibles());
        } catch (Exception e) {
            System.out.println("error al listar cursos: " + e.getMessage());
            return ResponseEntity.status(500).body("error interno");
        }
    }

    // sirve para inscribir un alumno a un curso
    @PostMapping("/inscribirse")
    public ResponseEntity<?> inscribirse(
            @RequestParam Long idAlumno,
            @RequestParam Long idCronograma) {

        try {
            cursoService.inscribirseACurso(idAlumno, idCronograma);
            return ResponseEntity.ok("inscripción realizada con éxito");
        } catch (Exception e) {
            System.out.println("error en inscripción: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/mis-cursos")
    public ResponseEntity<?> verMisCursos(@RequestParam Long idAlumno) {
        try {
            return ResponseEntity.ok(cursoService.obtenerCursosInscripto(idAlumno));
        } catch (Exception e) {
            System.out.println("error al obtener cursos del alumno: " + e.getMessage());
            return ResponseEntity.status(500).body("error interno");
        }
    }
}

