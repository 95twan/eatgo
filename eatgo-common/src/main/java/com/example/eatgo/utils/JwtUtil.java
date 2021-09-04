package com.example.eatgo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtUtil {

    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long userId, String userName, Long restaurantId) {
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("userName", userName);

        if(restaurantId != null){
            builder.claim("restaurantId", restaurantId);
        }

        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
