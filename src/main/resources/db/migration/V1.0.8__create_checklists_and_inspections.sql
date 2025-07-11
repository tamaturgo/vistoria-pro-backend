-- Tabela de modelos de checklist
CREATE TABLE checklist (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    name TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Tabela de vistorias
CREATE TABLE inspection (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    property_id UUID NOT NULL REFERENCES properties(id),
    client_id UUID NOT NULL REFERENCES clients(id),
    date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status TEXT NOT NULL CHECK (status IN ('PENDENTE', 'FINALIZADA', 'CANCELADA')),
    notes TEXT,
    checklist_id UUID REFERENCES checklist(id) ON DELETE SET NULL
);

-- Tabela de itens (públicos ou personalizados)
CREATE TABLE checklist_item (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    public_item BOOLEAN NOT NULL DEFAULT FALSE,
    user_id UUID REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Associação N:N entre modelos e itens
CREATE TABLE checklist_items_checklist (
    checklist_id UUID NOT NULL REFERENCES checklist(id) ON DELETE CASCADE,
    checklist_item_id UUID NOT NULL REFERENCES checklist_item(id) ON DELETE CASCADE,
    order_index INT DEFAULT 0,
    PRIMARY KEY (checklist_id, checklist_item_id)
);

-- Resultados de uma inspeção (item + status + anotação + imagem)
CREATE TABLE inspection_result (
    id UUID PRIMARY KEY,
    inspection_id UUID NOT NULL REFERENCES inspection(id) ON DELETE CASCADE,
    checklist_item_id UUID NOT NULL REFERENCES checklist_item(id),
    status TEXT NOT NULL,
    annotation TEXT,
    image_url TEXT,
    order_index INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);