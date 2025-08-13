package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, String> {
    
    Optional<Producto> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
}

