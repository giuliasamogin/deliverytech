package com.deliverytech.delivery_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Gera um token JWT para o usuário
    public String gerarToken(String email) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + expiration);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(agora)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // Valida um token JWT e retorna as claims
    public Claims validarToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null; // Token inválido
        }
    }

    // Extrai o email (subject) do token
    public String getEmailFromToken(String token) {
        Claims claims = validarToken(token);
        return claims != null ? claims.getSubject() : null;
    }
}