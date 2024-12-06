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
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .claim("role", role) // Aggiungi il ruolo come claim
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
                               .setSigningKey(secretKey)
                               .build();
        String username = parser.parseClaimsJws(token).getBody().getSubject();
    
        // Estrai anche il ruolo se necessario
        String role = (String) parser.parseClaimsJws(token).getBody().get("role");
        // Puoi anche restituire il ruolo insieme al nome utente se ne hai bisogno
        return username;
    }
    
    public String extractRole(String token) {
        JwtParser parser = Jwts.parserBuilder()
                               .setSigningKey(secretKey)
                               .build();
        
        // Estrai il ruolo dal token
        String role = (String) parser.parseClaimsJws(token).getBody().get("role");
    
        // Puoi loggare o restituire anche il ruolo, ma per Spring Security, dobbiamo usare il ruolo per configurare l'autenticazione
        return role;
    }    
    
}

