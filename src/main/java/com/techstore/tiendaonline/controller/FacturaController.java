package com.techstore.tiendaonline.controller;

import com.techstore.tiendaonline.entity.Factura;
import com.techstore.tiendaonline.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return facturaRepository.findByPedidoId(idPedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}