package com.example.employee_api.security;


import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String SECRET = 
        "12345678890JAVA12345678901234567890";

        public String generateToken(String email)
        {
            return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(System.currentTimeMillis() + 86500000)
                )
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();

        }

        public String extractEmail(String token)
        {
            return Jwts.parserBuilder()
                        .setSigningKey(SECRET.getBytes())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
        }
}
