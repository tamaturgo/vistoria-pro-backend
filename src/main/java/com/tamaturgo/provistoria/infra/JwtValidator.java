package com.tamaturgo.provistoria.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtValidator {

    private final JWTVerifier verifier;

    public JwtValidator(String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm)
                .withIssuer("supabase")
                .build();
    }

    public DecodedJWT validateToken(String token) {
        return verifier.verify(token);
    }

    public String decodeJWT(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getSubject();
    }
}
