package com.tamaturgo.provistoria.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tamaturgo.provistoria.models.User;
import com.tamaturgo.provistoria.repository.UserRepository;
import com.tamaturgo.provistoria.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticatedUserProvider {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    @Value("${supabase.jwt.secret}")
    private String jwtSecret;

    public User getUserFromAuthorization(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.error("Token JWT não fornecido ou inválido");
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Token JWT não fornecido");
        }

        String token = authorizationHeader.substring(7);
        String sub = jwtService.verify(token);
        log.info("Recuperando usuário com sub: {}", sub);

        return userRepository.findBySub(sub)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.UNAUTHORIZED,
                        "Usuário não encontrado"
                ));
    }

}
