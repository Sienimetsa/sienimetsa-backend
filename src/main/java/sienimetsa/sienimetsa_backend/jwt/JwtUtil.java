package sienimetsa.sienimetsa_backend.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Secret key for JWT signing (this should ideally come from properties or environment variables)
    private final String jwtSecret = "YourSecretKeyForJWTGenerationYourSecretKeyForJWTGeneration"; // Ensure key length is sufficient
    private final long jwtExpirationMs = 86400000; // 24 hours expiration time for the token

    // Generate the JWT token based on the email
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) // Set email as the subject (username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())) // Sign with the secret key
                .compact();
    }

    // Extract the email (subject) from the JWT token
    public String extractEmail(String token) {
        return getClaims(token).getSubject(); // This will extract the email
    }

    // Validate the JWT token by checking its claims
    public boolean validateJwtToken(String token) {
        try {
            getClaims(token); // If the claims are invalid or expired, this will throw an exception
            return true;
        } catch (Exception e) {
            // Log error details (you may want to log the exception here)
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
