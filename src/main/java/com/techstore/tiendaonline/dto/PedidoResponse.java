package com.techstore.tiendaonline.dto;




import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para enviar al Frontend la información resumida del Pedido (Carrito).
 * Utiliza LineaPedidoDTO para los detalles.
 */
public class PedidoResponse {

    private Long idPedido;
    private LocalDateTime fechaCreacion;
    private String estado;
    private BigDecimal total;
    private List<LineaPedidoDto> lineas; // Lista de los ítems del carrito

    // Constructores, Getters y Setters
    public PedidoResponse() {}

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<LineaPedidoDto> getLineas() { return lineas; }
    public void setLineas(List<LineaPedidoDto> lineas) { this.lineas = lineas; }
}