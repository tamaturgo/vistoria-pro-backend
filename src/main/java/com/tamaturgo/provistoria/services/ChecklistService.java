package com.tamaturgo.provistoria.services;

import com.tamaturgo.provistoria.dto.checklist.ChecklistItemSummary;
import com.tamaturgo.provistoria.dto.checklist.ChecklistResponse;
import com.tamaturgo.provistoria.dto.checklist.CreateChecklistRequest;
import com.tamaturgo.provistoria.models.User;
import com.tamaturgo.provistoria.repository.ChecklistRepository;
import com.tamaturgo.provistoria.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChecklistService {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final ChecklistRepository checklistRepository;

    public ChecklistResponse createChecklist(CreateChecklistRequest request, String token) {
        User user = authenticatedUserProvider.getUserFromAuthorization(token);
        UUID checklistId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        checklistRepository.insertChecklist(checklistId, user.getId(), request.name(), now);
        checklistRepository.insertChecklistItems(checklistId, request.itemIds());

        List<ChecklistItemSummary> items = checklistRepository.findItemSummariesByIds(request.itemIds());

        return new ChecklistResponse(checklistId, request.name(), now, items);
    }
}
