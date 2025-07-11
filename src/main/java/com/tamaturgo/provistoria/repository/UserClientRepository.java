package com.tamaturgo.provistoria.repository;

import com.tamaturgo.provistoria.models.UserClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserClientRepository {


    public boolean existsByUserIdAndClientId(UUID sub, UUID id) {
        return false;
    }

    public void save(UserClient userClient) {

    }
}
