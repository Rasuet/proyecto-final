package com.techstore.tiendaonline.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pedidos")
public class Pedido {

    // El ID lo definimos como Long y Long en Factura, LineaPedido, y PedidoController
    // pero la BD lo mapeó como String/VARCHAR. Por simplicidad, lo dejamos aquí como Long.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonIgnore // Evita bucles al listar pedidos
    private Cliente cliente;

    @Column(nullable = false)
    private String estado; // ACTIVO, COMPLETADO

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;


    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaPedido> lineasPedido = new ArrayList<>();

    // --- Constructores ---
    public Pedido() {
        this.total = BigDecimal.ZERO;
        this.fechaPedido = LocalDateTime.now();
        this.estado = "ACTIVO";
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<LineaPedido> getLineasPedido() { return lineasPedido; }
    public void setLineasPedido(List<LineaPedido> lineasPedido) { this.lineasPedido = lineasPedido; }
}

