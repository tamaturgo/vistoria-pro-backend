-- Ativa extensão para UUIDs (recomendo criar isso fora do Flyway também no Supabase)
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    full_name VARCHAR(150) NOT NULL,
    office_name VARCHAR(150), -- Nome do escritório (opcional)
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,

    has_crea BOOLEAN NOT NULL DEFAULT false,

    -- Auxiliares
    role VARCHAR(50) NOT NULL DEFAULT 'ENGINEER', -- Pode ser ADMIN futuramente
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE', -- ACTIVE, INACTIVE, BLOCKED

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_email CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);
