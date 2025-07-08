package com.tamaturgo.provistoria.dto.user;

public record RegisterRequest(
        String fullName,
        String officeName,
        String email,
        String password,
        Boolean hasCrea
) {}
