package com.techstore.tiendaonline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    // CORRECCIÓN: Usamos 'password' en lugar de 'passwordHash' para coincidir con DataInitializer
    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // Relación con Cliente (Dueño de la relación)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    public Usuario() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getRol() {
        return "";
    }

    public String getPasswordHash() {

        return "";
    }


    public void setRol(String cliente) {
    }

    public void setPasswordHash(String encode) {
    }
}