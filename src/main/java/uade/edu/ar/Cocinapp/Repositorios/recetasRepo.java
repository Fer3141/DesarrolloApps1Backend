package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import uade.edu.ar.Cocinapp.Entidades.Receta;
import uade.edu.ar.Cocinapp.Entidades.TipoReceta;

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

    List<Receta> findByAprobadaTrueAndTipoOrderByIdRecetaDesc(TipoReceta tipo); //filtro pr tipo

    List<Receta> findByUsuario_AliasIgnoreCaseAndAprobadaTrueOrderByIdRecetaDesc(String alias);


    //Para filtrar por un ingrediente hacemos esta consulta SQL para "unir" todas las tablas de recetas, utilizados, ingredientes"
    @Query("""
        SELECT DISTINCT r FROM Receta r
        JOIN Utilizado u ON u.receta = r
        JOIN Ingrediente i ON u.ingrediente = i
        WHERE LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombreIngrediente, '%'))
        AND r.aprobada = true
        ORDER BY r.idReceta DESC
    """)
    List<Receta> findByIngrediente(String nombreIngrediente);

    @Query("""
        SELECT r FROM Receta r
        WHERE r.aprobada = true
        AND NOT EXISTS (
            SELECT 1 FROM Utilizado u
            JOIN Ingrediente i ON u.ingrediente = i
            WHERE u.receta = r
            AND LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombreIngrediente, '%'))
        )
        ORDER BY r.idReceta DESC
    """)
    List<Receta> findRecetasSinIngrediente(String nombreIngrediente);

}
