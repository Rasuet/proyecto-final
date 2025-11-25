package com.techstore.tiendaonline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebMvcSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CORS: Permite que el navegador haga peticiones
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. CSRF: Desactivado para simplificar la integración SPA (REST API)
                .csrf(csrf -> csrf.disable())

                // 3. Reglas de Acceso
                .authorizeHttpRequests(authz -> authz
                        // Acceso público a la página de inicio, recursos estáticos, imágenes, y H2
                        .requestMatchers("/", "/index.html", "/static/**", "/images/**", "/error").permitAll()
                        .requestMatchers("/api/login", "/api/session", "/api/logout").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // Todas las demás peticiones API requieren autenticación
                        .anyRequest().authenticated()
                )

                // 4. Manejo de errores de sesión (Devuelve 401 para que el Front pueda reaccionar)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )

                // 5. Configuración de cabeceras (para H2 Console)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permitir cualquier origen (útil para desarrollo)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Permitir cookies de sesión
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}