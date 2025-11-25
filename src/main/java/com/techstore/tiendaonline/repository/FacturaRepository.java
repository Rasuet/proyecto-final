package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Factura;
import com.techstore.tiendaonline.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {


    Optional<Factura> findByPedidoId(Long idPedido);

}

