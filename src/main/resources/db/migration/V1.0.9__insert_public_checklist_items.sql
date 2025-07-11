-- SALA
INSERT INTO checklist_item (id, name, public_item, user_id) VALUES
(gen_random_uuid(), 'Piso em bom estado', TRUE, NULL),
(gen_random_uuid(), 'Paredes sem trincas', TRUE, NULL),
(gen_random_uuid(), 'Teto sem infiltrações', TRUE, NULL),
(gen_random_uuid(), 'Janelas funcionando', TRUE, NULL),
(gen_random_uuid(), 'Instalação elétrica adequada', TRUE, NULL),
(gen_random_uuid(), 'Iluminação suficiente', TRUE, NULL);

-- COZINHA
INSERT INTO checklist_item (id, name, public_item, user_id) VALUES
(gen_random_uuid(), 'Piso antiderrapante', TRUE, NULL),
(gen_random_uuid(), 'Azulejos bem fixados', TRUE, NULL),
(gen_random_uuid(), 'Pia e bancada íntegras', TRUE, NULL),
(gen_random_uuid(), 'Torneiras funcionando', TRUE, NULL),
(gen_random_uuid(), 'Instalação de gás segura', TRUE, NULL),
(gen_random_uuid(), 'Ventilação adequada', TRUE, NULL);

-- QUARTO
INSERT INTO checklist_item (id, name, public_item, user_id) VALUES
(gen_random_uuid(), 'Piso nivelado', TRUE, NULL),
(gen_random_uuid(), 'Paredes sem umidade', TRUE, NULL),
(gen_random_uuid(), 'Janelas vedando bem', TRUE, NULL),
(gen_random_uuid(), 'Tomadas funcionando', TRUE, NULL),
(gen_random_uuid(), 'Iluminação adequada', TRUE, NULL),
(gen_random_uuid(), 'Armários fixos seguros', TRUE, NULL);

-- BANHEIRO
INSERT INTO checklist_item (id, name, public_item, user_id) VALUES
(gen_random_uuid(), 'Impermeabilização adequada', TRUE, NULL),
(gen_random_uuid(), 'Louças bem fixadas', TRUE, NULL),
(gen_random_uuid(), 'Chuveiro funcionando', TRUE, NULL),
(gen_random_uuid(), 'Instalação elétrica segura', TRUE, NULL),
(gen_random_uuid(), 'Ventilação presente', TRUE, NULL),
(gen_random_uuid(), 'Piso antiderrapante', TRUE, NULL);

-- VARANDA
INSERT INTO checklist_item (id, name, public_item, user_id) VALUES
(gen_random_uuid(), 'Guarda-corpo seguro', TRUE, NULL),
(gen_random_uuid(), 'Piso adequado', TRUE, NULL),
(gen_random_uuid(), 'Impermeabilização OK', TRUE, NULL),
(gen_random_uuid(), 'Escoamento de água', TRUE, NULL),
(gen_random_uuid(), 'Estrutura íntegra', TRUE, NULL);

-- ÁREA TÉCNICA
INSERT INTO checklist_item (id, name, public_item, user_id) VALUES
(gen_random_uuid(), 'Instalações elétricas organizadas', TRUE, NULL),
(gen_random_uuid(), 'Quadro elétrico adequado', TRUE, NULL),
(gen_random_uuid(), 'Tubulações bem fixadas', TRUE, NULL),
(gen_random_uuid(), 'Acesso facilitado', TRUE, NULL),
(gen_random_uuid(), 'Ventilação presente', TRUE, NULL);
