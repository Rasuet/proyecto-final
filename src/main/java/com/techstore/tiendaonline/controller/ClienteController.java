package com.techstore.tiendaonline.controller;

import com.techstore.tiendaonline.dto.UpdateClienteRequest;
import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import com.techstore.tiendaonline.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ClienteController(ClienteService clienteService, UsuarioRepository usuarioRepository) {
        this.clienteService = clienteService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método auxiliar para obtener el cliente autenticado de forma segura.
     * Usa la consulta 'findByUsernameWithCliente' para evitar errores de Lazy Initialization.
     */
    private Cliente getClienteAutenticado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // IMPORTANTE: Asegúrate de que UsuarioRepository tenga el método findByUsernameWithCliente
        return usuarioRepository.findByUsernameWithCliente(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
                .getCliente();
    }

    /**
     * Obtiene los datos del perfil del usuario logueado.
     */
    @GetMapping("/perfil")
    public ResponseEntity<Cliente> getPerfil() {
        try {
            return ResponseEntity.ok(getClienteAutenticado());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualiza los datos personales del usuario logueado.
     */
    @PutMapping("/perfil")
    public ResponseEntity<Cliente> updatePerfil(@Valid @RequestBody UpdateClienteRequest request) {
        try {
            Cliente cliente = getClienteAutenticado();

            // Actualizamos solo los campos permitidos
            cliente.setNombre(request.getNombre());
            cliente.setApellidos(request.getApellidos());
            cliente.setDireccion(request.getDireccion());
            cliente.setTelefono(request.getTelefono());

            return ResponseEntity.ok(clienteService.save(cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}