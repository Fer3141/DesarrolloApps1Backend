package uade.edu.ar.Cocinapp.Repositorios;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.edu.ar.Cocinapp.Entidades.RegistroPendiente;

@Repository
public interface RegistroPendienteRepository extends JpaRepository<RegistroPendiente, Long> {
    boolean existsByEmail(String email);
    boolean existsByAlias(String alias);
    Optional<RegistroPendiente> findByEmail(String email);
}
