package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Cliente.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Necesitamos buscar al Cliente por el ID del Usuario asociado para el login
    Optional<Cliente> findByUsuarioId(Long usuarioId);
}

