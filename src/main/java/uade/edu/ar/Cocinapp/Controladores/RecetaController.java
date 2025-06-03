package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uade.edu.ar.Cocinapp.DTO.RecetaDTO;
import uade.edu.ar.Cocinapp.Servicios.recetasService;


@RestController
@RequestMapping("/api/recetas")
public class RecetaController {
	
	@Autowired
    private recetasService rs;
	
	@PostMapping("/crear")
    public ResponseEntity<?> crearReceta(@RequestBody RecetaDTO recetaDTO) {
        rs.crearRecetaConPasos(recetaDTO);
        return ResponseEntity.ok("Receta creada");
    }
	
	

}
