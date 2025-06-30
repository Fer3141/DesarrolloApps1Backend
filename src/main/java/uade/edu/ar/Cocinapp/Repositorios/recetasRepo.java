package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uade.edu.ar.Cocinapp.Entidades.Receta;

@Repository
public interface recetasRepo extends JpaRepository<Receta, Long> {
    List<Receta> findByNombreRecetaContainingIgnoreCaseOrderByIdRecetaDesc(String nombreReceta);

    List<Receta> findTop3ByOrderByIdRecetaDesc(); //para traer las mas recientes


    List<Receta> findTop3ByAprobadaTrueOrderByIdRecetaDesc();

    List<Receta> findByAprobadaTrueAndNombreRecetaContainingIgnoreCaseOrderByIdRecetaDesc(String nombre);

    List<Receta> findByAprobadaTrue();

    List<Receta> findByUsuario_IdUsuarioOrderByIdRecetaDesc(Long idUsuario); // mis recetas

    Optional<Receta> findByUsuario_IdUsuarioAndNombreRecetaIgnoreCase(Long idUsuario, String nombreReceta); // busca si el usuario ya tiene una receta con ese nombre

    List<Receta> findByAprobadaFalseAndRechazadaFalseOrderByIdRecetaDesc(); // para el admin
}
