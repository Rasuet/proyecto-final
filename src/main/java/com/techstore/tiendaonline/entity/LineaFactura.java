package com.techstore.tiendaonline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "linea_factura")
public class LineaFactura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_linea_factura")
    private String idLineaFactura;
    
    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;
    
    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String descripcion;
    
    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer cantidad;
    
    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "precio_unitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;
    
    @NotNull
    @DecimalMin(value = "0.00")
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;
    
    // Constructores
    public LineaFactura() {}
    
    public LineaFactura(Factura factura, String descripcion, Integer cantidad, BigDecimal precioUnitario) {
        this.factura = factura;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
    
    // Método para recalcular subtotal
    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario.multiply(BigDecimal.valueOf(this.cantidad));
    }
    
    // Getters y Setters
    public String getIdLineaFactura() {
        return idLineaFactura;
    }
    
    public void setIdLineaFactura(String idLineaFactura) {
        this.idLineaFactura = idLineaFactura;
    }
    
    public Factura getFactura() {
        return factura;
    }
    
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }
    
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    
    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}

