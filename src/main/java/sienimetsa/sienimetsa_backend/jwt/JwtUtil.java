package sienimetsa.sienimetsa_backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    // In a real project, store the secret in properties/env variables.
    private final String jwtSecret = "YourSecretKeyForJWTGenerationYourSecretKeyForJWTGeneration"; // ensure key length is sufficient
    private final long jwtExpirationMs = 86400000; // 24 hours

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) // builder-style subject()
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())) // use signWith(Key) without algorithm parameter
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            // log error details in a real project
        }
        return false;
    }

    private Claims getClaims(String token) {
        // Cast getPayload() to Claims as getBody() is deprecated
        return (Claims) Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}