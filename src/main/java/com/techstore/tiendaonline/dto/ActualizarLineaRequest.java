package com.techstore.tiendaonline.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ActualizarLineaRequest {
    
    @NotBlank(message = "El ID de la línea de pedido es requerido")
    private String idLineaPedido;
    
    @NotNull(message = "La cantidad es requerida")
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    private Integer cantidad;
    
    // Constructores
    public ActualizarLineaRequest() {}
    
    public ActualizarLineaRequest(String idLineaPedido, Integer cantidad) {
        this.idLineaPedido = idLineaPedido;
        this.cantidad = cantidad;
    }
    
    // Getters y Setters
    public String getIdLineaPedido() {
        return idLineaPedido;
    }
    
    public void setIdLineaPedido(String idLineaPedido) {
        this.idLineaPedido = idLineaPedido;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}

