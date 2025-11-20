package com.techstore.tiendaonline.service;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Busca un cliente por su ID.
     */
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Guarda o actualiza un cliente.
     */
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}