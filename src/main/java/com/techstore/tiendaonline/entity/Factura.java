package com.techstore.tiendaonline.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Relación 1:1 con Pedido.
    // Usamos JoinColumn para especificar la columna de la clave foránea
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", unique = true, nullable = false)
    @JsonIgnore
    private Pedido pedido;

    // --- Constructores ---

    public Factura() {
        this.fechaEmision = LocalDateTime.now();
    }

    /**
     * Constructor usado para crear la factura a partir del pedido confirmado.
     * @param pedido El pedido ya guardado.
     * @param total El total del pedido.
     */
    public Factura(Pedido pedido, BigDecimal total) {
        this();
        this.pedido = pedido;
        this.total = total;
    }

    // --- Getters y Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}