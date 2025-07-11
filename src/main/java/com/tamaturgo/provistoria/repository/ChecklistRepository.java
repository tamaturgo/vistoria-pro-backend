package com.tamaturgo.provistoria.repository;


import com.tamaturgo.provistoria.dto.checklist.ChecklistItemSummary;
import com.tamaturgo.provistoria.dto.checklist.ChecklistResponse;
import com.tamaturgo.provistoria.dto.checklist.ChecklistSimple;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.tamaturgo.jooq.generated.tables.Checklist.CHECKLIST;
import static com.tamaturgo.jooq.generated.tables.ChecklistItem.CHECKLIST_ITEM;
import static com.tamaturgo.jooq.generated.tables.ChecklistItemsChecklist.CHECKLIST_ITEMS_CHECKLIST;

@Repository
@RequiredArgsConstructor
public class ChecklistRepository {
    private final DSLContext dsl;

    public void insertChecklist(UUID id, UUID userId, String name, LocalDateTime createdAt) {
        dsl.insertInto(CHECKLIST)
                .set(CHECKLIST.ID, id)
                .set(CHECKLIST.USER_ID, userId)
                .set(CHECKLIST.NAME, name)
                .set(CHECKLIST.CREATED_AT, createdAt)
                .execute();
    }

    public void insertChecklistItems(UUID checklistId, List<UUID> itemIds) {
        List<org.jooq.Query> batchQueries = dsl.batchInsert(
            itemIds.stream()
                    .map((itemId, index) -> dsl.insertInto(CHECKLIST_ITEMS_CHECKLIST)
                        .set(CHECKLIST_ITEMS_CHECKLIST.CHECKLIST_ID, checklistId)
                        .set(CHECKLIST_ITEMS_CHECKLIST.CHECKLIST_ITEM_ID, itemId)
                        .set(CHECKLIST_ITEMS_CHECKLIST.ORDER_INDEX, index))
                    .toList()
        ).execute();
    }

    public List<ChecklistItemSummary> findItemSummariesByIds(List<UUID> ids) {
        return dsl.select(CHECKLIST_ITEM.ID, CHECKLIST_ITEM.NAME)
                .from(CHECKLIST_ITEM)
                .where(CHECKLIST_ITEM.ID.in(ids))
                .fetch(record -> new ChecklistItemSummary(
                        record.get(CHECKLIST_ITEM.ID),
                        record.get(CHECKLIST_ITEM.NAME)
                ));
    }

    public List<ChecklistItemSummary> findItemSummariesByChecklistId(UUID checklistId) {
        return dsl.select(CHECKLIST_ITEM.ID, CHECKLIST_ITEM.NAME)
                .from(CHECKLIST_ITEMS_CHECKLIST)
                .join(CHECKLIST_ITEM).on(CHECKLIST_ITEMS_CHECKLIST.CHECKLIST_ITEM_ID.eq(CHECKLIST_ITEM.ID))
                .where(CHECKLIST_ITEMS_CHECKLIST.CHECKLIST_ID.eq(checklistId))
                .fetch(record -> new ChecklistItemSummary(
                        record.get(CHECKLIST_ITEM.ID),
                        record.get(CHECKLIST_ITEM.NAME)
                ));
    }

    public List<ChecklistResponse> findChecklistsByUserId(UUID userId) {
        List<ChecklistSimple> checklists = dsl.select(
                        CHECKLIST.ID,
                        CHECKLIST.NAME,
                        CHECKLIST.CREATED_AT
                )
                .from(CHECKLIST)
                .where(CHECKLIST.USER_ID.eq(userId))
                .orderBy(CHECKLIST.CREATED_AT.desc())
                .fetch(record -> new ChecklistSimple(
                        record.get(CHECKLIST.ID),
                        record.get(CHECKLIST.NAME),
                        record.get(CHECKLIST.CREATED_AT)
                ));

        return checklists.stream()
                .map(c -> {
                    List<ChecklistItemSummary> items = findItemSummariesByChecklistId(c.id());
                    return new ChecklistResponse(c.id(), c.name(), c.createdAt(), items);
                })
                .toList();
    }


}
