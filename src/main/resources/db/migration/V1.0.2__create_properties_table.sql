CREATE TABLE properties (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(150) NOT NULL,                         -- Nome do Imóvel
    address VARCHAR(255) NOT NULL,                      -- Endereço
    number VARCHAR(20),                                 -- Número
    complement VARCHAR(100),                            -- Complemento
    type VARCHAR(50) NOT NULL,                          -- Tipo (ex: residencial, comercial, etc.)
    block VARCHAR(50),                                  -- Bloco
    tower VARCHAR(50),                                  -- Torre

    tags TEXT[],                                        -- Lista de tags (array de texto)

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
