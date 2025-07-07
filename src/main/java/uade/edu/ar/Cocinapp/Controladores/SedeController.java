package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uade.edu.ar.Cocinapp.Entidades.Sede;
import uade.edu.ar.Cocinapp.Servicios.SedeService;

import java.util.List;

@RestController
@RequestMapping("/sedes")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Sede sede) {
        Sede nueva = sedeService.crearSede(sede);
        return ResponseEntity.ok("Sede creada con ID: " + nueva.getIdSede());
    }

    @GetMapping
    public List<Sede> listar() {
        return sedeService.listarSedes();
    }
}
