package com.techstore.tiendaonline.controller;


import com.techstore.tiendaonline.dto.AgregarProductoRequest;
import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Pedido;
import com.techstore.tiendaonline.service.ClienteService;
import com.techstore.tiendaonline.service.PedidoService;
import com.techstore.tiendaonline.entity.Usuario;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioRepository usuarioRepository; // Necesario para obtener el ID del Cliente

    @Autowired
    public PedidoController(PedidoService pedidoService, UsuarioRepository usuarioRepository) {
        this.pedidoService = pedidoService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Helper para obtener el cliente autenticado desde la sesión.
     * En un entorno real, usaríamos @AuthenticationPrincipal, pero lo simulamos con la sesión.
     */
    private Cliente getClienteAutenticado(HttpSession session) {
        String username = (String) session.getAttribute("usuario");
        if (username == null) {
            throw new SecurityException("Usuario no autenticado en la sesión.");
        }

        // Buscar el Usuario por username
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new SecurityException("Usuario no encontrado en BD."));

        // El Usuario tiene una relación 1:1 con el Cliente
        return usuario.getCliente();
    }

    /**
     * R2.1: Añade un producto al pedido (carrito) activo del cliente.
     */
    @PostMapping("/agregar")
    public ResponseEntity<Pedido> agregarProducto(@Valid @RequestBody AgregarProductoRequest request, HttpSession session) {
        try {
            Cliente cliente = getClienteAutenticado(session);

            // Llamar a la lógica del servicio
            Pedido pedidoActualizado = pedidoService.agregarProductoAlCarrito(cliente, request);

            return ResponseEntity.ok(pedidoActualizado);

        } catch (SecurityException e) {
            return ResponseEntity.status(401).build(); // No autenticado
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Producto no encontrado o stock insuficiente
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * R3.0: Consulta el pedido (carrito) activo del cliente.
     */
    @GetMapping("/activo")
    public ResponseEntity<Pedido> getPedidoActivo(HttpSession session) {
        try {
            Cliente cliente = getClienteAutenticado(session);
            Pedido pedido = pedidoService.obtenerCarrito(cliente);
            return ResponseEntity.ok(pedido);
        } catch (SecurityException e) {
            return ResponseEntity.status(401).build(); // No autenticado
        } catch (IllegalArgumentException e) {
            // No hay carrito activo, devolvemos 204 No Content para que el frontend lo maneje
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * R3.1: Confirma el pedido activo y genera la factura.
     */
    @PostMapping("/confirmar")
    public ResponseEntity<Map<String, String>> confirmarPedido(HttpSession session) {
        try {
            Cliente cliente = getClienteAutenticado(session);

            // Lógica de confirmación: actualiza stock y genera la Factura (Pago)
            pedidoService.confirmarPedido(cliente);

            return ResponseEntity.ok(Map.of("message", "Pedido confirmado y factura generada."));

        } catch (SecurityException e) {
            return ResponseEntity.status(401).build();
        } catch (IllegalStateException e) {
            // Stock insuficiente o carrito vacío
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error interno al procesar el pago/factura."));
        }
    }
}
