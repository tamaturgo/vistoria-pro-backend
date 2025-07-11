package com.tamaturgo.provistoria.dto.checklist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ChecklistResponse(
    UUID id,
    String name,
    LocalDateTime createdAt,
    List<ChecklistItemSummary> items
) {
}
