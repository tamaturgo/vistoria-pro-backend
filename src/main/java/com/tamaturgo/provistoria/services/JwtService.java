package com.tamaturgo.provistoria.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@Slf4j
public class JwtService {

    @Value("${supabase.jwt.secret}")
    private String jwtSecret;

    public String verify(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException e) {
            log.error("Token JWT inválido ou expirado: {}", e.getMessage());
            throw new HttpClientErrorException(
                    HttpStatus.UNAUTHORIZED,
                    "Token JWT inválido ou expirado: " + e.getMessage()
            );
        }
    }
}