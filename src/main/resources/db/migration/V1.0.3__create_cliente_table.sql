CREATE TABLE clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    name VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    document VARCHAR(20),        -- CPF ou CNPJ (formato livre, pode validar na aplicação)
    phone VARCHAR(30),           -- Telefone de contato

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
