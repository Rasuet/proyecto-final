package com.techstore.tiendaonline.service;

import com.techstore.tiendaonline.entity.Cliente;
import com.techstore.tiendaonline.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // <--- ¡CRÍTICO!
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de lógica de negocio para la gestión de entidades Cliente.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Guarda o actualiza un objeto Cliente en la base de datos.
     * @param cliente El cliente a guardar.
     * @return El cliente guardado.
     */
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Aquí se podría añadir lógica para buscar por ID, validar campos extra, etc.
}