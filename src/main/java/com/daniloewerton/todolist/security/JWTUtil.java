package com.daniloewerton.todolist.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(final String email) {

        final Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());

        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
//        return Jwts.builder()
//                .setSubject(email)
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
//                .compact();
    }

    public boolean validateToken(final String token) {
        final Claims claims = getClaims(token);
        if (claims != null) {
            final String username = claims.getSubject();
            final Date expirationDate = claims.getExpiration();
            final Date now = new Date(System.currentTimeMillis());

            return username != null && expirationDate != null && now.before(expirationDate);
        }
        return false;
    }

    private Claims getClaims(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes())
                    .build()
                    .parseClaimsJwt(token)
                    .getBody();
        } catch (final Exception exception) {
            return null;
        }
    }

    public String getUsername(final String token) {
        final Claims claims = getClaims(token);

        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }
}
