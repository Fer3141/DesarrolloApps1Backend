package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import uade.edu.ar.Cocinapp.Entidades.RecetaFavorita;

import java.util.List;

public interface RecetaFavoritaRepository extends JpaRepository<RecetaFavorita, Long> {
    List<RecetaFavorita> findByUsuario_IdUsuario(Long idUsuario);
    
    boolean existsByUsuario_IdUsuarioAndReceta_IdReceta(Long idUsuario, Long idReceta);

    void deleteByUsuario_IdUsuarioAndReceta_IdReceta(Long idUsuario, Long idReceta);
}

