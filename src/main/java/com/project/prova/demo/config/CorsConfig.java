package com.project.prova.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permetti le richieste dal frontend su localhost:5173
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // URL del tuo frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS") // I metodi HTTP consentiti
                        .allowedHeaders("*"); // Consenti tutti gli headers
            }
        };
    }
}



