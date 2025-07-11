package com.tamaturgo.provistoria.repository;

import com.tamaturgo.jooq.generated.tables.Properties;
import com.tamaturgo.jooq.generated.tables.UsersClients;
import com.tamaturgo.jooq.generated.tables.records.PropertiesRecord;
import com.tamaturgo.provistoria.models.Property;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.jooq.Record;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PropertyRepository {

    private final DSLContext dsl;

    public Property save(Property property) {
        UUID id = property.getId() != null ? property.getId() : UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        dsl.insertInto(Properties.PROPERTIES)
                .set(Properties.PROPERTIES.ID, id)
                .set(Properties.PROPERTIES.NAME, property.getName())
                .set(Properties.PROPERTIES.ADDRESS, property.getAddress())
                .set(Properties.PROPERTIES.NUMBER, property.getNumber())
                .set(Properties.PROPERTIES.COMPLEMENT, property.getComplement())
                .set(Properties.PROPERTIES.TYPE, property.getType())
                .set(Properties.PROPERTIES.BLOCK, property.getBlock())
                .set(Properties.PROPERTIES.TOWER, property.getTower())
                .set(Properties.PROPERTIES.TAGS, property.getTags().toArray(new String[0]))
                .set(Properties.PROPERTIES.CLIENT_ID, property.getClientId())
                .set(Properties.PROPERTIES.CREATED_AT, now)
                .set(Properties.PROPERTIES.UPDATED_AT, now)
                .execute();

        property.setId(id);
        property.setCreatedAt(now);
        property.setUpdatedAt(now);
        return property;
    }

    public Optional<Property> findById(UUID id) {
        Record record = dsl.select()
                .from(Properties.PROPERTIES)
                .where(Properties.PROPERTIES.ID.eq(id))
                .fetchOne();

        return Optional.ofNullable(record).map(this::mapToProperty);
    }

    public List<Property> findAllByUserId(UUID userId) {
        return dsl.select(Properties.PROPERTIES.fields())
                .from(Properties.PROPERTIES)
                .join(UsersClients.USERS_CLIENTS)
                .on(UsersClients.USERS_CLIENTS.CLIENT_ID.eq(Properties.PROPERTIES.CLIENT_ID))
                .where(UsersClients.USERS_CLIENTS.USER_ID.eq(userId))
                .fetch(this::mapToProperty);
    }

    public List<Property> findAll() {
        return dsl.selectFrom(Properties.PROPERTIES)
                .fetch(this::mapToProperty);
    }

    private Property mapToProperty(Record record) {
        PropertiesRecord r = record.into(Properties.PROPERTIES);
        Property p = new Property();
        p.setId(r.getId());
        p.setName(r.getName());
        p.setAddress(r.getAddress());
        p.setNumber(r.getNumber());
        p.setComplement(r.getComplement());
        p.setType(r.getType());
        p.setBlock(r.getBlock());
        p.setTower(r.getTower());
        p.setTags(List.of(r.getTags()));
        p.setCreatedAt(r.getCreatedAt());
        p.setUpdatedAt(r.getUpdatedAt());
        p.setClientId(r.getClientId());

        return p;
    }
}
