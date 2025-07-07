package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.edu.ar.Cocinapp.DTO.CalificacionDTO;
import uade.edu.ar.Cocinapp.DTO.CalificacionVistaDTO;
import uade.edu.ar.Cocinapp.Entidades.*;
import uade.edu.ar.Cocinapp.Repositorios.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private recetasRepo recetaRepo;

    // guarda una calificacion nueva
    public void guardarCalificacion(CalificacionDTO dto) {

        Usuario usuario = usuarioRepo.findById(dto.idUsuario)
                .orElseThrow(() -> new RuntimeException("usuario no encontrado"));

        Receta receta = recetaRepo.findById(dto.idReceta)
                .orElseThrow(() -> new RuntimeException("receta no encontrada"));

        Calificacion c = new Calificacion();
        c.setUsuario(usuario);
        c.setReceta(receta);
        c.setCalificacion(dto.calificacion);
        c.setComentarios(dto.comentarios);
        c.setAprobado(false);

        calificacionRepo.save(c);
        System.out.println("nueva calificacion guardada");
    }

    // devuelve todas las valoraciones que tiene una receta especifica
    public List<CalificacionVistaDTO> obtenerPorReceta(Long idReceta) {
        return calificacionRepo.findAll().stream()
                .filter(c -> c.getReceta().getIdReceta().equals(idReceta))
                .filter(c -> Boolean.TRUE.equals(c.isAprobado())) //agrega campo aprobado, las recetas solo mostraran comentarios aprobados

                .map(c -> new CalificacionVistaDTO(
                        c.getUsuario().getAlias(),
                        c.getCalificacion(),
                        c.getComentarios(),
                        c.isAprobado())) 

                .collect(Collectors.toList());
    }
    
    
    public Optional<Calificacion> findById(Long id) {
        return calificacionRepo.findById(id);
    }

    public void save(Calificacion calificacion) {
    	calificacionRepo.save(calificacion);
    }

    public void delete(Calificacion calificacion) {
    	calificacionRepo.delete(calificacion);
    }
    
    
    public List<Calificacion> obtenerComentariosPendientes() {
        return calificacionRepo.findByAprobadoFalse();
    }
    



}
