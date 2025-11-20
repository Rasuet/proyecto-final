package com.techstore.tiendaonline.controller;

import com.techstore.tiendaonline.entity.Producto;
import com.techstore.tiendaonline.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * R2.0: Muestra una lista de todos los productos disponibles.
     */
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();

        if (productos.isEmpty()) {
            // Devolvemos 204 No Content si no hay productos, para que el JS lo maneje
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    /**
     * Endpoint extra: Obtener un solo producto (útil para detalle o validación)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}