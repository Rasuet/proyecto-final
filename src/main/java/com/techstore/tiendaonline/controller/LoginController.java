package com.techstore.tiendaonline.controller;


import com.techstore.tiendaonline.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

    @RestController
    @RequestMapping("/api")
    public class LoginController {

        @PostMapping("/login")
        public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest loginRequest) {
            Map<String, String> response = new HashMap<>();

            if ("cliente1".equals(loginRequest.getUsername()) && "123456".equals(loginRequest.getPassword())) {
                response.put("message", "Login exitoso");
                response.put("username", loginRequest.getUsername());
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Credenciales inválidas");
                return ResponseEntity.status(401).body(response);
            }
        }
    }

