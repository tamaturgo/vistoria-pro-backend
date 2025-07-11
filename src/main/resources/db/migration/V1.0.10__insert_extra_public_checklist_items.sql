INSERT INTO checklist_item (id, name, public_item, user_id) VALUES
-- Itens gerais adicionais
(gen_random_uuid(), 'Pintura uniforme nas paredes', TRUE, NULL),
(gen_random_uuid(), 'Rodapés fixos e sem danos', TRUE, NULL),
(gen_random_uuid(), 'Interruptores e tomadas em bom estado', TRUE, NULL),
(gen_random_uuid(), 'Forro bem fixado', TRUE, NULL),
(gen_random_uuid(), 'Boa acústica do ambiente', TRUE, NULL),
(gen_random_uuid(), 'Cortinas ou persianas instaladas', TRUE, NULL),

-- Cozinha e serviço adicionais
(gen_random_uuid(), 'Armários fixos bem instalados', TRUE, NULL),
(gen_random_uuid(), 'Exaustor ou coifa funcionando', TRUE, NULL),
(gen_random_uuid(), 'Vedação da pia sem vazamentos', TRUE, NULL),
(gen_random_uuid(), 'Sifão instalado corretamente', TRUE, NULL),
(gen_random_uuid(), 'Tanque sem trincas e bem fixado', TRUE, NULL),
(gen_random_uuid(), 'Máquina de lavar com ponto de água/esgoto adequados', TRUE, NULL),
(gen_random_uuid(), 'Tomada exclusiva para máquina de lavar', TRUE, NULL),

-- Banheiro adicionais
(gen_random_uuid(), 'Box instalado corretamente', TRUE, NULL),
(gen_random_uuid(), 'Vedação da cuba sem vazamentos', TRUE, NULL),
(gen_random_uuid(), 'Registro funcionando sem goteiras', TRUE, NULL),
(gen_random_uuid(), 'Ralo com escoamento adequado', TRUE, NULL),

-- Varanda e garagem
(gen_random_uuid(), 'Tomadas externas protegidas contra intempéries', TRUE, NULL),
(gen_random_uuid(), 'Cobertura da varanda bem fixada', TRUE, NULL),
(gen_random_uuid(), 'Portão da garagem funcionando corretamente', TRUE, NULL),
(gen_random_uuid(), 'Piso da garagem com escoamento', TRUE, NULL),
(gen_random_uuid(), 'Iluminação funcional na garagem', TRUE, NULL),

-- Telhado / cobertura
(gen_random_uuid(), 'Telhas alinhadas e fixadas', TRUE, NULL),
(gen_random_uuid(), 'Calhas limpas e desobstruídas', TRUE, NULL),
(gen_random_uuid(), 'Estrutura de apoio do telhado segura', TRUE, NULL),
(gen_random_uuid(), 'Ausência de goteiras ou infiltrações no telhado', TRUE, NULL),

-- Áreas comuns de condomínio
(gen_random_uuid(), 'Portão de entrada funcionando corretamente', TRUE, NULL),
(gen_random_uuid(), 'Área de lazer em bom estado', TRUE, NULL),
(gen_random_uuid(), 'Salão de festas limpo e com estrutura preservada', TRUE, NULL),
(gen_random_uuid(), 'Brinquedoteca segura e sem danos', TRUE, NULL),
(gen_random_uuid(), 'Piscina limpa e com equipamentos funcionando', TRUE, NULL),
(gen_random_uuid(), 'Academia com equipamentos em bom estado', TRUE, NULL),
(gen_random_uuid(), 'Elevador funcionando normalmente', TRUE, NULL),
(gen_random_uuid(), 'Caixa d’água limpa e protegida', TRUE, NULL),

-- Segurança
(gen_random_uuid(), 'Extintores dentro da validade', TRUE, NULL),
(gen_random_uuid(), 'Iluminação de emergência funcional', TRUE, NULL),
(gen_random_uuid(), 'Sinalização de saída de emergência visível', TRUE, NULL),
(gen_random_uuid(), 'Trancas e fechaduras funcionando', TRUE, NULL),
(gen_random_uuid(), 'Câmeras instaladas e operando', TRUE, NULL),
(gen_random_uuid(), 'Portaria com controle de acesso ativo', TRUE, NULL),

-- Acessibilidade
(gen_random_uuid(), 'Rampa de acesso com inclinação adequada', TRUE, NULL),
(gen_random_uuid(), 'Portas com largura mínima para cadeira de rodas', TRUE, NULL),
(gen_random_uuid(), 'Barras de apoio instaladas em banheiros', TRUE, NULL),
(gen_random_uuid(), 'Sinalização em braile nos elevadores', TRUE, NULL),
(gen_random_uuid(), 'Piso tátil presente em áreas comuns', TRUE, NULL),

-- Salas comerciais / corporativas
(gen_random_uuid(), 'Cabeamento estruturado disponível', TRUE, NULL),
(gen_random_uuid(), 'Pontos de rede e telefone acessíveis', TRUE, NULL),
(gen_random_uuid(), 'Divisórias em bom estado', TRUE, NULL),
(gen_random_uuid(), 'Ambiente climatizado', TRUE, NULL),
(gen_random_uuid(), 'Forro modular bem fixado', TRUE, NULL),
(gen_random_uuid(), 'Iluminação de LED em funcionamento', TRUE, NULL),
(gen_random_uuid(), 'Sistemas de detecção de fumaça presentes', TRUE, NULL),
(gen_random_uuid(), 'Sala de espera adequada e equipada', TRUE, NULL),
(gen_random_uuid(), 'Isolamento acústico entre salas', TRUE, NULL),
(gen_random_uuid(), 'Sistemas de alarme funcional', TRUE, NULL);
