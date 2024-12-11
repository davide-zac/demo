package com.project.prova.demo.controller;


import com.project.prova.demo.model.User;
import com.project.prova.demo.repository.UserRepository;
import com.project.prova.demo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

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
    private static final String ADMIN_PASS = "sonoadmin";

    @CrossOrigin(origins = "http://localhost:5173")
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

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/register/admin")
    public ResponseEntity<?> promoteToAdmin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String adminPass = request.get("adminPass");

        log.info("Tentativo di promozione ad admin per utente: {}", username);

        // Verifica se l'utente esiste
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Errore: utente non trovato.");
        }

        // Se la chiave "adminPass" è nulla o vuota
        if (adminPass == null || adminPass.isEmpty()) {
            log.info("Nessuna admin-pass fornita. Nessuna modifica al ruolo per utente: {}", username);
            return ResponseEntity.ok("Nessuna modifica effettuata. L'utente mantiene il ruolo di TEST.");
        }

        // Verifica la chiave "admin-pass"
        if (!ADMIN_PASS.equals(adminPass)) {
            log.error("Admin-pass errata per utente: {}", username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Errore: admin-pass errata.");
        }

        // Aggiorna il ruolo dell'utente a ADMIN
        existingUser.setRoles(Collections.singleton("ROLE_ADMIN"));
        userRepository.save(existingUser);
        log.info("Utente promosso a ADMIN con successo: {}", username);

        return ResponseEntity.ok("Utente promosso ad ADMIN con successo.");
    }
    
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        log.info("Tentativo di login per utente: {}", user.getUsername());

        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            log.info("Login riuscito per utente: {}", user.getUsername());
            String role = existingUser.getRoles().iterator().next();

            return jwtUtils.generateToken(user.getUsername(), role);
        }
        log.error("Login fallito: credenziali non valide per utente: {}", user.getUsername());

        throw new RuntimeException("Invalid username or password");
    }
}

