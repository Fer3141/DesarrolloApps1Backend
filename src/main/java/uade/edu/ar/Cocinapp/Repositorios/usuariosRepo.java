package uade.edu.ar.Cocinapp.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uade.edu.ar.Cocinapp.Entidades.usuarios;

@Repository
public interface usuariosRepo extends JpaRepository<usuarios, Long>{
	
	
	
	Optional<usuarios> findByMail(String mail);
	boolean existsByMail(String mail);
    boolean existsByNickname(String nickname);

}
