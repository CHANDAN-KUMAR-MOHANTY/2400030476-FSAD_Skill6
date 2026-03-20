# Spring MVC Web Request Handling Demo (Skill 6)

## Prerequisites
- Java 17+
- Maven 3.9+
- VS Code with Extension Pack for Java (recommended)

## Run in VS Code
1. Open folder: `FS-Skill-6`
2. Open terminal in VS Code
3. Run:
   ```bash
   mvn spring-boot:run
   ```
4. App runs at:
   `http://localhost:8080`

## Endpoints
- `GET /welcome` -> welcome message
- `GET /count` -> total books count
- `GET /price` -> sample price
- `GET /books` -> list of book titles
- `GET /books/{id}` -> sample book details by id
- `GET /search?title=Clean Code` -> search confirmation
- `GET /author/{name}` -> author message
- `POST /addbook` -> add book JSON body
- `GET /viewbooks` -> list all added books

## Sample POST Request
URL:
`POST http://localhost:8080/addbook`

Body:
```json
{
  "id": 101,
  "title": "Microservices Patterns",
  "author": "Chris Richardson",
  "price": 799.0
}
```

## Build Jar
```bash
mvn clean package
```
