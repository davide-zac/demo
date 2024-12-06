package com.project.prova.demo.controller;


import com.project.prova.demo.model.User;
import com.project.prova.demo.repository.UserRepository;
import com.project.prova.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/register")
    public String register(@RequestBody User user) {
        log.info("Registrazione di un nuovo utente: {}", user.getUsername());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "Errore: username già esistente.";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Assegna ruoli di default, ad esempio: TEST
        user.setRoles(Collections.singleton("ROLE_TEST"));
        userRepository.save(user);
        log.info("Utente registrato con successo: {}", user.getUsername());

        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        log.info("Tentativo di login per utente: {}", user.getUsername());

        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            log.info("Login riuscito per utente: {}", user.getUsername());

            return jwtUtils.generateToken(user.getUsername());
        }
        log.error("Login fallito: credenziali non valide per utente: {}", user.getUsername());

        throw new RuntimeException("Invalid username or password");
    }
}

