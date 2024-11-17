package com.example.sports_perf.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long validityInMilliseconds;

    // Generate JWT Token
    public String generateToken(String username, String role) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Parse JWT Token and get Claims
    public Claims parseClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    // Get username from JWT token
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    // Validate JWT Token
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}

