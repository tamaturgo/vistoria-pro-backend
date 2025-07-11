package com.tamaturgo.provistoria.dto.inspection;

import com.tamaturgo.provistoria.dto.checklist.ChecklistResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record InspectionDetailsResponse(
    UUID id,
    UUID propertyId,
    UUID clientId,
    LocalDateTime date,
    String status,
    String notes,
    LocalDateTime createdAt,
    List<ChecklistResponse> checklists
) {}

