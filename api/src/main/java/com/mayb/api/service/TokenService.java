package com.mayb.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mayb.api.entity.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private String secret = "123456";

    public String generateToken(User user){
        try {
            // Define o algoritmo de criptografia (HMAC256) usando a nossa senha secreta
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Cria o token
            String token = JWT.create()
                    .withIssuer("our-wallet-api") // Quem criou
                    .withSubject(user.getEmail()) // Quem é o dono do token (Email do usuário)
                    .withExpiresAt(genExpirationDate()) // Quando expira (pra segurança)
                    .sign(algorithm); // Carimba com a assinatura digital

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token", exception);
        }
    }

    // Define que o token vale por 2 horas (fuso horário de Brasília -3)
    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("our-wallet-api")
                    .build()
                    .verify(token) // <--- Aqui ele verifica a assinatura e validade
                    .getSubject(); // <--- Se der certo, devolve o email do usuário
        } catch (JWTVerificationException exception){
            // Se o token for falso ou estiver expirado, retorna vazio
            return "";
        }
    }
}
