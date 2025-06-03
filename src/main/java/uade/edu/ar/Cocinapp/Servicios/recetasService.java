package uade.edu.ar.Cocinapp.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uade.edu.ar.Cocinapp.Repositorios.pasosRepo;
import uade.edu.ar.Cocinapp.Repositorios.recetasRepo;
import uade.edu.ar.Cocinapp.DTO.RecetaDTO;
import uade.edu.ar.Cocinapp.DTO.PasoDTO;
import uade.edu.ar.Cocinapp.Entidades.recetas;
import uade.edu.ar.Cocinapp.Entidades.pasos;

@Service
public class recetasService {
	
	@Autowired
	private recetasRepo rr;
	
	@Autowired
	private pasosRepo pr;;
	
	public void crearRecetaConPasos(RecetaDTO recetaDTO) {
        recetas receta = new recetas();
        receta.setIdUsuario(recetaDTO.getIdUsuario());
        receta.setNombreReceta(recetaDTO.getNombre());
        receta.setDescripcionReceta(recetaDTO.getDescripcion());
        receta.setFotoPrincipal(recetaDTO.getFotoPrincipal());
        receta.setPorciones(recetaDTO.getPorciones());
        receta.setCantidadPersonas(recetaDTO.getCantidadPersonas());
        receta.setIdTipo(recetaDTO.getIdTipo());

        List<PasoDTO> nuevos = new ArrayList<>();
        int contador = 1;
        for (PasoDTO paso : recetaDTO.getPasos()) {
            pasos p = new pasos();
            p.setNroPaso(contador);
            p.setTexto(paso.getTexto());
            p.setIdReceta(receta.getIdReceta());  // asignar la receta al paso
            pr.save(p);
            contador = contador + 1;
        }

        rr.save(receta); // guarda receta y pasos
    }

}
