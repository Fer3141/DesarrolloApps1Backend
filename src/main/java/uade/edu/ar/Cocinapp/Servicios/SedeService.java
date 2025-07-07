package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.edu.ar.Cocinapp.Entidades.Sede;
import uade.edu.ar.Cocinapp.Repositorios.SedeRepository;

import java.util.List;

@Service
public class SedeService {

    @Autowired
    private SedeRepository sedeRepo;

    public Sede crearSede(Sede sede) {
        return sedeRepo.save(sede);
    }

    public List<Sede> listarSedes() {
        return sedeRepo.findAll();
    }

    public Sede buscarPorId(Long id) {
        return sedeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
    }
}
