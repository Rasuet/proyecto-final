package com.techstore.tiendaonline.controller;

import com.techstore.tiendaonline.dto.LoginRequest;
import com.techstore.tiendaonline.entity.Usuario;
import com.techstore.tiendaonline.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationService authenticationService;
    // Repositorio necesario para guardar el contexto de seguridad en la sesión (Spring Boot 3)
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // R1.0: Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> responseBody = new HashMap<>();

        Optional<Usuario> authenticatedUser = authenticationService.authenticate(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        if (authenticatedUser.isPresent()) {
            Usuario usuario = authenticatedUser.get();

            // 1. Crear el token de autenticación de Spring Security
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    usuario.getUsername(),
                    null,
                    AuthorityUtils.createAuthorityList("ROLE_" + usuario.getRol())
            );

            // 2. Crear y establecer el Contexto de Seguridad
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(token);
            SecurityContextHolder.setContext(context);

            // 3. Guardar explícitamente el contexto en la sesión (CRÍTICO para Spring Security 6)
            securityContextRepository.saveContext(context, request, response);

            responseBody.put("message", "Login exitoso");
            responseBody.put("username", usuario.getUsername());
            return ResponseEntity.ok(responseBody);
        } else {
            responseBody.put("message", "Credenciales inválidas");
            return ResponseEntity.status(401).body(responseBody);
        }
    }

    // Verificar sesión
    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> checkSession() {
        // Verificamos contra el contexto de Spring Security
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            return ResponseEntity.ok(Map.of(
                    "authenticated", true,
                    "user", Map.of("username", auth.getName())
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("authenticated", false));
        }
    }

    // Logout
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        // Limpiamos el contexto y la sesión
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada"));
    }
}