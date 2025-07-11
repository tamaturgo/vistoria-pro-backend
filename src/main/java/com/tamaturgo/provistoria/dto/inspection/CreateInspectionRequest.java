package com.tamaturgo.provistoria.dto.inspection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateInspectionRequest(
         UUID propertyId,
         UUID clientId,
         LocalDateTime date,
         String notes,
         List<UUID> checklistIds
) {}