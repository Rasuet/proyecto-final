package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, String> {
    
    List<Pedido> findByClienteOrderByFechaPedidoDesc(Cliente cliente);
    
    Optional<Pedido> findByClienteAndEstado(Cliente cliente, String estado);
    
    List<Pedido> findByEstado(String estado);
}

