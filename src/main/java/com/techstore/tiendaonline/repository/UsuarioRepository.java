package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    /**
     * ESTA ES LA CLAVE: Trae al Usuario Y sus datos de Cliente juntos.
     * Sin esto, la aplicación falla al intentar leer el perfil.
     */
    @Query("SELECT u FROM Usuario u JOIN FETCH u.cliente WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithCliente(String username);
}
