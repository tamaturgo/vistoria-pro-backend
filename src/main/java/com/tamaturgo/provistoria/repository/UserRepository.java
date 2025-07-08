package com.tamaturgo.provistoria.repository;

import com.tamaturgo.provistoria.models.User;
import org.jooq.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.tamaturgo.jooq.generated.Tables.USERS;

@Repository
public class UserRepository {

    private final DSLContext dsl;

    public UserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public User save(User user) {
            return dsl.insertInto(USERS)
                    .set(USERS.SUB, user.getSub())
                    .set(USERS.EMAIL, user.getEmail())
                    .set(USERS.FULL_NAME, user.getFullName())
                    .set(USERS.OFFICE_NAME, user.getOfficeName())
                    .set(USERS.STATUS, user.getStatus())
                    .set(USERS.ROLE, user.getRole())
                    .set(USERS.CREATED_AT, LocalDateTime.now())
                    .set(USERS.UPDATED_AT, LocalDateTime.now())
                    .returning()
                    .fetchOneInto(User.class);
    }

    public boolean existsByEmail(String email) {
        return dsl.fetchExists(
                dsl.selectFrom(USERS)
                        .where(USERS.EMAIL.eq(email))
        );
    }

    public void deleteById(UUID id) {
        dsl.deleteFrom(USERS)
                .where(USERS.SUB.eq(id))
                .execute();
    }

    public boolean existsById(UUID id) {
        return dsl.fetchExists(
                dsl.selectFrom(USERS)
                        .where(USERS.SUB.eq(id))
        );
    }

    public Optional<User> findById(UUID id) {
        return dsl.selectFrom(USERS)
                .where(USERS.SUB.eq(id))
                .fetchOptionalInto(User.class);
    }

    public List<User> findAll() {
        return dsl.selectFrom(USERS)
                .fetchInto(User.class);
    }

    public Optional<User> findByEmail(String email) {
        return dsl.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptionalInto(User.class);
    }

    public Optional<User> findBySub(String sub) {
        return dsl.selectFrom(USERS)
                .where(USERS.SUB.eq(UUID.fromString(sub)))
                .fetchOptionalInto(User.class);
    }
}