# Provistoria

Provistoria is a platform for managing property inspections, clients, and custom checklists, with secure authentication and integration between backend (Java Spring Boot) and frontend (React).

## Technologies

- **Backend:** Java 17, Spring Boot, JPA, Flyway, JWT
- **Frontend:** React, React Query, Axios
- **Database:** PostgreSQL

## Installation

### Backend

```bash
# Requirements: Java 17+, Maven
mvn clean install
mvn spring-boot:run
```

### Frontend

```bash
# Requirements: Node.js 18+
npm install
npm start
```

## Features (Roadmap)

### MVP

- User authentication (login, register, logout)
- Client registration and listing
- Property registration and listing
- Inspection registration with custom checklist
- View previous checklist status
- Checklist template selection
- Visual feedback for success/error
- Required fields validation

### Backend

- `GET /properties` — List user's properties
- `GET /clients` — List user's clients
- `POST /inspections` — Register inspection
- `GET /inspections/:propertyId/last` — Get property's last inspection
- `GET /checklist-templates` — List checklist templates
- `GET /checklist-templates/:id/items` — List template items
- `POST /checklist-templates` — Create custom templates
- `POST /inspections/:id/checklist` — Save inspection checklist

### Frontend

- Clean logout logic (remove session)
- Route protection (redirect to login if not authenticated)
- Connect property form to `POST /properties` endpoint
- List properties on dashboard using React Query
- Navigate to property details (`GET /properties/:id`)
- Inspection registration form with property/client autocomplete and address autofill
- Checklist template selection during inspection registration
- UI for quick checklist view and edit
- Show previous checklist status
- Save inspection with custom checklist
- Visual feedback for success/error
- Required fields and format validation
- Loading indicator on button during inspection creation

### Future

- Annotation system per inspection
- Edit inspection checklist after creation
- Public sharing and viewing of reports
- Reports and dashboard for tracking inspections by property/client

## How to contribute

1. Fork the project
2. Create a branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

