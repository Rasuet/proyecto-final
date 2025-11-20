package com.techstore.tiendaonline.controller;


import com.techstore.tiendaonline.dto.UpdateClienteRequest;
import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.entity.Usuario;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import com.techstore.tiendaonline.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private Cliente getClienteAutenticado(HttpSession session) {
        String username = (String) session.getAttribute("usuario");
        if (username == null) throw new SecurityException("No autenticado");

        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new SecurityException("Usuario no encontrado"))
                .getCliente();
    }

    /**
     * Obtener datos del perfil del cliente logueado.
     */
    @GetMapping("/perfil")
    public ResponseEntity<Cliente> getPerfil(HttpSession session) {
        try {
            Cliente cliente = getClienteAutenticado(session);
            return ResponseEntity.ok(cliente);
        } catch (SecurityException e) {
            return ResponseEntity.status(401).build();
        }
    }

    /**
     * R5.0: Actualizar datos del cliente.
     */
    @PutMapping("/perfil")
    public ResponseEntity<Cliente> updatePerfil(@Valid @RequestBody UpdateClienteRequest request, HttpSession session) {
        try {
            Cliente cliente = getClienteAutenticado(session);

            // Actualizamos los campos permitidos
            cliente.setNombre(request.getNombre());
            cliente.setApellidos(request.getApellidos());
            cliente.setDireccion(request.getDireccion());
            cliente.setTelefono(request.getTelefono());

            Cliente actualizado = clienteService.save(cliente);
            return ResponseEntity.ok(actualizado);

        } catch (SecurityException e) {
            return ResponseEntity.status(401).build();
        }
    }
}