-- Adiciona relacionamento
ALTER TABLE properties
    ADD COLUMN client_id UUID;

ALTER TABLE properties
    ADD CONSTRAINT fk_properties_client
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE SET NULL;
