# Provistoria

Provistoria é uma plataforma para gestão de vistorias de imóveis, clientes e checklist personalizado, com autenticação segura e integração entre backend (Java Spring Boot) e frontend (React).

## Tecnologias

- **Backend:** Java 17, Spring Boot, JPA, Flyway, JWT
- **Frontend:** React, React Query, Axios
- **Banco de Dados:** PostgreSQL

## Instalação

### Backend

```bash
# Requisitos: Java 17+, Maven
mvn clean install
mvn spring-boot:run
```

### Frontend

```bash
# Requisitos: Node.js 18+
npm install
npm start
```

## Funcionalidades (Roadmap)

### MVP

- Autenticação de usuários (login, registro, logout)
- Cadastro e listagem de clientes
- Cadastro e listagem de propriedades
- Cadastro de vistorias com checklist personalizado
- Visualização do status anterior do checklist
- Seleção de modelos de checklist
- Feedback visual de sucesso/erro
- Validação de campos obrigatórios

### Backend

- `GET /properties` — Listar imóveis do usuário
- `GET /clients` — Listar clientes do usuário
- `POST /inspections` — Cadastrar vistoria
- `GET /inspections/:propertyId/last` — Buscar última vistoria do imóvel
- `GET /checklist-templates` — Listar modelos de checklist
- `GET /checklist-templates/:id/items` — Listar itens do modelo
- `POST /checklist-templates` — Criar modelos personalizados
- `POST /inspections/:id/checklist` — Salvar checklist da vistoria

### Frontend

- Lógica de logout (remover sessão)
- Proteção de rotas (redirecionar para login se não autenticado)
- Conectar formulário de imóvel com endpoint `POST /properties`
- Listar imóveis no dashboard usando React Query
- Navegar até detalhes do imóvel (`GET /properties/:id`)
- Formulário para cadastro de vistoria com seleção/autocomplete de imóvel e cliente
- Seleção do modelo de checklist durante cadastro da vistoria
- UI para visualização e edição rápida do checklist
- Mostrar status anterior do checklist
- Salvar vistoria junto com checklist personalizado
- Feedback visual para sucesso/erro
- Validação de campos obrigatórios e formatos
- Loading no botão durante criação de vistoria

### Futuro

- Sistema de anotações por vistoria
- Edição posterior do checklist da vistoria
- Compartilhamento e visualização pública de laudos
- Relatórios e dashboard para acompanhamento de vistorias por imóvel/cliente

## Como contribuir

1. Faça um fork do projeto
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Commit suas alterações (`git commit -m 'Adiciona nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT.

