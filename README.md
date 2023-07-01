# To-do list API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

This project is an API built using Java, Spring Boot, Spring Security, JWT, Swagger UI, PostgreSQL and Redis.

This application was developed by me for studying and documentation purposes.

![diagram](https://github.com/daniloewerton/todoListBase/assets/76541826/ae281876-a425-45e1-8316-6a5387ba095c)

## Requirements

- JDK 17;
- Docker.

## Instalation

1ª - Clone the repository

```
git clone https://github.com/daniloewerton/todoListBase.git
```

2ª - Install dependencies with maven

4ª - Run the 'docker compose up' command in project root directory

4ª - Run the project using an Java IDE
The app will be up in http://localhost:8080

The swagger UI documentation will be UP in http://localhost:8080/swagger-ui/index.html

## Endpoints

```
POST /auth/login - Authenticate users.

GET /users/{id} - Get a specific user by its id.
POST /users - Create a new user.
PUT /users/{id} - Update user's information.
DELETE /users/{id} - Evict a user's cache.

GET /tasks/{id} - Get a specific task by its id.
GET {id}/users - Get all tasks by an user.
POST /tasks - Create a new task
PUT /tasks/{id} - Update task by its id
DELETE /tasks/{id} - Evict tasks for a given user.
```

## Authentication
The API uses Spring Security for authentication control. The following roles are available:

```
USER -> Standard user role for logged-in users.
ADMIN -> Admin role for managing partners (registering new partners).
```
To access protected endpoints as an ADMIN user, provide the appropriate authentication credentials in the request header.
