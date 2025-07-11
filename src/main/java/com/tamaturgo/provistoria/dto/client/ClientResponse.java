package com.tamaturgo.provistoria.dto.client;

import java.util.UUID;

public record ClientResponse(UUID id,
                             String name,
                             String email) {
}
