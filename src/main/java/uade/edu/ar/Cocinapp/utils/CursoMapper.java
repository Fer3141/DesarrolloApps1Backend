package uade.edu.ar.Cocinapp.utils;

import uade.edu.ar.Cocinapp.DTO.CronogramaDTO;
import uade.edu.ar.Cocinapp.DTO.CursoConCronogramasDTO;
import uade.edu.ar.Cocinapp.DTO.CursoResumenDTO;
import uade.edu.ar.Cocinapp.Entidades.CronogramaCurso;
import uade.edu.ar.Cocinapp.Entidades.Curso;
import uade.edu.ar.Cocinapp.Entidades.Sede;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CursoMapper {

    public static CursoConCronogramasDTO mapearCurso(Curso curso) {
        if (curso == null) {
            System.out.println(">>> ERROR: Curso es null");
            return null;
        }

        System.out.println(">>> CURSO: " + curso.getDescripcion());

        List<CronogramaDTO> cronogramasDTO = new ArrayList<>();

        if (curso.getCronogramas() != null) {
            for (CronogramaCurso cronograma : curso.getCronogramas()) {
                Sede sede = cronograma.getSede();
                System.out.println(">>> CRONO ID: " + cronograma.getIdCronograma());
                System.out.println(">>> SEDE: " + (sede != null ? sede.getNombreSede() : "null"));

                CronogramaDTO dto = new CronogramaDTO(
                        cronograma.getIdCronograma(),
                        cronograma.getFechaInicio(),
                        cronograma.getFechaFin(),
                        sede != null ? sede.getNombreSede() : null,
                        sede != null ? sede.getDireccionSede() : null,
                        cronograma.getVacantesDisponibles(),
                        cronograma.getQrId()
                );

                cronogramasDTO.add(dto);
            }
        } else {
            System.out.println(">>> CRONOGRAMAS del curso son null");
        }


        
        return new CursoConCronogramasDTO(
                curso.getIdCurso(),
                curso.getDescripcion(),
                curso.getContenidos(),
                curso.getRequerimientos(),
                curso.getDuracion(),
                curso.getPrecio(),
                curso.getModalidad(),
                cronogramasDTO
        );
    }
    
    
    
    public static CursoResumenDTO mapearResumen(Curso curso) {
        return new CursoResumenDTO(
            curso.getIdCurso(),
            curso.getDescripcion(),
            curso.getModalidad(),
            curso.getDuracion(),
            curso.getPrecio()
        );
    }

}
