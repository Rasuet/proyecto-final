package com.techstore.tiendaonline.config;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Pedido;
import com.techstore.tiendaonline.entity.Producto;
import com.techstore.tiendaonline.entity.Usuario;
import com.techstore.tiendaonline.repository.ClienteRepository;
import com.techstore.tiendaonline.repository.PedidoRepository;
import com.techstore.tiendaonline.repository.ProductoRepository;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Clase de configuración encargada de cargar los datos iniciales.
 * Se separa de la clase Main para evitar problemas de reinicio con DevTools.
 */
@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initData(UsuarioRepository usuarioRepo,
                                      ClienteRepository clienteRepo,
                                      ProductoRepository productoRepo,
                                      PedidoRepository pedidoRepo,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Evitar duplicados si se reinicia sin borrar BD
            if (usuarioRepo.findByUsername("cliente1").isPresent()) {
                System.out.println("--> Datos ya existentes. Omitiendo carga inicial.");
                return;
            }

            System.out.println("--> Cargando datos de prueba...");

            // 1. Crear Cliente
            Cliente cliente = new Cliente();
            cliente.setNombre("Tech");
            cliente.setApellidos("User");
            cliente.setEmail("user@techstore.com");
            cliente.setDireccion("Calle Digital 101");
            cliente.setTelefono("555-0000");
            clienteRepo.save(cliente);

            // 2. Crear Usuario (Login: cliente1 / 123456)
            Usuario usuario = new Usuario();
            usuario.setUsername("cliente1");
            usuario.setPasswordHash(passwordEncoder.encode("123456"));
            usuario.setCliente(cliente);
            usuario.setRol("CLIENTE");
            usuarioRepo.save(usuario);

            // 3. Crear Productos
            productoRepo.save(new Producto("Laptop Gamer X1", "Potente laptop con RTX 3060", new BigDecimal("1200.00"), 10, "https://placehold.co/300x200?text=Laptop"));
            productoRepo.save(new Producto("Smartphone Pro Max", "Cámara 8K y 5G", new BigDecimal("999.99"), 20, "https://placehold.co/300x200?text=Phone"));
            productoRepo.save(new Producto("Auriculares NoiseCancel", "Cancelación de ruido activa", new BigDecimal("199.50"), 50, "https://placehold.co/300x200?text=Headset"));

            // 4. Crear Carrito Inicial
            Pedido pedido = new Pedido(cliente);
            pedido.setEstado("ACTIVO");
            pedido.setTotal(BigDecimal.ZERO);
            pedidoRepo.save(pedido);

            System.out.println("--> DATOS DE PRUEBA CARGADOS EXITOSAMENTE <--");
            System.out.println("--> USUARIO: cliente1  | PASSWORD: 123456 <--");
        };
    }
}