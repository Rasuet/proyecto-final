package com.techstore.tiendaonline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Mapea la URL "/images/**" a la carpeta física en el disco duro.
     * Esto permite que las imágenes se carguen desde C:/imagenes_tienda.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // La URL del navegador es /images/**
        // La ubicación en el disco duro es file:///C:/imagenes_tienda/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/imagenes_tienda/");
    }
}
