package com.techstore.tiendaonline.service;

import com.techstore.tiendaonline.entity.Usuario;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> authenticate(String username, String rawPassword) {
        System.out.println("--- DEBUG LOGIN ---");
        System.out.println("Intento de login para: " + username);

        // 1. Buscar usuario
        Optional<Usuario> optionalUser = usuarioRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            Usuario usuario = optionalUser.get();
            System.out.println("✅ Usuario ENCONTRADO en BD. Hash guardado: " + usuario.getPasswordHash());

            // 2. Comprobar contraseña
            boolean coinciden = passwordEncoder.matches(rawPassword, usuario.getPasswordHash());
            System.out.println("Comparando password ingresado (" + rawPassword + ") con hash.");
            System.out.println("¿Coinciden?: " + coinciden);

            if (coinciden) {
                return Optional.of(usuario);
            } else {
                System.out.println("❌ Error: La contraseña no coincide.");
            }
        } else {
            System.out.println("❌ Error: Usuario NO encontrado en la base de datos (BD vacía o nombre incorrecto).");
        }
        System.out.println("-------------------");

        return Optional.empty();
    }
}