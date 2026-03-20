# FSAD Skill-9 - Global Exception Handling using @ControllerAdvice
**Course:** 24SDCS02 / 24SDCS02E / 24SDCS02P / 24SDCS02L
**Topic:** Custom Exceptions + @ControllerAdvice + Structured JSON Error Responses

---

## Project Structure

```
spring-exception/
├── pom.xml
└── src/main/java/com/fsad/exception/
    ├── SpringExceptionApplication.java     <- Main class
    ├── entity/
    │   └── Student.java                    <- Student POJO
    ├── model/
    │   └── ErrorResponse.java              <- Task 6: structured JSON error
    ├── exception/
    │   ├── StudentNotFoundException.java   <- Task 1: custom exception
    │   └── InvalidInputException.java      <- Task 5: second custom exception
    ├── handler/
    │   └── GlobalExceptionHandler.java     <- Task 3 & 4 & 5: @ControllerAdvice
    └── controller/
        └── StudentController.java          <- Task 2: REST endpoints
```

---

## How to Run in STS

1. File -> Import -> Maven -> Existing Maven Projects -> select this folder
2. Right-click `SpringExceptionApplication.java` -> Run As -> Spring Boot App
3. App starts at `http://localhost:8080`

---

## Postman / Browser Test Cases (Task 7 & 8)

### HAPPY PATH - Valid student ID
```
GET http://localhost:8080/student/1
```
**Response 200 OK:**
```json
{
  "id": 1,
  "name": "Arjun Sharma",
  "department": "CSE",
  "year": 3
}
```

---

### ERROR - Student ID not found (Task 4)
```
GET http://localhost:8080/student/99
```
**Response 404 Not Found:**
```json
{
  "timestamp": "2024-11-15 10:30:45",
  "statusCode": 404,
  "error": "Not Found",
  "message": "Student with ID 99 not found. Please check the ID and try again.",
  "path": "/student/99"
}
```

---

### ERROR - Negative ID (Task 5 - InvalidInputException)
```
GET http://localhost:8080/student/-1
```
**Response 400 Bad Request:**
```json
{
  "timestamp": "2024-11-15 10:30:45",
  "statusCode": 400,
  "error": "Bad Request",
  "message": "Invalid value '-1' for field 'id'. Please provide a valid positive number.",
  "path": "/student/-1"
}
```

---

### ERROR - Text instead of number (Task 8 - Type Mismatch)
```
GET http://localhost:8080/student/abc
```
**Response 400 Bad Request:**
```json
{
  "timestamp": "2024-11-15 10:30:45",
  "statusCode": 400,
  "error": "Bad Request - Type Mismatch",
  "message": "Invalid value 'abc' for parameter 'id'. Expected type: int",
  "path": "/student/abc"
}
```

---

### All students (happy path)
```
GET http://localhost:8080/student/all
```

### Search by name (blank triggers InvalidInputException)
```
GET http://localhost:8080/student/search?name=Arjun
GET http://localhost:8080/student/search?name=
```

---

## Push to GitHub

```bash
cd spring-exception
git init
git add .
git commit -m "FSAD Skill-9: Global Exception Handling with @ControllerAdvice"
git remote add origin https://github.com/<your-username>/spring-exception.git
git branch -M main
git push -u origin main
```

---

## Viva Questions and Answers

**Q1. What is the purpose of @ControllerAdvice in Spring?**
@ControllerAdvice is a specialization of @Component that allows centralized exception handling across ALL controllers in the application. Instead of writing try-catch blocks in every controller, all exception handling logic is placed in one class, making the code cleaner and consistent.

**Q2. How does @ExceptionHandler work inside a global handler?**
@ExceptionHandler(SomeException.class) marks a method that should be invoked whenever a SomeException (or its subclass) is thrown anywhere in the application. Spring's DispatcherServlet intercepts the exception and routes it to the matching @ExceptionHandler method, which then returns a structured response.

**Q3. Why do applications use global exception handling?**
- Avoids repetitive try-catch blocks in every controller
- Returns consistent, structured error responses to clients
- Hides internal technical stack traces from end users
- Makes it easy to add/change error handling in one place
- Improves API usability and security

**Q4. Difference between custom exceptions and default exceptions?**
Default exceptions (like NullPointerException, IllegalArgumentException) are generic and carry no business context. Custom exceptions (like StudentNotFoundException) are domain-specific, carry meaningful messages and data, and allow the global handler to return precise HTTP status codes and user-friendly messages tailored to the situation.

**Q5. Can a single @ControllerAdvice class handle multiple exceptions?**
Yes. A single @ControllerAdvice class can contain as many @ExceptionHandler methods as needed, each handling a different exception type. Spring routes each thrown exception to the most specific matching handler. This project demonstrates handling StudentNotFoundException, InvalidInputException, MethodArgumentTypeMismatchException, and a generic Exception fallback all in one GlobalExceptionHandler class.
