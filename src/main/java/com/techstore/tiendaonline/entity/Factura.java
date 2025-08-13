package com.techstore.tiendaonline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "facturas")
public class Factura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_factura")
    private String idFactura;
    
    @NotNull
    @Column(name = "fecha_factura", nullable = false)
    private LocalDateTime fechaFactura = LocalDateTime.now();
    
    @NotNull
    @DecimalMin(value = "0.00")
    @Column(name = "importe_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal importeTotal;
    
    @OneToOne
    @JoinColumn(name = "id_pedido", unique = true, nullable = false)
    private Pedido pedido;
    
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LineaFactura> lineasFactura;
    
    // Constructores
    public Factura() {}
    
    public Factura(Pedido pedido, BigDecimal importeTotal) {
        this.pedido = pedido;
        this.importeTotal = importeTotal;
        this.fechaFactura = LocalDateTime.now();
    }
    
    // Getters y Setters
    public String getIdFactura() {
        return idFactura;
    }
    
    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }
    
    public LocalDateTime getFechaFactura() {
        return fechaFactura;
    }
    
    public void setFechaFactura(LocalDateTime fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
    
    public BigDecimal getImporteTotal() {
        return importeTotal;
    }
    
    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
    
    public Pedido getPedido() {
        return pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    public List<LineaFactura> getLineasFactura() {
        return lineasFactura;
    }
    
    public void setLineasFactura(List<LineaFactura> lineasFactura) {
        this.lineasFactura = lineasFactura;
    }
}

