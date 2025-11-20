package com.techstore.tiendaonline.repository;


import com.techstore.tiendaonline.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio oficial para la entidad Producto.
 * Al extender JpaRepository, obtienes findAll(), findById(), save(), delete() gratis.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Método personalizado útil: Buscar productos con stock bajo
    List<Producto> findByStockLessThan(Integer stockMinimo);

    // Método útil: Buscar productos que contengan cierto texto en el nombre
    List<Producto> findByNombreContainingIgnoreCase(String texto);
}

