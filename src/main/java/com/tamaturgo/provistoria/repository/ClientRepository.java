package com.tamaturgo.provistoria.repository;

import com.tamaturgo.jooq.generated.tables.Clients;
import com.tamaturgo.jooq.generated.tables.records.ClientsRecord;
import com.tamaturgo.provistoria.models.Client;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepository {
    private final DSLContext dsl;

    public Optional<Client> findByEmail(String email) {
        var record = dsl.selectFrom(Clients.CLIENTS)
                .where(Clients.CLIENTS.EMAIL.eq(email))
                .fetchOne();

        return Optional.ofNullable(record).map(this::mapToClient);
    }

    public Client save(Client newClient) {
        UUID clientId = newClient.getId() != null ? newClient.getId() : UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        dsl.insertInto(Clients.CLIENTS)
                .set(Clients.CLIENTS.ID, clientId)
                .set(Clients.CLIENTS.NAME, newClient.getName())
                .set(Clients.CLIENTS.EMAIL, newClient.getEmail())
                .set(Clients.CLIENTS.DOCUMENT, newClient.getDocument())
                .set(Clients.CLIENTS.PHONE, newClient.getPhone())
                .set(Clients.CLIENTS.CREATED_AT, now)
                .set(Clients.CLIENTS.UPDATED_AT, now)
                .execute();

        newClient.setId(clientId);
        newClient.setCreatedAt(now);
        newClient.setUpdatedAt(now);
        return newClient;
    }

    private Client mapToClient(ClientsRecord record) {
        Client client = new Client();
        client.setId(record.getId());
        client.setName(record.getName());
        client.setEmail(record.getEmail());
        client.setDocument(record.getDocument());
        client.setPhone(record.getPhone());
        client.setCreatedAt(record.getCreatedAt());
        client.setUpdatedAt(record.getUpdatedAt());
        return client;
    }
}
