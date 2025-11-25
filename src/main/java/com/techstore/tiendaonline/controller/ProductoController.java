package com.techstore.tiendaonline.controller;

import com.techstore.tiendaonline.entity.Producto;
import com.techstore.tiendaonline.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; // <-- Importación necesaria
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Obtiene el listado completo de productos disponibles.
     * Hacemos el método transaccional para asegurar que se puedan cargar
     * las colecciones de las entidades sin errores de Lazy Loading.
     */
    @GetMapping
    @Transactional(readOnly = true) // <-- SOLUCIÓN CRÍTICA PARA LECTURA
    public ResponseEntity<List<Producto>> getProductos() {
        try {
            // Usa findAll() para obtener todos los productos de la BD
            List<Producto> productos = productoRepository.findAll();

            if (productos.isEmpty()) {
                // Devuelve 204 No Content si la lista está vacía
                return ResponseEntity.noContent().build();
            }

            // Devuelve 200 OK con la lista de productos
            return ResponseEntity.ok(productos);

        } catch (Exception e) {
            // Imprime el error en la consola del servidor para depuración
            e.printStackTrace();
            // Devuelve 500 Internal Server Error si falla la base de datos o el mapeo
            return ResponseEntity.internalServerError().build();
        }
    }
}