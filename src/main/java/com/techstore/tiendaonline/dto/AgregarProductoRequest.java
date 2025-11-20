package com.techstore.tiendaonline.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AgregarProductoRequest {

    @NotNull(message = "El ID del producto es requerido")
    private Long idProducto;

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad = 1;

    // --- Constructores ---
    public AgregarProductoRequest() {}

    public AgregarProductoRequest(Long idProducto, Integer cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    // --- Getters y Setters ---

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}