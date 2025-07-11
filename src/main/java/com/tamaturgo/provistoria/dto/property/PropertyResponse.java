package com.tamaturgo.provistoria.dto.property;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tamaturgo.provistoria.dto.client.ClientDTO;
import com.tamaturgo.provistoria.dto.client.ClientResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record  PropertyResponse(
    UUID id,
    String name,
    String address,
    String number,
    String complement,
    String type,
    String block,
    String tower,
    List<String> tags,
    ClientResponse client,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime updatedAt
) {}