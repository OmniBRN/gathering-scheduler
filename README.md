# Gathering Scheduler

A small web app for figuring out *when* and *where* a group should meet up.

One person creates a gathering and gets a link to share. Everyone who opens it
joins with a username and a 4-digit PIN — no email, no account, no sign-up flow.
Members then propose time slots with a location, and the group votes on them.

> **Status: early work in progress.** The backend is being built out
> feature-by-feature. There is no frontend yet, and no authentication on the
> endpoints — the PINs are stored (hashed) but not yet checked anywhere. Don't
> deploy this.

## Tech stack

- Java 21 / Spring Boot 4
- Spring Data JPA + Hibernate
- PostgreSQL 17, schema managed by Flyway
- Argon2 (via Spring Security Crypto + BouncyCastle) for hashing member PINs
- springdoc-openapi for API docs
- Docker Compose for the database and Adminer

## Getting started

You'll need Docker (or a local Postgres) and a JDK 21.

```bash
# 1. database credentials
cp compose/.env.example compose/.env
$EDITOR compose/.env

# 2. start postgres + adminer
docker compose -f compose/compose.yml up -d

# 3. run the backend (Flyway applies the migrations on startup)
cd backend
./mvnw spring-boot:run
```

The API listens on `http://localhost:3000`, Swagger UI is at
`http://localhost:3000/swagger-ui.html`, and Adminer is at
`http://localhost:8080`.

The backend reads the database credentials straight out of `compose/.env`, so
both halves stay in sync from one file.

## Data model

| Table | What it holds |
| --- | --- |
| `gathering` | The event itself — an id and a name |
| `gathering_members` | People in a gathering: username, hashed PIN, admin flag. Keyed by `(gathering_id, id)` so a member only exists inside one gathering |
| `time_and_location` | A proposed slot: start/end time, lat/long, whether it's the primary pick |
| `time_and_location_votes` | One row per member vote on a proposal |

## API

Base path `/api/gathering`. Everything is unauthenticated at the moment.

**Gatherings**

| Method | Path | |
| --- | --- | --- |
| `POST` | `/api/gathering/` | Create a gathering; the caller becomes its first admin |
| `GET` | `/api/gathering/{id}` | Fetch one |
| `PUT` | `/api/gathering/{id}` | Rename |
| `DELETE` | `/api/gathering/{id}` | Delete (cascades to members and proposals) |

**Members**

| Method | Path | |
| --- | --- | --- |
| `POST` | `/api/gathering/{gatheringId}/user/` | Join a gathering with a username + PIN |
| `GET` | `/api/gathering/{gatheringId}/user/all` | List members |
| `GET` | `/api/gathering/{gatheringId}/user/{userId}` | Fetch one member |
| `PUT` | `/api/gathering/{gatheringId}/user/{userId}/grantAdmin` | Promote to admin |
| `PUT` | `/api/gathering/{gatheringId}/user/{userId}/revokeAdmin` | Demote (refuses on the last admin) |

**Time & location proposals** — `/api/gathering/{gatheringId}/tal`, controller
scaffolded, endpoints not written yet.

Usernames are capped at 20 characters, gathering names at 128, and PINs must be
exactly 4 digits.

## Roadmap

- [ ] Time & location CRUD endpoints
- [ ] Voting on proposals
- [ ] Actually verify the PIN — sessions or tokens for member requests
- [ ] Frontend (`frontend/` is still an empty folder)
- [ ] Tests beyond the generated context-load test

## Layout

```
backend/    Spring Boot app
frontend/   (empty for now)
compose/    docker compose file + database env
```
