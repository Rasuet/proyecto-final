package com.techstore.tiendaonline.dto;

import java.math.BigDecimal;

/**
 * DTO para transportar los datos de una línea de pedido de forma plana.
 * Necesario para que PedidoResponse compile.
 */
public class LineaPedidoDTO {

    private Long idLinea;
    private Long idProducto;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Constructores
    public LineaPedidoDTO() {}

    public LineaPedidoDTO(Long idLinea, Long idProducto, String nombreProducto, Integer cantidad, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.idLinea = idLinea;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Getters y Setters
    public Long getIdLinea() { return idLinea; }
    public void setIdLinea(Long idLinea) { this.idLinea = idLinea; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}