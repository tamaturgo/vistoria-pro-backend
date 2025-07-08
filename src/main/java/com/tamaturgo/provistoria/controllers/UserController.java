package com.tamaturgo.provistoria.controllers;

import com.tamaturgo.provistoria.dto.user.LoginRequest;
import com.tamaturgo.provistoria.dto.user.RegisterRequest;
import com.tamaturgo.provistoria.dto.user.UserResponse;
import com.tamaturgo.provistoria.services.UserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        return  ResponseEntity.ok(userService.authenticateUser(request));
    }
}
