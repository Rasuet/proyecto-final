package com.techstore.tiendaonline.controller;

import com.techstore.tiendaonline.entity.Usuario;
import com.techstore.tiendaonline.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request, HttpSession session) {
        String username = request.get("username");
        String rawPassword = request.get("password");

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        if (usuario != null) {
            // 1. Verificar contraseña
            if (passwordEncoder.matches(rawPassword, usuario.getPassword())) {

                // 2. CRÍTICO: Informar a Spring Security de que el usuario está autenticado

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRole()))
                );

                // 3. Establezco la autenticación en el contexto de seguridad
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(authToken);

                // 4. Guardo el contexto de seguridad en la sesión HTTP para que persista entre peticiones
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

                // 5. Guardo también objeto usuario para uso fácil (opcional pero útil)
                session.setAttribute("user", usuario);

                Map<String, Object> response = new HashMap<>();
                response.put("username", usuario.getUsername());
                response.put("role", usuario.getRole());
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.status(401).body(Map.of("message", "Credenciales inválidas"));
    }

    @GetMapping("/session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        // Verifico si Spring Security reconoce al usuario
        SecurityContext sc = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        if (sc != null && sc.getAuthentication() != null && sc.getAuthentication().isAuthenticated()) {
            // Recupero objeto usuario para enviarlo al front
            Usuario usuario = (Usuario) session.getAttribute("user");
            Map<String, Object> response = new HashMap<>();
            response.put("authenticated", true);
            response.put("user", usuario);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of("authenticated", false));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada"));
    }
}