package com.tamaturgo.provistoria.repository;


import com.tamaturgo.provistoria.dto.checklist.ChecklistItemSummary;
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
        int index = 0;
        for (UUID itemId : itemIds) {
            dsl.insertInto(CHECKLIST_ITEMS_CHECKLIST)
                    .set(CHECKLIST_ITEMS_CHECKLIST.CHECKLIST_ID, checklistId)
                    .set(CHECKLIST_ITEMS_CHECKLIST.CHECKLIST_ITEM_ID, itemId)
                    .set(CHECKLIST_ITEMS_CHECKLIST.ORDER_INDEX, index++)
                    .execute();
        }
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

}
