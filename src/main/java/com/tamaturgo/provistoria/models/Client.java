package com.tamaturgo.provistoria.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Client {
    UUID id;
    String name;
    String email;
    String document;
    String phone;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}