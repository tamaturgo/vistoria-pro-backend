package com.tamaturgo.provistoria.controllers;

import com.tamaturgo.provistoria.dto.LoginRequest;
import com.tamaturgo.provistoria.dto.RegisterRequest;
import com.tamaturgo.provistoria.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${supabase.api.url}")
    private String supabaseUrl;

    @Value("${supabase.api.key}")
    private String apiKey;

    private final UserService userService;
    private final RestTemplate restTemplate = new RestTemplate();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        String result = userService.createSupabaseUser(request.email(), request.password());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String url = supabaseUrl + "/auth/v1/token?grant_type=password";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", apiKey);

        String body = """
            {
              "email": "%s",
              "password": "%s"
            }
            """.formatted(request.email(), request.password());

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            if (responseBody.contains("email_not_confirmed")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email não confirmado. Verifique sua caixa de entrada.");
            }
            return ResponseEntity.status(e.getStatusCode()).body(responseBody);
        }
    }
}
