package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uade.edu.ar.Cocinapp.Entidades.Multimedia;

public interface MultimediaRepository extends JpaRepository<Multimedia, Integer> {
    List<Multimedia> findByPaso_IdPaso(Long idPaso);

    @Modifying
    @Query("DELETE FROM Multimedia m WHERE m.paso.idPaso IN (SELECT p.idPaso FROM pasos p WHERE p.receta.idReceta = :idReceta)")
    void deleteByRecetaIdIndirecto(@Param("idReceta") Long idReceta);
}