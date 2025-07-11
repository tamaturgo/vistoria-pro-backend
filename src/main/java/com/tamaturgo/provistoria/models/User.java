package com.tamaturgo.provistoria.models;

import lombok.Data;

import java.util.UUID;
@Data
public class User {
    private UUID id;
    private UUID sub;
    private String email;
    private String fullName;
    private String officeName;
    private String status;
    private String role;
    private String createdAt;
    private String updatedAt;
}
