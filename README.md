# Spring Boot Postgres Bookstore API

A Spring Boot REST API for managing **books** and **authors**, backed by **PostgreSQL** with **Spring Data JPA** and **Hibernate**. The project follows a clean layered architecture (Controller → Service → Repository) with DTO/Mapper separation, ModelMapper-based mapping, and integration tests using an in-memory H2 database.

---

## Features

- RESTful CRUD endpoints for `Author` and `Book` resources
- Layered architecture: `Controller` → `Service` → `Repository`
- DTO ↔ Entity mapping via a generic `Mapper<E, D>` abstraction (powered by **ModelMapper**)
- One-to-Many relationship between `Author` and `Book`
- Custom `BookEntity` `Persistable<String>` implementation for ISBN-as-primary-key handling
- Partial updates via `PATCH` (e.g. update author's name or age, update book title)
- Integration tests with **Spring Boot Test**, **MockMvc**, and **H2** for an isolated test database
- Local PostgreSQL instance via **Docker Compose**
- **Lombok** for boilerplate reduction
- **Spring Boot DevTools** for hot reload during development

---

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 4.x (Web, Data JPA, DevTools) |
| ORM | Hibernate (via Spring Data JPA) |
| Database | PostgreSQL (runtime) / H2 (tests) |
| Mapping | ModelMapper |
| Build | Maven |
| Testing | JUnit 5, Spring Boot Test, MockMvc |
| Infra | Docker Compose |
| Utility | Lombok |

---

## Project Structure

```
PostgresqlDatabaseWithConfig/
├── Dockerfile
├── .dockerignore
├── docker-compose.yml
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/example/PostgresqlDatabaseWithConfig/
    │   │   ├── PostgresqlDatabaseWithConfigApplication.java
    │   │   ├── Utils.java
    │   │   ├── config/                 # MapperConfig (ModelMapper bean)
    │   │   ├── controllers/            # REST controllers
    │   │   ├── services/               # Service interfaces + impl
    │   │   ├── repositories/           # Spring Data JPA repositories
    │   │   ├── mappers/                # Generic Mapper interface + impls
    │   │   └── domain/
    │   │       ├── entities/           # JPA entities (AuthorEntity, BookEntity)
    │   │       └── dto/                # DTOs (AuthorDto, BookDto, DeleteResultDto)
    │   └── resources/
    │       └── application.properties
    └── test/
        ├── java/.../                   # Integration tests (controllers + repositories)
        └── resources/application.properties
```

---

## Getting Started

### Prerequisites

- **Java 17+**
- **Maven 3.8+** (or use the bundled `./mvnw`)
- **Docker** & **Docker Compose** (optional, for the local PostgreSQL container or fully containerized run)

### 1. Clone the repository

```bash
git clone https://github.com/<your-username>/spring-boot-postgres-bookstore-api.git
cd spring-boot-postgres-bookstore-api/PostgresqlDatabaseWithConfig
```

### Option A — Fully containerized (recommended)

Build and run **both** the Spring Boot app and PostgreSQL with one command:

```bash
docker compose up --build
```

This will:

- Start a PostgreSQL 16 container on port `5432` with a persistent named volume (`postgres_data`)
- Build the application image via the multi-stage `Dockerfile` (Maven build → JRE runtime, layered jar)
- Start the app on **http://localhost:8080**, pointing at the `db` service via `SPRING_DATASOURCE_URL`
- Wait for the database to be healthy (via `pg_isready`) before starting the app

Stop everything with:

```bash
docker compose down            # keep data
docker compose down -v         # also remove the postgres_data volume
```

### Option B — Run PostgreSQL in Docker, app locally

Useful for active development with hot reload via Spring Boot DevTools:

```bash
# Terminal 1: start only the database
docker compose up -d db

# Terminal 2: run the app from your machine
./mvnw spring-boot:run
```

The application will start on **http://localhost:8080**.

Schema is auto-managed by Hibernate (`spring.jpa.hibernate.ddl-auto=update`), so tables are created/updated on startup.

### Run the tests

```bash
./mvnw test
```

Integration tests run against an embedded **H2** database, so no external setup is required.

---

## API Endpoints

### Authors

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST`   | `/authors`                       | Create a new author |
| `GET`    | `/authors`                       | List all authors |
| `GET`    | `/authors/{id}`                  | Get an author by ID |
| `GET`    | `/authors/book/{id}`             | List books for a given author |
| `PUT`    | `/authors/{id}`                  | Full update of an author |
| `PATCH`  | `/authors/{id}?name={name}`      | Update only the author's name |
| `PATCH`  | `/authors/{id}?age={age}`        | Update only the author's age |
| `DELETE` | `/authors/{id}`                  | Delete an author by ID |

### Books

| Method | Endpoint | Description |
| --- | --- | --- |
| `PUT`    | `/books/{isbn}`                            | Create or upsert a book by ISBN |
| `PUT`    | `/books/{isbn}?authorId={id}`              | Create a book attached to an existing author |
| `GET`    | `/books`                                   | List all books |
| `GET`    | `/books?authorId={id}`                     | List all books by a given author |
| `GET`    | `/books/{isbn}`                            | Get a book by ISBN |
| `PATCH`  | `/books/{isbn}?title={title}`              | Update only the book title |
| `DELETE` | `/books/{isbn}`                            | Delete a book by ISBN |

---

## Example Requests

### Create an author

```bash
curl -X POST http://localhost:8080/authors \
  -H "Content-Type: application/json" \
  -d '{"name": "George Orwell", "age": 46}'
```

### Create a book attached to that author

```bash
curl -X PUT "http://localhost:8080/books/978-0451524935?authorId=1" \
  -H "Content-Type: application/json" \
  -d '{"isbn": "978-0451524935", "title": "1984"}'
```

### List all books for an author

```bash
curl http://localhost:8080/books?authorId=1
```

---

## Configuration

Default configuration lives in `src/main/resources/application.properties`:

```properties
spring.application.name=PostgresqlDatabaseWithConfig
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
```

For production, override these via environment variables or a separate profile (`application-prod.properties`) and **never commit real credentials**.

---

## Roadmap / Ideas

- [ ] Global exception handling with `@ControllerAdvice`
- [ ] Bean validation (`@Valid`, `@NotNull`, etc.) on DTOs
- [ ] Pagination & sorting on list endpoints
- [ ] OpenAPI/Swagger documentation (`springdoc-openapi`)
- [ ] Authentication & authorization with Spring Security + JWT
- [ ] Database migrations with Flyway or Liquibase
- [ ] CI workflow (GitHub Actions) running `mvn verify` on PRs
- [x] Dockerfile for the application itself

---

## License

This project is released under the **MIT License**. See `LICENSE` for details.

---

## Author

Built as a hands-on Spring Boot learning project exploring REST APIs, JPA/Hibernate, and PostgreSQL. Feedback and contributions are welcome — feel free to open an issue or PR.
