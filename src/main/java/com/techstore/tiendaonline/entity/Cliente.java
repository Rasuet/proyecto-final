package com.techstore.tiendaonline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente {

    // 1. CORREGIDO: ID es Long para coincidir con el INT AUTO_INCREMENT en H2
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String apellidos;

    @Size(max = 255)
    private String direccion;

    @Size(max = 20)
    private String telefono;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true, nullable = false)
    private String email;

    // 2. CORREGIDO: El mapeo se hace con 'mappedBy' porque la tabla 'usuarios'
    // es la que contiene la llave foránea (id_cliente).
    // Esta entidad 'Cliente' no es la propietaria de la relación.
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Usuario usuario;

    // Supongo que 'Pedido' es tu 'Reserva' (Reservas/Pedidos)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;

    // Constructores
    public Cliente() {}

    public Cliente(String nombre, String apellidos, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Mantiene la coherencia de la relación bidireccional
        if (usuario != null && usuario.getCliente() != this) {
            usuario.setCliente(this);
        }
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}