package com.tamaturgo.provistoria.services;

import com.tamaturgo.provistoria.dto.client.ClientResponse;
import com.tamaturgo.provistoria.dto.property.PropertyRequest;
import com.tamaturgo.provistoria.dto.property.PropertyResponse;
import com.tamaturgo.provistoria.models.Client;
import com.tamaturgo.provistoria.models.Property;
import com.tamaturgo.provistoria.models.User;
import com.tamaturgo.provistoria.models.UserClient;
import com.tamaturgo.provistoria.repository.ClientRepository;
import com.tamaturgo.provistoria.repository.PropertyRepository;
import com.tamaturgo.provistoria.repository.UserClientRepository;
import com.tamaturgo.provistoria.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PropertyService {

    private final ClientRepository clientRepository;
    private final PropertyRepository propertyRepository;
    private final UserClientRepository userClientRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;


    public PropertyResponse createProperty(PropertyRequest request, String authorization) {
        log.info("Iniciando criação de propriedade: {}", request.name());
        Client client = clientRepository.findByEmail(request.client().email())
                .orElseGet(() -> {
                    Client newClient = new Client();
                    newClient.setName(request.client().name());
                    newClient.setEmail(request.client().email());
                    return clientRepository.save(newClient);
                });

        User user = authenticatedUserProvider.getUserFromAuthorization(authorization);

        boolean alreadyLinked = userClientRepository.existsByUserIdAndClientId(user.getSub(), client.getId());

        if (!alreadyLinked) {
            log.info("Associando usuário {} com cliente {}", user.getId(), client.getId());
            userClientRepository.save(new UserClient(user.getId(), client.getId()));
        }

        Property property = new Property();
        property.setName(request.name());
        property.setAddress(request.address());
        property.setNumber(request.number());
        property.setComplement(request.complement());
        property.setType(request.type());
        property.setBlock(request.block());
        property.setTower(request.tower());
        property.setTags(request.tags());
        property.setClientId(client.getId());

        Property saved = propertyRepository.save(property);
        log.info("Propriedade salva com sucesso: {}", saved.getId());
        return new PropertyResponse(
                saved.getId(),
                saved.getName(),
                saved.getAddress(),
                saved.getNumber(),
                saved.getComplement(),
                saved.getType(),
                saved.getBlock(),
                saved.getTower(),
                saved.getTags(),
                new ClientResponse(client.getId(), client.getName(), client.getEmail()),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );
    }
}
