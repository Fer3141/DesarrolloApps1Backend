package uade.edu.ar.Cocinapp.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uade.edu.ar.Cocinapp.DTO.RegistroInicialRequest;
import uade.edu.ar.Cocinapp.Servicios.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registro-inicial")
    public ResponseEntity<String> registroInicial(@RequestBody RegistroInicialRequest request) {
        String respuesta = authService.iniciarRegistro(request);
        return ResponseEntity.ok(respuesta);
    }
}
