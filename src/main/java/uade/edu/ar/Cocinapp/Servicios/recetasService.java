package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.edu.ar.Cocinapp.DTO.*;
import uade.edu.ar.Cocinapp.Entidades.*;
import uade.edu.ar.Cocinapp.Repositorios.*;

import java.util.*;

@Service
public class recetasService {

    @Autowired
    private recetasRepo recetaRepo;

    @Autowired
    private pasosRepo pasoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private IngredienteRepository ingredienteRepo;

    @Autowired
    private UnidadRepository unidadRepo;

    @Autowired
    private UtilizadoRepository utilizadoRepo;

    @Autowired
    private MultimediaRepository multimediaRepo;

    // metodo para guardar una receta completa
    public void guardarRecetaCompleta(RecetaDTO dto) {

        // validamos que el usuario exista
        Usuario usuario = usuarioRepo.findById((long) dto.idUsuario).orElseThrow(() ->
                new RuntimeException("usuario no encontrado"));

        // creamos y guardamos la receta principal
        recetas receta = new recetas();
        receta.setUsuario(usuario);
        receta.setNombreReceta(dto.nombreReceta);
        receta.setDescripcionReceta(dto.descripcionReceta);
        receta.setPorciones(dto.porciones);
        receta.setCantidadPersonas(dto.cantidadPersonas);
        receta.setFotoPrincipal(dto.fotoPrincipal);
        receta.setIdTipo(dto.idTipo);

        receta = recetaRepo.save(receta);
        System.out.println("receta guardada con id: " + receta.getIdReceta());

        // guardamos los ingredientes utilizados
        for (IngredienteDTO ing : dto.ingredientes) {

            // buscamos o creamos el ingrediente
            Ingrediente ingrediente = ingredienteRepo.findByNombreIgnoreCase(ing.nombre).orElseGet(() -> {
                Ingrediente nuevo = new Ingrediente();
                nuevo.setNombre(ing.nombre);
                return ingredienteRepo.save(nuevo);
            });

            // buscamos o creamos la unidad
            Unidad unidad = unidadRepo.findByDescripcionIgnoreCase(ing.unidad).orElseGet(() -> {
                Unidad nueva = new Unidad();
                nueva.setDescripcion(ing.unidad);
                return unidadRepo.save(nueva);
            });

            // creamos el objeto utilizado
            Utilizado usado = new Utilizado();
            usado.setReceta(receta);
            usado.setIngrediente(ingrediente);
            usado.setCantidad(ing.cantidad);
            usado.setUnidad(unidad);
            usado.setObservaciones(ing.observaciones);

            utilizadoRepo.save(usado);
        }

        System.out.println("ingredientes cargados correctamente");

        // guardamos los pasos
        for (PasoCompletoDTO pasoDto : dto.pasos) {
            pasos paso = new pasos();
            paso.setReceta(receta);
            paso.setNroPaso(pasoDto.nroPaso);
            paso.setTexto(pasoDto.texto);
            paso = pasoRepo.save(paso);

            if (pasoDto.multimedia != null) {
                for (MultimediaDTO media : pasoDto.multimedia) {
                    Multimedia m = new Multimedia();
                    m.setPaso(paso);
                    m.setTipo_contenido(media.tipo);
                    m.setExtension(media.extension);
                    m.setUrlContenido(media.url);
                    multimediaRepo.save(m);
                }
            }
        }

        System.out.println("pasos y multimedia cargados correctamente");
    }
}
