package com.tamaturgo.provistoria.repository;

import com.tamaturgo.provistoria.dto.checklist.ChecklistItemSummary;
import com.tamaturgo.provistoria.dto.checklist.ChecklistResponse;
import com.tamaturgo.provistoria.dto.inspection.InspectionDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.tamaturgo.jooq.generated.tables.ChecklistItem.CHECKLIST_ITEM;
import static com.tamaturgo.jooq.generated.tables.ChecklistItemsChecklist.CHECKLIST_ITEMS_CHECKLIST;
import static com.tamaturgo.jooq.generated.tables.Inspection.INSPECTION;
import static com.tamaturgo.jooq.generated.tables.InspectionChecklists.INSPECTION_CHECKLISTS;

@Repository
@RequiredArgsConstructor
public class InspectionRepository {

    private final DSLContext dsl;

    public void insertInspection(UUID inspectionId,
                                 UUID userId,
                                 UUID propertyId,
                                 UUID clientId,
                                 LocalDateTime date,
                                 LocalDateTime createdAt,
                                 String status,
                                 String notes) {

        dsl.insertInto(INSPECTION)
                .set(INSPECTION.ID, inspectionId)
                .set(INSPECTION.USER_ID, userId)
                .set(INSPECTION.PROPERTY_ID, propertyId)
                .set(INSPECTION.CLIENT_ID, clientId)
                .set(INSPECTION.DATE, date.toLocalDate())
                .set(INSPECTION.CREATED_AT, createdAt)
                .set(INSPECTION.STATUS, status)
                .set(INSPECTION.NOTES, notes)
                .execute();
    }

    public void linkChecklistsToInspection(UUID inspectionId, List<UUID> checklistIds) {
        var batch = dsl.batch(
                checklistIds.stream()
                        .map(checklistId -> dsl.insertInto(INSPECTION_CHECKLISTS)
                                .set(INSPECTION_CHECKLISTS.INSPECTION_ID, inspectionId)
                                .set(INSPECTION_CHECKLISTS.CHECKLIST_ID, checklistId))
                        .toList()
        );
        batch.execute();
    }

    public List<InspectionDetailsResponse> findInspectionsWithChecklistsAndItems(UUID userId, int page, int size) {
        var inspections = dsl.selectFrom(INSPECTION)
                .where(INSPECTION.USER_ID.eq(userId))
                .orderBy(INSPECTION.DATE.desc())
                .limit(size)
                .offset(page * size)
                .fetch();

        return inspections.stream().map(inspectionRecord -> {
            UUID inspectionId = inspectionRecord.getId();

            var checklistIds = dsl.select(INSPECTION_CHECKLISTS.CHECKLIST_ID)
                    .from(INSPECTION_CHECKLISTS)
                    .where(INSPECTION_CHECKLISTS.INSPECTION_ID.eq(inspectionId))
                    .fetch(INSPECTION_CHECKLISTS.CHECKLIST_ID);

            List<ChecklistResponse> checklists = checklistIds.stream().map(checklistId -> {
                var checklistRecord = dsl.selectFrom(com.tamaturgo.jooq.generated.tables.Checklist.CHECKLIST)
                        .where(com.tamaturgo.jooq.generated.tables.Checklist.CHECKLIST.ID.eq(checklistId))
                        .fetchOne();

                var itemRecords = dsl.select(CHECKLIST_ITEM.ID, CHECKLIST_ITEM.NAME)
                        .from(CHECKLIST_ITEMS_CHECKLIST)
                        .join(CHECKLIST_ITEM)
                        .on(CHECKLIST_ITEMS_CHECKLIST.CHECKLIST_ITEM_ID.eq(CHECKLIST_ITEM.ID))
                        .where(CHECKLIST_ITEMS_CHECKLIST.CHECKLIST_ID.eq(checklistId))
                        .orderBy(CHECKLIST_ITEMS_CHECKLIST.ORDER_INDEX.asc())
                        .fetch();

                List<ChecklistItemSummary> items = itemRecords.stream()
                        .map(r -> new ChecklistItemSummary(
                                r.get(CHECKLIST_ITEM.ID),
                                r.get(CHECKLIST_ITEM.NAME)
                        ))
                        .toList();

                assert checklistRecord != null;
                return new ChecklistResponse(
                        checklistRecord.getId(),
                        checklistRecord.getName(),
                        checklistRecord.getCreatedAt(),
                        items
                );
            }).toList();

            return new InspectionDetailsResponse(
                    inspectionRecord.getId(),
                    inspectionRecord.getPropertyId(),
                    inspectionRecord.getClientId(),
                    inspectionRecord.getDate().atStartOfDay(),
                    inspectionRecord.getStatus(),
                    inspectionRecord.getNotes(),
                    inspectionRecord.getCreatedAt(),
                    checklists
            );
        }).toList();
    }

}