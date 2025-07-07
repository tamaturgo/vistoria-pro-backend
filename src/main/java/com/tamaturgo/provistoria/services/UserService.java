package com.tamaturgo.provistoria.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Value("${supabase.api.url}")
    private String supabaseUrl;

    @Value("${supabase.api.key}")
    private String apiKey;

    @Value("${supabase.jwt.secret}")
    private String jwtSecret;

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createSupabaseUser(String email, String password) {
        String body = """
        {
          "email": "%s",
          "password": "%s"
        }
        """.formatted(email, password);

        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                supabaseUrl + "/auth/v1/admin/users",
                request,
                String.class
        );

        return response.getBody();
    }

    public String updateSupabaseUser(String userId, String email, String password) {
        String body = """
        {
          "email": "%s",
          "password": "%s"
        }
        """.formatted(email, password);

        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                supabaseUrl + "/auth/v1/admin/users/" + userId,
                HttpMethod.PUT,
                request,
                String.class
        );

        return response.getBody();
    }

    public String deleteSupabaseUser(String userId) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                supabaseUrl + "/auth/v1/admin/users/" + userId,
                HttpMethod.DELETE,
                request,
                String.class
        );

        return response.getBody();
    }

    public String getSupabaseUser(String userId) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                supabaseUrl + "/auth/v1/admin/users/" + userId,
                HttpMethod.GET,
                request,
                String.class
        );

        return response.getBody();
    }

    public String listSupabaseUsers() {
        HttpHeaders headers = createHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                supabaseUrl + "/auth/v1/admin/users",
                HttpMethod.GET,
                request,
                String.class
        );

        return response.getBody();
    }

    public String getSupabaseUserByEmail(String email) {
        HttpHeaders headers = createHeaders();
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                supabaseUrl + "/auth/v1/admin/users?email=" + email,
                HttpMethod.GET,
                request,
                String.class
        );

        return response.getBody();
    }

    public String validateAndGetUserIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("supabase")
                    .build();

            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();

        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", apiKey);
        headers.setBearerAuth(apiKey);
        return headers;
    }
}
