package com.project.prova.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    // SecretKey generata per la firma del token
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Genera un JWT token per l'utente.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Scadenza 10 ore
                .signWith(secretKey) // Usa SecretKey
                .compact();
    }

    /**
     * Valida un JWT token.
     */
    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                                   .setSigningKey(secretKey) // Usa SecretKey
                                   .build();
            parser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println("Token non valido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Estrae il nome utente (subject) dal token.
     */
    public String extractUsername(String token) {
        JwtParser parser = Jwts.parserBuilder()
                               .setSigningKey(secretKey) // Usa SecretKey
                               .build();
        return parser.parseClaimsJws(token).getBody().getSubject();
    }
}

