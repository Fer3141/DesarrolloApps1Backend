package uade.edu.ar.Cocinapp.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uade.edu.ar.Cocinapp.DTO.CronogramaDTO;
import uade.edu.ar.Cocinapp.Entidades.CronogramaCurso;
import uade.edu.ar.Cocinapp.Entidades.Curso;
import uade.edu.ar.Cocinapp.Entidades.Sede;
import uade.edu.ar.Cocinapp.Repositorios.CronogramaCursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.CursoRepository;
import uade.edu.ar.Cocinapp.Repositorios.SedeRepository;

@Service
public class CronogramaCursoService {
	
	
    @Autowired
    private CronogramaCursoRepository cronogramaRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private SedeRepository sedeRepo;

	
	public void crearCronograma(CronogramaDTO dto) {
	    Curso curso = cursoRepo.findById(dto.getIdCurso())
	        .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
	    Sede sede = sedeRepo.findById(dto.getIdSede())
	        .orElseThrow(() -> new RuntimeException("Sede no encontrada"));

	    CronogramaCurso c = new CronogramaCurso();
	    c.setCurso(curso);
	    c.setSede(sede);
	    c.setFechaInicio(dto.getFechaInicio());
	    c.setFechaFin(dto.getFechaFin());
	    c.setVacantesDisponibles(dto.getVacantesDisponibles());

	    cronogramaRepo.save(c);
	}

	
}
