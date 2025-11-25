package com.techstore.tiendaonline.controller;

import com.techstore.tiendaonline.dto.AgregarProductoRequest;
import com.techstore.tiendaonline.dto.LineaPedidoDTO;
import com.techstore.tiendaonline.dto.PedidoResponse;
import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Pedido;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import com.techstore.tiendaonline.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PedidoController(PedidoService pedidoService, UsuarioRepository usuarioRepository) {
        this.pedidoService = pedidoService;
        this.usuarioRepository = usuarioRepository;
    }

    private Cliente getClienteAutenticado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsernameWithCliente(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getCliente();
    }

    // --- MÉTODO DE CONVERSIÓN (Entidad -> DTO) ---
    private PedidoResponse mapToDTO(Pedido pedido) {
        PedidoResponse dto = new PedidoResponse();

        // CRÍTICO: Aseguro que el pedido no sea nulo antes de acceder a sus métodos
        if (pedido == null) return dto;

        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setEstado(pedido.getEstado());
        dto.setTotal(pedido.getTotal());

        // Convierto las líneas para evitar problemas de Lazy Loading en el JSON
        if (pedido.getLineasPedido() != null) {
            List<LineaPedidoDTO> lineasDTO = pedido.getLineasPedido().stream().map(linea ->
                    new LineaPedidoDTO(
                            linea.getId(),
                            linea.getProducto().getId(),
                            linea.getProducto().getNombre(),
                            linea.getCantidad(),
                            linea.getPrecioUnitario(),
                            linea.getSubtotal()
                    )
            ).collect(Collectors.toList());
            dto.setLineasPedido(lineasDTO);
        }
        return dto;
    }

    // --- ENDPOINTS ---

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> getHistorial() {
        try {
            Cliente cliente = getClienteAutenticado();
            List<Pedido> historialEntities = pedidoService.obtenerHistorial(cliente);

            if (historialEntities.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<PedidoResponse> historialDTOs = historialEntities.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(historialDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/activo")
    public ResponseEntity<PedidoResponse> getPedidoActivo() {
        try {
            Cliente cliente = getClienteAutenticado();
            Pedido pedido = pedidoService.obtenerCarrito(cliente);
            return ResponseEntity.ok(mapToDTO(pedido));
        } catch (Exception e) {
            // Si el carrito está vacío, devuelve 204 No Content
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<PedidoResponse> agregarProducto(@Valid @RequestBody AgregarProductoRequest request) {
        try {
            Cliente cliente = getClienteAutenticado();
            Pedido pedido = pedidoService.agregarProductoAlCarrito(cliente, request);
            return ResponseEntity.ok(mapToDTO(pedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<Map<String, String>> confirmarPedido() {
        try {
            Cliente cliente = getClienteAutenticado();
            pedidoService.confirmarPedido(cliente);
            return ResponseEntity.ok(Map.of("message", "Pedido confirmado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}