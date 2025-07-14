package uade.edu.ar.Cocinapp.Controladores;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uade.edu.ar.Cocinapp.DTO.CursoConCronogramasDTO;
import uade.edu.ar.Cocinapp.Entidades.CronogramaCurso;
import uade.edu.ar.Cocinapp.Entidades.Curso;
import uade.edu.ar.Cocinapp.Servicios.AsistenciaService;
import uade.edu.ar.Cocinapp.Servicios.CursoService;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    
    @Autowired
    private AsistenciaService as;

    // devuelve la lista de cursos disponibles con info de sede, fechas y promociones
    @GetMapping
    public ResponseEntity<?> listarCursos() {
        try {
            return ResponseEntity.ok(cursoService.obtenerCursosDisponibles());
        } catch (Exception e) {
            System.out.println("error al listar cursos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("error interno");
        }
    }
    
    @GetMapping("/obtenerCursos")
    public ResponseEntity<?> obtenerCursos() {
    	 try {
    	        return ResponseEntity.ok(cursoService.obtenerCursosDisponibles2());
    	    } catch (Exception e) {
    	        System.out.println("error al listar cursos: " + e.getMessage());
    	        e.printStackTrace();
    	        return ResponseEntity.status(500).body("error interno");
    	    }
    }

    // sirve para inscribir un alumno a un curso
    @PostMapping("/inscribirse")
    public ResponseEntity<?> inscribirse(
            @RequestParam Long idAlumno,
            @RequestParam Long idCronograma) {

        try {
        	System.out.println("ID Alumno recibido: " + idAlumno);
            System.out.println("ID Cronograma recibido: " + idCronograma);
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
    
    @PostMapping("/asistencia/checkin-qr")
    public ResponseEntity<?> marcarAsistenciaPorQR(
        @RequestParam Long idAlumno,
        @RequestParam String qr) {

        try {
        	
        	 System.out.println("===== Asistencia QR recibida =====");
        	    System.out.println("ID Alumno: " + idAlumno);
        	    System.out.println("QR recibido (idCronograma): " + qr);
        	    
            cursoService.marcarAsistenciaPorQR(idAlumno, qr);
            return ResponseEntity.ok("Asistencia registrada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/crearCurso")
    public ResponseEntity<?> crearCurso(@RequestBody Curso curso) {
        try {
            Curso nuevo = cursoService.crearCurso(curso);
            return ResponseEntity.ok("Curso creado con ID: " + nuevo.getIdCurso());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear curso: " + e.getMessage());
        }
    }

    @PostMapping("/crear-cronograma")
    public ResponseEntity<?> crearCronograma(
            @RequestParam Long idCurso,
            @RequestParam Long idSede,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam int vacantes) {
        try {
            LocalDate fInicio = LocalDate.parse(fechaInicio);
            LocalDate fFin = LocalDate.parse(fechaFin);

            CronogramaCurso nuevo = cursoService.crearCronograma(idCurso, idSede, fInicio, fFin, vacantes);
            return ResponseEntity.ok("Cronograma creado con ID: " + nuevo.getIdCronograma());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear cronograma: " + e.getMessage());
        }
    }
    
    
    @GetMapping("/cursos/{id}")
    public ResponseEntity<CursoConCronogramasDTO> detalleCurso(@PathVariable Long id) {
    	  CursoConCronogramasDTO dto = cursoService.obtenerCursoConCronogramas(id);
          return ResponseEntity.ok(dto);
    }
    
}

