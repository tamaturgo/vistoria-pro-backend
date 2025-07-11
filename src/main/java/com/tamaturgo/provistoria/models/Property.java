package com.tamaturgo.provistoria.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class Property {
    private UUID id;
    private String name;
    private String address;
    private String number;
    private String complement;
    private String type;
    private String block;
    private String tower;
    private UUID clientId;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}