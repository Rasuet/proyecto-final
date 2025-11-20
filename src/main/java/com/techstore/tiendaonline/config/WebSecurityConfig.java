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
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Configuración de CORS (Cross-Origin Resource Sharing)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Deshabilitar CSRF (necesario para APIs REST sin estado o con tokens propios)
                .csrf(csrf -> csrf.disable())

                // 3. Reglas de Autorización
                .authorizeHttpRequests(authz -> authz
                        // Permitir acceso público a recursos estáticos, login y errores
                        .requestMatchers("/", "/index.html", "/static/**", "/images/**", "/error").permitAll()
                        // Permitir endpoints de autenticación
                        .requestMatchers("/api/login", "/api/session", "/api/logout").permitAll()
                        // Permitir acceso a la consola H2 (solo para desarrollo)
                        .requestMatchers("/h2-console/**").permitAll()
                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )

                // 4. Manejo de Excepciones
                // Devuelve 401 (Unauthorized) en lugar de 403 (Forbidden) cuando no hay sesión,
                // permitiendo que el frontend detecte cuándo redirigir al login.
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )

                // 5. Configuración de Cabeceras
                .headers(headers -> headers
                        // Permitir que la consola H2 se muestre en un frame/iframe
                        .frameOptions(frameOptions -> frameOptions.disable())
                        // Política de Seguridad de Contenido (CSP) permisiva para evitar errores de consola
                        // con scripts 'data:', 'blob:' o inline (común en entornos de desarrollo web).
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self' * data: blob: 'unsafe-inline' 'unsafe-eval'; script-src 'self' * data: blob: 'unsafe-inline' 'unsafe-eval'; style-src 'self' * 'unsafe-inline'; img-src 'self' * data: blob:;")
                        )
                );

        return http.build();
    }

    /**
     * Configuración detallada de CORS para permitir peticiones desde el navegador.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite cualquier origen (útil para desarrollo, restringir en producción)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        // Permite los métodos HTTP comunes
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite cualquier cabecera
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Permite el envío de credenciales (cookies de sesión)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}