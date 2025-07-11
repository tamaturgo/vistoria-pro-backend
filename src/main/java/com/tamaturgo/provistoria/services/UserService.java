package com.tamaturgo.provistoria.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tamaturgo.provistoria.dto.user.*;
import com.tamaturgo.provistoria.models.User;
import com.tamaturgo.provistoria.repository.UserRepository;
import com.tamaturgo.provistoria.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${supabase.api.url}")
    private String supabaseUrl;

    @Value("${supabase.api.key}")
    private String apiKey;


    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    // ========== CRIAÇÃO DE USUÁRIO ==========
    public UserResponse registerUser(RegisterRequest request) {
        log.info("Iniciando criação de usuário: {}", request.email());
        validateEmailNotExists(request.email());

        try {
            log.info("Enviando requisição para criar usuário no Supabase: {}", request.email());
            String supabaseResponseJson = createSupabaseUser(request.email(), request.password());
            SupabaseUserResponse userResp = objectMapper.readValue(supabaseResponseJson, SupabaseUserResponse.class);
            log.info("Usuário criado no Supabase com ID: {}", userResp.id);

            User user = new User();
            user.setSub(UUID.fromString(userResp.identities.getFirst().user_id));
            user.setEmail(request.email());
            user.setFullName(request.fullName());
            user.setOfficeName(request.officeName());
            user.setStatus("ACTIVE");
            user.setRole("ENGINEER");

            User savedUser = userRepository.save(user);
            log.info("Usuário salvo no banco de dados: {}", savedUser.getEmail());
            // Faz login para obter o token JWT
            SupabaseLoginResponse loginResponse = loginSupabaseUser(request.email(), request.password());
            if (loginResponse == null || loginResponse.user == null) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
            }
            return mapToUserResponse(savedUser, loginResponse.accessToken, loginResponse.refreshToken, loginResponse.expiresAt);

        } catch (JsonProcessingException e) {
            log.error("Erro ao processar resposta do Supabase para usuário {}: {}", request.email(), e.getMessage());
            throw new RuntimeException("Erro ao criar usuário: " + e.getMessage());
        }
    }

    private void validateEmailNotExists(String email) {
        if (userRepository.existsByEmail(email)) {
            log.warn("Tentativa de registro com email já existente: {}", email);
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
    }

    // ========== AUTENTICAÇÃO ==========
    public UserResponse authenticateUser(LoginRequest request) {
        log.info("Iniciando autenticação do usuário: {}", request.email());
        try {
            SupabaseLoginResponse response = loginSupabaseUser(request.email(), request.password());
            if (response == null || response.user == null) {
                log.warn("Falha na autenticação: usuário ou senha inválidos");
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
            }
            String token = response.accessToken;
            jwtService.verify(token);

            User user = userRepository.findBySub(String.valueOf(UUID.fromString(response.user.id)))
                    .orElseThrow(() -> new HttpClientErrorException(
                            HttpStatus.UNAUTHORIZED,
                            "Usuário não encontrado"
                    ));
            log.info("Usuário autenticado com sucesso: {}", user.getEmail());

            return mapToUserResponse(user,
                    response.accessToken,
                    response.refreshToken,
                    response.expiresAt);

        } catch (HttpClientErrorException e) {
            log.error("Erro na autenticação: {}", e.getMessage());
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
            log.error("Erro ao autenticar usuário no Supabase: {}", response.getBody());
            throw new HttpClientErrorException(
                    response.getStatusCode(),
                    "Falha ao autenticar usuário no Supabase: " + response.getBody()
            );
        }
        try {
            return objectMapper.readValue(response.getBody(), SupabaseLoginResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar resposta de login do Supabase: {}", e.getMessage());
            throw new RuntimeException("Erro ao processar resposta de login do Supabase", e);
        }
    }

    // ========== OPERAÇÕES DE USUÁRIO ==========
    public UserResponse getUserById(UUID id) {
        log.info("Recuperando usuário com ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado"
                ));
        return mapToUserResponseWithoutToken(user);
    }


    public List<UserResponse> getAllUsers() {
        log.info("Recuperando todos os usuários");
        return userRepository.findAll().stream()
                .map(this::mapToUserResponseWithoutToken)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(UUID id, RegisterRequest request) {
        log.info("Atualizando usuário {} com ID: {}", request.email(), id);
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
        log.info("Deletando usuário com ID: {}", id);
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

    public UserMeResponse getCurrentUser(String authorizationHeader) {
        User user = authenticatedUserProvider.getUserFromAuthorization(authorizationHeader);
        return mapToUserMeResponse(user);
    }


    private UserMeResponse mapToUserMeResponse(User user) {
        return UserMeResponse.builder()
                .id(user.getSub())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .officeName(user.getOfficeName())
                .status(user.getStatus())
                .build();
    }
}