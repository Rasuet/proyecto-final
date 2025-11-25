package com.techstore.tiendaonline.controller;

import com.techstore.tiendaonline.entity.Factura;
import com.techstore.tiendaonline.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional; // Asegúrate de que esta importación existe

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaRepository facturaRepository;

    @Autowired
    public FacturaController(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    /**
     * Busca la factura asociada a un ID de pedido.
     */
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Factura> getFacturaPorPedido(@PathVariable Long idPedido) {

        Optional<Factura> facturaOptional = facturaRepository.findByPedidoId(Long.valueOf(idPedido));

        // SOLUCIÓN FINAL: Evitamos el método .map() que causa ambigüedad en el compilador.
        // Verificamos explícitamente si el Optional tiene un valor.
        if (facturaOptional.isPresent()) {
            // Si la factura existe, devolvemos la respuesta OK (200) con la factura
            return ResponseEntity.ok(facturaOptional.get());
        } else {
            // Si no se encuentra, devolvemos 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}