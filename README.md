# 📝 Blog Platform – Backend

This is the backend service for a **multi-user blogging platform**, developed using **Java 21**, **Spring Boot**, and **PostgreSQL**. It provides secure REST APIs for managing users, blog posts, comments, tags, and categories. Authentication is handled using **JWT**, and the application is fully containerized using **Docker** and Docker Compose.

---

## 🚀 Features

- 🔐 **JWT-based Authentication** – Secure login system for registered users
- ✍️ **Post Management** – Create, edit, delete, and retrieve blog posts
- 🏷️ **Tags & Categories** – Filter and classify posts easily
- 💬 **Comments** – Users can comment on any blog post
- ❤️ **Like System** – Like functionality implemented directly on post entity
- 📄 **Draft & Published Posts** – Managed using `Enum` for post status
- 📌 **Filter Posts** – By category and tags
- 🧾 **Exception Handling** – Centralized via `@ControllerAdvice`
- 📤 **DTOs & Mappers** – Clean data exchange using MapStruct
- ✅ **Validation** – Bean validation on incoming request DTOs
- 🐳 **Docker + Docker Compose** – For local development with PostgreSQL

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Spring Security + JWT**
- **PostgreSQL**
- **MapStruct**
- **Bean Validation**
- **Docker & Docker Compose**
- **Maven**

---

## 🧱 Entity Relationships

- A **User** can write many **Posts**
- A **Post** can have many **Tags** and one **Category**
- A **Post** can have many **Comments**
- Any **User** can comment or like any post
- Likes are stored as a counter in the post entity (not a separate table)

---

## 🔐 Security

- JWT Authentication with login/register endpoints
- All endpoints require authentication
- Currently supports only `USER` role (Admin role coming soon)

---
## 📦 Want to read the whole Documentation?

[Documentation](https://github.com/lakshaybxt)

## 📦 API Examples

| Method | Endpoint                 | Description                |
|--------|--------------------------|----------------------------|
| POST   | `/api/auth/register`     | Register a new user        |
| POST   | `/api/auth/login`        | Login and receive JWT      |
| GET    | `/api/posts`             | Get all published posts    |
| POST   | `/api/posts`             | Create a new post          |
| GET    | `/api/posts/filter`      | Filter by tag or category  |
| POST   | `/api/posts/{id}/like`   | Like a post                |
| POST   | `/api/posts/{id}/comment`| Add a comment              |

📌 _All endpoints require JWT token in the Authorization header._

---

## 🛠️ Setup Instructions

### ✅ Prerequisites

- Java 21+
- Maven
- Docker & Docker Compose

### 🔧 Configuration

Set the following in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/blogdb
    username: your_db_user
    password: your_db_password
  jpa:
    hibernate:
      ddl-auto: update
```

jwt:
  secret: your_jwt_secret

## 🐳 Run with Docker Compose

```bash
docker-compose up --build
```
App will run on [http://localhost:8080](http://localhost:8080)

---

## 📌 To-Do / Future Plans

- 🔄 Frontend integration (React) - Currently on it
- 📧 Email verification
- 👤 Profile picture upload
- 🛠️ Admin panel for moderation
- 📑 Pagination and advanced search
- 📄 Swagger/OpenAPI documentation

---

## 🤝 Contributing

Pull requests are welcome. If you find bugs or want to suggest features, feel free to open an issue or create a PR.

---

## 👨‍💻 Author

**Lakshay** — [@lakshaybxt](https://github.com/lakshaybxt)
