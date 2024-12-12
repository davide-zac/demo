package com.project.prova.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors() // Abilita CORS
            .and()        
            .csrf().disable() // Disabilita la protezione CSRF (non necessaria per le API REST)
            .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/").permitAll() // Permetti l'accesso pubblico a questi endpoint
                .requestMatchers(HttpMethod.POST, "/auth/register","/auth/register/admin", "/auth/login").permitAll() // Permetti l'accesso pubblico a questi endpoint
                .requestMatchers(HttpMethod.GET, "/test/**").hasRole("TEST") // Richiede il ruolo TEST per accedere agli endpoint test
                .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() // Richiede autenticazione per tutte le altre richieste
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Aggiungi il filtro JWT
            .formLogin().disable() // Disabilita il login basato su form (stai usando JWT)
            .httpBasic().disable(); // Disabilita l'autenticazione base (se non la utilizzi)

        return http.build();
    }
}



