package com.techstore.tiendaonline.config;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Producto;
import com.techstore.tiendaonline.entity.Usuario;
import com.techstore.tiendaonline.repository.ClienteRepository;
import com.techstore.tiendaonline.repository.ProductoRepository;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(ProductoRepository productoRepository,
                                   UsuarioRepository usuarioRepository,
                                   ClienteRepository clienteRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. CREAR PRODUCTOS (Si no existen)
            if (productoRepository.count() == 0) {
                // NOTA: Asegúrate de que en C:/imagenes_tienda/ los archivos se llamen:
                // laptop.avif, phone.avif, headset.avif
                productoRepository.save(new Producto("Laptop Gamer", "Potente laptop para juegos.", new BigDecimal("1200.00"), 10, "/images/laptop.avif"));
                productoRepository.save(new Producto("Smartphone Pro", "Teléfono última generación.", new BigDecimal("800.00"), 20, "/images/phone.avif"));
                productoRepository.save(new Producto("Auriculares", "Cancelación de ruido.", new BigDecimal("150.00"), 50, "/images/headset.avif"));

                System.out.println("--> PRODUCTOS CREADOS CON RUTAS AVIF <--");
            }

            // 2. GESTIONAR USUARIO (Borrar y Recrear para evitar errores)
            if (usuarioRepository.existsByUsername("cliente1")) {
                Usuario oldUser = usuarioRepository.findByUsername("cliente1").get();
                usuarioRepository.delete(oldUser);
                usuarioRepository.flush();
                System.out.println("--> Usuario antiguo borrado para limpieza.");
            }

            // Crear usuario nuevo y limpio
            Usuario usuario = new Usuario();
            usuario.setUsername("cliente1");
            usuario.setPassword(passwordEncoder.encode("123456"));
            usuario.setRole("USER");

            Cliente cliente = new Cliente("Tech", "User", "Calle Digital 101", "555-0199", "cliente1@techstore.com");
            usuario.setCliente(cliente);
            cliente.setUsuario(usuario);

            usuarioRepository.save(usuario);

            System.out.println("--> USUARIO CREADO EXITOSAMENTE: cliente1 / 123456 <--");
        };
    }
}