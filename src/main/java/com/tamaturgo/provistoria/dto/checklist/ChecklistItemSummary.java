package com.tamaturgo.provistoria.dto.checklist;

import lombok.Data;

import java.util.UUID;

public record ChecklistItemSummary (
    UUID id,
    String name
){}
