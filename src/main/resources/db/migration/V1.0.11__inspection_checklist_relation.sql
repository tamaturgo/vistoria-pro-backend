-- Remover a coluna antiga de referência única
ALTER TABLE inspection DROP COLUMN checklist_id;

-- Criar a nova tabela de relacionamento N:N entre vistorias e checklists
CREATE TABLE inspection_checklists (
    inspection_id UUID NOT NULL REFERENCES inspection(id) ON DELETE CASCADE,
    checklist_id UUID NOT NULL REFERENCES checklist(id) ON DELETE CASCADE,
    PRIMARY KEY (inspection_id, checklist_id)
);