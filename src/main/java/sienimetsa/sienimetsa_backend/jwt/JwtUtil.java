package sienimetsa.sienimetsa_backend.jwt;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import sienimetsa.sienimetsa_backend.domain.Appuser;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Secret key for JWT signing (read from environment variable)
    private final String jwtSecret;
    private final long jwtExpirationMs = 86400000; // 24 hours expiration time for the token

    public JwtUtil(Dotenv dotenv) {
        this.jwtSecret = dotenv.get("JWT_SECRET");
    }

    // Generate the JWT token based on the email
    public String generateToken(String email,Appuser user) {
        return Jwts.builder()
                .subject(email) // Set email as the subject (username)
                .claim("uId", user.getU_id())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())) // Sign with the secret key
                .compact();
    }

    // Extract the email (subject) from the JWT token
    public String extractEmail(String token) {
        return getClaims(token).getSubject(); // This will extract the email
    }

    // Extract uId from the JWT token
    public Long extractUId(String token) {
        return getClaims(token).get("uId", Long.class); // Extract the uId claim
    }
    
    

    // Validate the JWT token by checking its claims
    public boolean validateJwtToken(String token) {
        try {
            getClaims(token); // If the claims are invalid or expired, this will throw an exception
            return true;
        } catch (Exception e) {
            // Log error details (you may want to log the exception here)
            System.out.println("Failed to validate JWT token: " + e.getMessage());
        }
        return false;
    }

    private Claims getClaims(String token) {
        return (Claims) Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
