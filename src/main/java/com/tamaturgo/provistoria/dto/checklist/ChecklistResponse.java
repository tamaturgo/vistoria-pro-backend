package com.tamaturgo.provistoria.dto.checklist;

import java.util.UUID;

public record ChecklistResponse(
    UUID id,
    String name,
    String description,
    String createdAt,
    String updatedAt,
    ChecklistItemSummary[] items
) {
}
