package com.techstore.tiendaonline.service;


import com.techstore.tiendaonline.entity.Producto;
import com.techstore.tiendaonline.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión del catálogo de productos y control de inventario.
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Obtiene todos los productos disponibles en la tienda.
     * Útil para la página principal (R2.0).
     */
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su ID.
     * @param id ID del producto.
     * @return Optional con el producto si existe.
     */
    @Transactional(readOnly = true)
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Método para verificar si hay suficiente stock de un producto.
     * @param productoId ID del producto.
     * @param cantidadRequerida Cantidad que se desea comprar.
     * @return true si hay stock suficiente.
     */
    public boolean hayStockDisponible(Long productoId, int cantidadRequerida) {
        return productoRepository.findById(productoId)
                .map(producto -> producto.getStock() >= cantidadRequerida)
                .orElse(false);
    }

    /**
     * Guarda o actualiza un producto (Utilidad para administradores o carga inicial).
     */
    @Transactional
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }
}