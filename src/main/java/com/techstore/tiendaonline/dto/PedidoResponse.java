package com.techstore.tiendaonline.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponse {

    private Long id;
    private LocalDateTime fechaPedido;
    private String estado;
    private BigDecimal total;

    private List<LineaPedidoDTO> lineasPedido;

    public PedidoResponse() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDateTime fechaPedido) { this.fechaPedido = fechaPedido; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<LineaPedidoDTO> getLineasPedido() { return lineasPedido; }
    public void setLineasPedido(List<LineaPedidoDTO> lineasPedido) { this.lineasPedido = lineasPedido; }
}