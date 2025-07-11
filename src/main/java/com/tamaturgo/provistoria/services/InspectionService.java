package com.tamaturgo.provistoria.services;

import com.tamaturgo.provistoria.dto.inspection.CreateInspectionRequest;
import com.tamaturgo.provistoria.dto.inspection.InspectionDetailsResponse;
import com.tamaturgo.provistoria.dto.checklist.ChecklistResponse;
import com.tamaturgo.provistoria.dto.checklist.ChecklistItemSummary;
import com.tamaturgo.provistoria.models.User;
import com.tamaturgo.provistoria.repository.InspectionRepository;
import com.tamaturgo.provistoria.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InspectionService {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final InspectionRepository inspectionRepository;

    public void createInspection(CreateInspectionRequest request, String token) {
        log.info("Iniciando criação de vistoria para propriedade: {} e cliente: {}", request.propertyId(), request.clientId());
        User user = authenticatedUserProvider.getUserFromAuthorization(token);

        UUID inspectionId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        inspectionRepository.insertInspection(
                inspectionId,
                user.getId(),
                request.propertyId(),
                request.clientId(),
                request.date(),
                now,
                "PENDENTE",
                request.notes()
        );
        log.info("Vistoria criada com ID: {} para usuário: {}", inspectionId, user.getId());

        inspectionRepository.linkChecklistsToInspection(inspectionId, request.checklistIds());
        log.info("Checklists vinculados à vistoria {}: {}", inspectionId, request.checklistIds());
    }

    public List<InspectionDetailsResponse> listInspections(String token, int page, int size) {
        User user = authenticatedUserProvider.getUserFromAuthorization(token);
        log.info("Listando vistorias para usuário {} (page={}, size={})", user.getId(), page, size);
        List<InspectionDetailsResponse> inspections = inspectionRepository.findInspectionsWithChecklistsAndItems(user.getId(), page, size);
        log.info("{} vistorias encontradas para usuário {} (page={}, size={})", inspections.size(), user.getId(), page, size);
        return inspections;
    }
}
