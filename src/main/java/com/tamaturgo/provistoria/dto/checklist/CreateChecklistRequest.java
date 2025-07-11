package com.tamaturgo.provistoria.dto.checklist;

import java.util.List;
import java.util.UUID;

public record CreateChecklistRequest(String name, List<UUID> itemIds) {}
