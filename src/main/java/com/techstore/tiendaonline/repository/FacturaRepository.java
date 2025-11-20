package com.techstore.tiendaonline.repository;


import com.techstore.tiendaonline.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Factura.
 * Permite guardar y consultar las facturas generadas.
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    /**
     * Busca la factura asociada a un pedido específico.
     * Útil para mostrar el detalle de compra al usuario (R3.1).
     * * @param idPedido El ID del pedido.
     * @return Un Optional que contiene la factura si existe.
     */
    Optional<Factura> findByPedidoId(Long idPedido);
}
