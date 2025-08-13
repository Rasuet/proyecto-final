package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.LineaPedido;
import com.techstore.tiendaonline.entity.Pedido;
import com.techstore.tiendaonline.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LineaPedidoRepository extends JpaRepository<LineaPedido, String> {
    
    List<LineaPedido> findByPedido(Pedido pedido);
    
    Optional<LineaPedido> findByPedidoAndProducto(Pedido pedido, Producto producto);
}

