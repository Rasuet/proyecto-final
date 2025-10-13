package com.techstore.tiendaonline.controller;


import com.techstore.tiendaonline.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {
        Map<String, String> response = new HashMap<>();

        if ("cliente1".equals(loginRequest.getUsername()) && "123456".equals(loginRequest.getPassword())) {
            session.setAttribute("usuario", loginRequest.getUsername()); // ✅ Guarda en sesión
            response.put("message", "Login exitoso");
            response.put("username", loginRequest.getUsername());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Credenciales inválidas");
            return ResponseEntity.status(401).body(response);
        }
    }

    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> checkSession(HttpSession session) {
        String usuario = (String) session.getAttribute("usuario");
        if (usuario != null) {
            return ResponseEntity.ok(Map.of(
                    "authenticated", true,
                    "user", Map.of("username", usuario)
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("authenticated", false));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada"));
    }
}

