package com.techstore.tiendaonline;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Pedido;
import com.techstore.tiendaonline.entity.Producto;
import com.techstore.tiendaonline.entity.Usuario;
import com.techstore.tiendaonline.repository.ClienteRepository;
import com.techstore.tiendaonline.repository.PedidoRepository;
import com.techstore.tiendaonline.repository.ProductoRepository;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
public class TiendaOnlineApplication {


    public static void main(String[] args) {
        SpringApplication.run(TiendaOnlineApplication.class,args);
    }
}