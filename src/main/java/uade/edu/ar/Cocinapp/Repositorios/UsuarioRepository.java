package uade.edu.ar.Cocinapp.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uade.edu.ar.Cocinapp.Entidades.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByAlias(String alias);
    Optional<Usuario> findByEmail(String email);
}

