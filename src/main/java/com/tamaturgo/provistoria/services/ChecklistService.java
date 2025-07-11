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
        log.info("Iniciando criação de checklist: {} para usuário ID: {}", request.name(), user.getId());
        UUID checklistId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        checklistRepository.insertChecklist(checklistId, user.getId(), request.name(), now);
        log.info("Checklist criado com ID: {} para usuário: {}", checklistId, user.getId());
        checklistRepository.insertChecklistItems(checklistId, request.itemIds());
        log.info("Itens adicionados ao checklist: {} - Itens: {}", checklistId, request.itemIds());

        List<ChecklistItemSummary> items = checklistRepository.findItemSummariesByIds(request.itemIds());
        log.info("Resumo dos itens do checklist recuperado: {}", items.size());

        return new ChecklistResponse(checklistId, request.name(), now, items);
    }

    public List<ChecklistResponse> listChecklists(String token) {
        log.info("Listando checklists para usuário do token: {}", token);
        User user = authenticatedUserProvider.getUserFromAuthorization(token);
        List<ChecklistResponse> checklists = checklistRepository.findChecklistsByUserId(user.getId());
        log.info("{} checklists encontrados para usuário: {}", checklists.size(), user.getId());
        return checklists;
    }
}
