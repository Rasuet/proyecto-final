package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Usuario (Autenticación).
 * El segundo tipo genérico (Long) debe coincidir con el tipo de la clave primaria (ID)
 * de la entidad Usuario.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Encuentra un usuario por su nombre de usuario.
     * Crucial para la lógica de login.
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Verifica si un nombre de usuario ya existe.
     * Útil para la lógica de registro (fase futura).
     */
    boolean existsByUsername(String username);
}

