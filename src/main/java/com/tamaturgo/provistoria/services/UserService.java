package com.tamaturgo.provistoria.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamaturgo.provistoria.dto.user.*;
import com.tamaturgo.provistoria.models.User;
import com.tamaturgo.provistoria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Value("${supabase.api.url}")
    private String supabaseUrl;

    @Value("${supabase.api.key}")
    private String apiKey;

    @Value("${supabase.jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.time}")
    private long jwtExpirationTime;

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserService(RestTemplate restTemplate,
                       UserRepository userRepository,
                       ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    // ========== CRIAÇÃO DE USUÁRIO ==========
    public UserResponse registerUser(RegisterRequest request) {
        validateEmailNotExists(request.email());

        try {
            String supabaseResponseJson = createSupabaseUser(request.email(), request.password());
            SupabaseUserResponse userResp = objectMapper.readValue(supabaseResponseJson, SupabaseUserResponse.class);

            User user = new User();
            user.setSub(UUID.fromString(userResp.identities.getFirst().user_id));
            user.setEmail(request.email());
            user.setFullName(request.fullName());
            user.setOfficeName(request.officeName());
            user.setStatus("ACTIVE");
            user.setRole("ENGINEER");

            User savedUser = userRepository.save(user);

            // Faz login para obter o token JWT
            SupabaseLoginResponse loginResponse = loginSupabaseUser(request.email(), request.password());
            if (loginResponse == null || loginResponse.user == null) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
            }
            return mapToUserResponse(savedUser, loginResponse.accessToken, loginResponse.refreshToken, loginResponse.expiresAt);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar resposta do Supabase", e);
        }
    }

    private void validateEmailNotExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(409), "Email já cadastrado");
        }
    }

    // ========== AUTENTICAÇÃO ==========
    public UserResponse authenticateUser(LoginRequest request) {
        try {
            SupabaseLoginResponse response = loginSupabaseUser(request.email(), request.password());
            if (response == null || response.user == null) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
            }
            String token = response.accessToken;
            verifyJwtToken(token);

            User user = userRepository.findBySub(String.valueOf(UUID.fromString(response.user.id)))
                    .orElseThrow(() -> new HttpClientErrorException(
                            HttpStatus.UNAUTHORIZED,
                            "Usuário não encontrado"
                    ));

            return mapToUserResponse(user,
                    response.accessToken,
                    response.refreshToken,
                    response.expiresAt);

        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(
                    e.getStatusCode(),
                    "Falha na autenticação: " + e.getResponseBodyAsString()
            );
        }
    }

    // ========== OPERAÇÕES SUPABASE ==========
    private String createSupabaseUser(String email, String password) {
        String body = String.format("""
        {
          "email": "%s",
          "password": "%s",
          "email_confirm": true
        }
        """, email, password);

        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                supabaseUrl + "/auth/v1/admin/users",
                request,
                String.class
        );

        return response.getBody();
    }

    private SupabaseLoginResponse loginSupabaseUser(String email, String password) {
        String body = String.format("""
        {
          "email": "%s",
          "password": "%s"
        }
        """, email, password);

        HttpHeaders headers = createHeaders();
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                supabaseUrl + "/auth/v1/token?grant_type=password",
                request,
                String.class
        );

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpClientErrorException(
                    response.getStatusCode(),
                    "Falha ao autenticar usuário no Supabase: " + response.getBody()
            );
        }
        try {
            return objectMapper.readValue(response.getBody(), SupabaseLoginResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar resposta de login do Supabase", e);
        }
    }

    // ========== GERENCIAMENTO DE TOKENS JWT ==========
    public String generateJwtToken(User user) {
        return JWT.create()
                .withSubject(user.getSub().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String verifyJwtToken(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(token);
            return jwt.getSubject(); // sub do JWT
        } catch (JWTVerificationException e) {
            throw new HttpClientErrorException(
                    HttpStatus.UNAUTHORIZED,
                    "Token JWT inválido ou expirado: " + e.getMessage()
            );
        }
    }


    // ========== OPERAÇÕES DE USUÁRIO ==========
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));
        return mapToUserResponseWithoutToken(user);
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponseWithoutToken)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(UUID id, RegisterRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));

        user.setFullName(request.fullName());
        user.setOfficeName(request.officeName());

        User updatedUser = userRepository.save(user);
        return mapToUserResponseWithoutToken(updatedUser);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new HttpClientErrorException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado"
            );
        }
        userRepository.deleteById(id);
    }

    // ========== UTILITÁRIOS ==========
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", apiKey);
        headers.set("Authorization", "Bearer " + apiKey);
        return headers;
    }



    private UserResponse mapToUserResponse(User user, String accessToken, String refreshToken, long expiresAt) {
        return UserResponse.builder()
                .id(user.getSub())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .officeName(user.getOfficeName())
                .status(user.getStatus())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresAt(expiresAt)
                .build();
    }


    private UserResponse mapToUserResponseWithoutToken(User user) {
        return UserResponse.builder()
                .id(user.getSub())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .officeName(user.getOfficeName())
                .status(user.getStatus())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}