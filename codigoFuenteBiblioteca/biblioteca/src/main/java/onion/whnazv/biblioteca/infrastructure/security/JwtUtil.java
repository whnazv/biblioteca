package onion.whnazv.biblioteca.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final Key secretKey;
    private final long expirationMillis;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMillis
    ) {
        if (secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret debe tener mínimo 32 caracteres");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(Authentication auth) {
        final String username = auth.getName();
        final List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            return parseToken(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> extractRoles(String token) {
        try {
            Claims claims = parseToken(token).getBody();
            Object roles = claims.get("roles");
            if (roles instanceof List<?> list) {
                return list.stream().map(Object::toString).toList();
            } else if (roles instanceof String s) {
                return Arrays.asList(s.split(","));
            }
            return List.of();
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}
