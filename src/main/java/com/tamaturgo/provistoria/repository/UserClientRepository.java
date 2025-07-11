package com.tamaturgo.provistoria.repository;

import com.tamaturgo.jooq.generated.tables.UsersClients;
import com.tamaturgo.provistoria.models.UserClient;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserClientRepository {

    private final DSLContext dsl;

    public boolean existsByUserIdAndClientId(UUID userId, UUID clientId) {
        return dsl.fetchExists(
                dsl.selectFrom(UsersClients.USERS_CLIENTS)
                        .where(UsersClients.USERS_CLIENTS.USER_ID.eq(userId))
                        .and(UsersClients.USERS_CLIENTS.CLIENT_ID.eq(clientId))
        );
    }

    public void save(UserClient userClient) {
        dsl.insertInto(UsersClients.USERS_CLIENTS)
                .set(UsersClients.USERS_CLIENTS.USER_ID, userClient.getUserId())
                .set(UsersClients.USERS_CLIENTS.CLIENT_ID, userClient.getClientId())
                .onConflictDoNothing()
                .execute();
    }
}
