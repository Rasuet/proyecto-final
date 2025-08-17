package com.techstore.tiendaonline.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeamos la URL "/images/**" a la carpeta física en tu disco C:
        // Nota: "file:..." indica que es una ruta del sistema de archivos
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:C:/imagenes_tienda/");
    }
}