package uade.edu.ar.Cocinapp.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import uade.edu.ar.Cocinapp.Entidades.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    
	@Modifying
    @Transactional
    @Query(value = "INSERT INTO alumnos (idusuario, numero_tarjeta, cuenta_corriente, foto_dni_frente, foto_dni_dorso, nro_tramite_dni) " +
                   "VALUES (:idUsuario, :numeroTarjeta, :cuentaCorriente, :fotoDniFrente, :fotoDniDorso, :nroTramiteDni)", nativeQuery = true)
    void insertarAlumno(
            @Param("idUsuario") Long idUsuario,
            @Param("numeroTarjeta") String numeroTarjeta,
            @Param("cuentaCorriente") Float cuentaCorriente,
            @Param("fotoDniFrente") String fotoDniFrente,
            @Param("fotoDniDorso") String fotoDniDorso,
            @Param("nroTramiteDni") String nroTramiteDni
    );


}
