package com.tamaturgo.provistoria.dto.checklist;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChecklistSimple(
        UUID id,
        String name,
        LocalDateTime createdAt
) {}