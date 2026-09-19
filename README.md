# k-vashalomidze

An earlier Spring Boot backend for my personal site. It serves a public profile and blog, and ships an OAuth2 protected admin panel for managing everything behind it.

## What it does

- Public API for profile info and blog posts, open to anyone
- Admin panel, behind OAuth2 login, for profile, education, resume, and post content
- Newsletter signup with a subscriber list and mail delivery
- A webhook endpoint for external triggers
- Caffeine caching on top of PostgreSQL and JPA

## Stack

Java, Spring Boot, Spring Security (OAuth2), PostgreSQL, JPA, Caffeine cache, Spring Mail

## Running it locally

```bash
docker compose up -d
POSTGRES_USER=youruser POSTGRES_PASSWORD=yourpassword ./mvnw spring-boot:run
```

Set `POSTGRES_USER` and `POSTGRES_PASSWORD` to whatever you pass into `docker-compose.yml`. `POSTGRES_DB` defaults to `portfolio`.

## Note

This predates the portfolio site I run today, which lives elsewhere. I kept this one public because the backend itself, the auth setup, the caching, the admin CRUD, still holds up as a reference.
