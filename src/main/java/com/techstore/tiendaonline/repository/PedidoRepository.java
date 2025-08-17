package com.techstore.tiendaonline.repository;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByClienteAndEstado(Cliente cliente, String estado);

    /**
     * CORRECCIÓN CRÍTICA: Añadido 'DISTINCT'.
     * Esto asegura que si el pedido tiene 5 líneas, JPA no devuelva 5 filas repetidas del pedido,
     * sino un solo objeto Pedido con la lista de 5 líneas dentro.
     * Esto evita que se sobrescriban o pierdan datos al guardar.
     */
    @Query("SELECT DISTINCT p FROM Pedido p " +
            "LEFT JOIN FETCH p.lineasPedido lp " +
            "LEFT JOIN FETCH lp.producto " +
            "WHERE p.cliente = :cliente AND p.estado = :estado")
    Optional<Pedido> findByClienteAndEstadoWithLineas(@Param("cliente") Cliente cliente,
                                                      @Param("estado") String estado);

    List<Pedido> findByClienteOrderByFechaPedidoDesc(Cliente cliente);
}