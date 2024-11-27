package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "i_love_you30000000000000000000000000000000000";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate JWT Token
    public static String generateToken(String email, Collection<String> roles) {
        return Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate JWT Token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Retrieve the current user's email from the SecurityContext
    public  static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String){
            return (String) authentication.getPrincipal();
        }
        throw new IllegalStateException("No authenticated user found");

    }

    // Extract Email from JWT Token
    public static String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // Extract Roles from JWT Token
    public static List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("roles", List.class);
    }
}
