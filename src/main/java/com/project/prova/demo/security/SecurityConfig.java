package com.project.prova.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disabilita la protezione CSRF (non necessaria per le API REST)
            .authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll() // Permetti l'accesso pubblico a questi endpoint
                .anyRequest().authenticated() // Richiede autenticazione per tutte le altre richieste
            .and()
            .formLogin().disable() // Disabilita il login basato su form (dal momento che stai usando JWT)
            .httpBasic().disable(); // Disabilita l'autenticazione base (se non la utilizzi)

        return http.build();
    }
}


