# ForumHub

ForumHub is a backend API project built with Java 17, Spring Boot 3, JWT Authentication, and MySQL. The system allows users to authenticate, create discussion topics, and interact with replies in a forum-style application.

## Features

- User registration and login with JWT authentication
- Token-based access control
- Create, list, update, and delete discussion topics
- Add and retrieve replies to topics
- Filter topics by author
- Database versioning using Flyway
- Password encryption with BCrypt

## Technologies Used

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- MySQL 8
- Flyway for database migrations
- Maven 4
- Postman (for testing)

## Authentication

All secured routes require a valid JWT token.  
Use the `/auth/login` endpoint with valid credentials to receive a token:

## POST /auth/login

`{
"email": "user@example.com",
"senha": "yourPassword"
}`
-
You will receive a response like:

`{
"token": "your.jwt.token.here"
}`

Include this token in the `Authorization` header for protected routes:
Authorization: Bearer your.jwt.token.here

## Project Structure 

`src
├── controller # API endpoints 
├── dto # Data Transfer Objects
├── model # Entities (Usuario, Topico, Resposta, etc.)
├── repository # JPA Repositories
├── security # JWT and authentication configuration
├── service # Business logic
└── config # Spring security configuration` 


## Running the Project


1. Clone the repository

2. Set up your MySQL database and configure `application.properties`:
   spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=validate
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   spring.flyway.enabled=true
   spring.flyway.locations=classpath:db/migration
   api.security.token.secret=supertokensecret
   api.security.token.expiration=3600000

3. Run the application:

4. 4. Use Postman or another REST client to test the API endpoints.

## API Endpoints (Summary)

| Method | Endpoint                    | Description               | Auth Required |
|--------|-----------------------------|---------------------------|----------------|
| POST   | /auth/login                 | Login and get JWT token   | No             |
| POST   | /usuarios                   | Register a new user       | No             |
| GET    | /topicos                    | List all topics           | Yes            |
| POST   | /topicos                    | Create a new topic        | Yes            |
| GET    | /topicos/{id}              | Get topic by ID           | Yes            |
| PUT    | /topicos/{id}              | Update a topic            | Yes            |
| DELETE | /topicos/{id}              | Delete a topic            | Yes            |
| POST   | /respostas                  | Add a reply to a topic    | Yes            |
| GET    | /respostas/topico/{id}     | List replies by topic     | Yes            |

## Author

Developed by César Santos  
