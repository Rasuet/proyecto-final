package com.techstore.tiendaonline.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) para recibir y validar los datos
 * del formulario de edición del perfil del cliente.
 */
public class UpdateClienteRequest {

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 100, message = "El nombre es demasiado largo.")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío.")
    @Size(max = 100, message = "El apellido es demasiado largo.")
    private String apellidos;

    @Size(max = 255, message = "La dirección es demasiado larga.")
    private String direccion;

    @Size(max = 20, message = "El teléfono no es válido.")
    private String telefono;

    // --- Getters y Setters ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

