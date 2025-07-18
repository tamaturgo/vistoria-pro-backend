package com.tamaturgo.provistoria.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserClient {
    private UUID userId;
    private UUID clientId;
}
