package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uade.edu.ar.Cocinapp.DTO.*;
import uade.edu.ar.Cocinapp.Entidades.*;
import uade.edu.ar.Cocinapp.Repositorios.*;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private RecetaFavoritaRepository favoritaRepo;

    // metodo para guardar una receta completa
    public void guardarRecetaCompleta(RecetaDTO dto) {

        // validamos que el usuario exista
        Usuario usuario = usuarioRepo.findById((long) dto.idUsuario).orElseThrow(() ->
                new RuntimeException("usuario no encontrado"));

        // creamos y guardamos la receta principal
        Receta receta = new Receta();
        receta.setUsuario(usuario);
        receta.setNombreReceta(dto.nombreReceta);
        receta.setDescripcionReceta(dto.descripcionReceta);
        receta.setPorciones(dto.porciones);
        receta.setCantidadPersonas(dto.cantidadPersonas);
        receta.setFotoPrincipal(dto.fotoPrincipal);
        receta.setIdTipo(dto.idTipo);
        

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

            receta = recetaRepo.save(receta);

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

    // metodo para buscar recetas por nombre parcial (o traer todas si no se pasa nada)
    public List<RecetaResumenDTO> buscarRecetasPorNombre(String nombre) {
        List<Receta> lista;

        if (nombre == null || nombre.isEmpty()) {
            lista = recetaRepo.findAll(); // si no se pasa nombre, trae todas
        } else {
            lista = recetaRepo.findByNombreRecetaContainingIgnoreCaseOrderByIdRecetaDesc(nombre);
        }

        List<RecetaResumenDTO> resultado = new ArrayList<>();

        for (Receta r : lista) {
            resultado.add(new RecetaResumenDTO(
                r.getIdReceta(),
                r.getNombreReceta(),
                r.getFotoPrincipal(),
                r.getUsuario().getAlias() // si queremos mostrar el alias
            ));
        }

        return resultado;
    }

    // obtenemos el detalle de la receta segun el id
    public RecetaDetalleDTO obtenerDetallePorId(Long id) {
        Receta receta = recetaRepo.findById(id).orElseThrow(() ->
                new RuntimeException("receta no encontrada"));

        RecetaDetalleDTO dto = new RecetaDetalleDTO();
        dto.idReceta = receta.getIdReceta();
        dto.nombreReceta = receta.getNombreReceta();
        dto.descripcionReceta = receta.getDescripcionReceta();
        dto.fotoPrincipal = receta.getFotoPrincipal();
        dto.porciones = receta.getPorciones();
        dto.cantidadPersonas = receta.getCantidadPersonas();
        dto.idTipo = receta.getIdTipo();
        dto.nombreUsuario = receta.getUsuario().getAlias();

        // ingredientes usados en la receta
        List<Utilizado> usados = utilizadoRepo.findByReceta_IdReceta(id);
        dto.ingredientes = new ArrayList<>();
        for (Utilizado u : usados) {
            IngredienteDTO ing = new IngredienteDTO();
            ing.nombre = u.getIngrediente().getNombre();
            ing.cantidad = u.getCantidad();
            ing.unidad = u.getUnidad().getDescripcion();
            ing.observaciones = u.getObservaciones();
            dto.ingredientes.add(ing);
        }

        // pasos con multimedia
        List<pasos> pasos = pasoRepo.findByReceta_IdRecetaOrderByNroPasoAsc(id);
        dto.pasos = new ArrayList<>();
        for (pasos p : pasos) {
            PasoCompletoDTO pasoDTO = new PasoCompletoDTO();
            pasoDTO.nroPaso = p.getNroPaso();
            pasoDTO.texto = p.getTexto();

            List<Multimedia> medios = multimediaRepo.findByPaso_IdPaso(p.getIdPaso());
            pasoDTO.multimedia = new ArrayList<>();
            for (Multimedia m : medios) {
                MultimediaDTO mediaDTO = new MultimediaDTO();
                mediaDTO.tipo = m.getTipo_contenido();
                mediaDTO.extension = m.getExtension();
                mediaDTO.url = m.getUrlContenido();
                pasoDTO.multimedia.add(mediaDTO);
            }

            dto.pasos.add(pasoDTO);
        }

        return dto;
    }

    //Agregar una receta a favoritos
    public void agregarAFavoritos(Long idUsuario, Long idReceta) {
        if (favoritaRepo.existsByUsuario_IdUsuarioAndReceta_IdReceta(idUsuario, idReceta)) {
            throw new RuntimeException("la receta ya está en favoritos");
        }

        Usuario usuario = usuarioRepo.findById(idUsuario).orElseThrow(() -> new RuntimeException("usuario no encontrado"));
        Receta receta = recetaRepo.findById(idReceta).orElseThrow(() -> new RuntimeException("receta no encontrada"));

        RecetaFavorita fav = new RecetaFavorita();
        fav.setUsuario(usuario);
        fav.setReceta(receta);

        favoritaRepo.save(fav);
    }

    //Obtener lista de favoritos por usuario
    public List<RecetaResumenDTO> obtenerFavoritos(Long idUsuario) {
        List<RecetaFavorita> favoritos = favoritaRepo.findByUsuario_IdUsuario(idUsuario);

        List<RecetaResumenDTO> resultado = new ArrayList<>();
        for (RecetaFavorita f : favoritos) {
            Receta r = f.getReceta();
            resultado.add(new RecetaResumenDTO(
                r.getIdReceta(),
                r.getNombreReceta(),
                r.getFotoPrincipal(),
                r.getUsuario().getAlias()
            ));
        }

        return resultado;
    }

    //eliminar de favoritos una receta
    @Transactional
    public void eliminarFavorito(Long idUsuario, Long idReceta) {
        if (!favoritaRepo.existsByUsuario_IdUsuarioAndReceta_IdReceta(idUsuario, idReceta)) {
            throw new RuntimeException("la receta no está en favoritos");
        }

        favoritaRepo.deleteByUsuario_IdUsuarioAndReceta_IdReceta(idUsuario, idReceta);
        System.out.println("favorito eliminado correctamente");
    }
}

