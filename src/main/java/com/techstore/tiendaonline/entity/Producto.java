package com.techstore.tiendaonline.entity;

import com.techstore.tiendaonline.repository.ProductoRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {

    @Bean
    CommandLineRunner cargarProductos(ProductoRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Producto("Teclado RGB", "Teclado mecánico con luces", new BigDecimal("49.99"), "/img/teclado.jpg"));
                repo.save(new Producto("Ratón Gamer", "Ratón óptico de alta precisión", new BigDecimal("29.99"), "/img/raton.jpg"));
                repo.save(new Producto("Monitor 4K", "Monitor UHD de 27 pulgadas", new BigDecimal("299.99"), "/img/monitor.jpg"));
            }
        };
    }
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_producto")
    private String idProducto;
    
    @NotBlank
    @Size(max = 255)
    @Column(unique = true, nullable = false)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @NotNull
    @DecimalMin(value = "0.01")
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precio;
    
    @Size(max = 255)
    @Column(name = "ruta_imagen")
    private String rutaImagen;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LineaPedido> lineasPedido;
    
    // Constructores
    public Producto() {}
    
    public Producto(String nombre, String descripcion, BigDecimal precio, String rutaImagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.rutaImagen = rutaImagen;
    }
    
    // Getters y Setters
    public String getIdProducto() {
        return idProducto;
    }
    
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public String getRutaImagen() {
        return rutaImagen;
    }
    
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    
    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }
    
    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }
}

