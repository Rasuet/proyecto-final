package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    
    Optional<Cliente> findByUsuario(Usuario usuario);
    
    Optional<Cliente> findByEmail(String email);
    
    boolean existsByEmail(String email);
}

