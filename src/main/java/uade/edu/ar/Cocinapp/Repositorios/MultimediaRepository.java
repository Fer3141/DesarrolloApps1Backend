package uade.edu.ar.Cocinapp.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import uade.edu.ar.Cocinapp.Entidades.Multimedia;

public interface MultimediaRepository extends JpaRepository<Multimedia, Integer> {
    List<Multimedia> findByPaso_IdPaso(Long idPaso);

    /**
         * Borra en cascada todos los Multimedia asociados a los Pasos
         * de la Receta cuyo idReceta es el que pasamos.
         */
        @Modifying
        @Transactional
        @Query("""
            DELETE 
            FROM Multimedia m 
            WHERE m.paso.receta.idReceta = :idReceta
        """)
    void deleteByRecetaIdIndirecto(@Param("idReceta") Long idReceta);
}