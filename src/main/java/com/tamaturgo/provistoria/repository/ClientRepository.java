package com.tamaturgo.provistoria.repository;

import com.tamaturgo.provistoria.models.Client;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClientRepository {
    public Optional<Client> findByEmail(String email) {
        return Optional.empty();
    }

    public Client save(Client newClient) {
        return null;
    }
}
