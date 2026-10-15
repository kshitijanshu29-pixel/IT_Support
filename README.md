# IT Support Helpdesk API

A RESTful IT Support Helpdesk application built using **Java, Spring Boot, Spring Security, JWT, Spring Data JPA, and MySQL**. The application provides secure authentication, role-based access control, user management, and ticket lifecycle management.

The backend is deployed on Railway and uses Flyway to manage database schema migrations.

## Features

- JWT-based authentication.
- Role-based access control for `CUSTOMER`, `SUPPORT`, and `ADMIN`.
- Customer registration and login.
- User management and administrative operations.
- Ticket creation and retrieval.
- Ticket assignment to support staff.
- Prevention of duplicate ticket assignment.
- Ticket status and priority management.
- DTO-based request and response handling.
- Input validation and exception handling.
- Database persistence using Spring Data JPA and Hibernate.
- Version-controlled database migrations using Flyway.
- Production deployment on Railway.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.1.1 | Backend framework |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| Spring Data JPA | Data persistence |
| Hibernate | Object-relational mapping |
| MySQL 8 | Relational database |
| Flyway | Database migrations |
| Maven | Build and dependency management |
| Railway | Deployment and hosting |
| Postman | API testing |

## Architecture

The application follows a layered architecture.

- **Controller Layer:** Handles HTTP requests and responses.
- **Service Layer:** Implements business logic and application rules.
- **Repository Layer:** Performs database operations through Spring Data JPA.
- **Entity Layer:** Represents persistent domain objects.
- **DTO Layer:** Defines request and response structures.
- **Security Layer:** Handles JWT authentication and role-based authorization.
- **Migration Layer:** Manages database schema changes using Flyway.

## User Roles

| Role | Responsibilities |
|---|---|
| `CUSTOMER` | Register, authenticate, create tickets, and access permitted ticket operations. |
| `SUPPORT` | Accept and handle support tickets according to authorization rules. |
| `ADMIN` | Perform authorized administrative operations, including ticket status and priority management. |

Public registration assigns the `CUSTOMER` role. Privileged staff accounts are created through administrative functionality.

## API Documentation

**Production Base URL**

`https://itsupport-production.up.railway.app`

All request and response bodies use JSON where applicable. Protected endpoints require a valid JWT and the appropriate permissions.

### 1. Authentication Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/register` | Register a new customer. |
| `POST` | `/auth/login` | Authenticate a user and obtain a JWT. |

### 2. User Management Endpoints

### 2. User Management Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/user` | Create a user. |
| `GET` | `/users/email?email={email}` | Retrieve a user by email address. |
| `GET` | `/users` | Retrieve users with pagination, sorting, and direction parameters. |
| `GET` | `/users/{id}` | Retrieve a user by ID. |
| `PUT` | `/user/{id}` | Update a user by ID. |
| `DELETE` | `/user/{id}` | Delete a user by ID. |
| `POST` | `/staffUser` | Create a staff user with the role and details specified by the request. |

#### Query Parameters

**`GET /users`**

| Parameter | Default | Description |
|---|---|---|
| `page` | `0` | Page number, starting at zero. |
| `size` | `10` | Number of users per page. |
| `sort` | `id` | Field used for sorting. |
| `direction` | `asc` | Sort direction, such as `asc` or `desc`. |

Example:

`GET /users?page=0&size=10&sort=id&direction=asc`

**`GET /users/email`**

Required query parameter: `email`

Example:

`GET /users/email?email=customer@example.com`

#### Response Status Codes

| Operation | Expected status |
|---|---|
| Create user | `201 Created` |
| Create staff user | `201 Created` |
| Retrieve user or list users | `200 OK` |
| Update user | `200 OK` |
| Delete user | `204 No Content` |

Protected user-management operations require the appropriate authentication and authorization configured by the application.

### 3. Ticket Management Endpoints

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/ticket` | Create a new ticket. |
| `GET` | `/ticket/getmytickets` | Retrieve the authenticated user's permitted tickets. |
| `GET` | `/ticket/{ticketNumber}` | Retrieve a ticket by its ticket number. |
| `POST` | `/ticket/acceptticket/{ticketNumber}` | Accept and assign a ticket to the authenticated support user. |
| `PUT` | `/ticket/updateticket/{ticketNumber}` | Update ticket details through the authorized administrative operation. |

**Note:** Confirm the exact HTTP method and path for ticket acceptance and ticket updates against the controller mappings before publishing this documentation.

## Authentication Flow

1. Register a customer using `/auth/register`.
2. Log in using `/auth/login`.
3. Obtain the JWT from the authentication response.
4. Include the token in subsequent authenticated requests.

Send the JWT in the HTTP `Authorization` header:

`Authorization: Bearer <your-jwt-token>`

Protected endpoints require a valid token and the appropriate role and permissions.

## Ticket Lifecycle

### Ticket Statuses

Tickets follow this lifecycle:

`OPEN → IN_PROGRESS → RESOLVED → CLOSED`

### Ticket Priorities

- `LOW`
- `MEDIUM`
- `HIGH`

### Ticket Assignment Rules

- Newly created tickets start with status `OPEN` and priority `LOW`.
- The authenticated customer is recorded as the ticket creator.
- New tickets are initially unassigned.
- Support staff can accept a ticket, recording themselves as the assigned support user.
- An already-assigned ticket cannot be accepted again.
- Authorized administrators can update ticket status and priority.

### Ticket Number Format

Each ticket receives a ticket number in the following format:

`TKT - XQWERT - <id>`

Example: `TKT - XQWERT - 1`

## Database Design

The application uses MySQL for persistent storage and Spring Data JPA for database access.

The primary domain entities include:

- **User:** Stores user information, credentials, and role.
- **Ticket:** Stores ticket details, status, priority, timestamps, creator, and assigned support user.

Tickets reference their creator and may reference an assigned support user. User summary DTOs are used in API responses to avoid exposing sensitive user entity fields.

### Database Migrations

Flyway manages database schema changes through versioned migration scripts:

- `V1__create_user_details_table.sql`
- `V2__create_ticket_details_table.sql`
- `V3__add_ticket_number.sql`

All three migrations were successfully applied in the production environment.

## Configuration

The application uses environment variables for database connectivity, JWT signing, and initial administrator configuration.

| Variable | Purpose |
|---|---|
| `DB_URL` | JDBC connection URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Secret used for JWT signing and validation |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile, such as `prod` |
| `ADMIN_EMAIL` | Email for the initial administrator account |
| `ADMIN_PASSWORD` | Password for the initial administrator account |

**Security:** Never commit production credentials, database passwords, JWT secrets, access tokens, or real `.env` files to version control. Configure production values through Railway's environment variable settings.

## Run Locally

### Prerequisites

- Java 21
- Maven, or the included Maven Wrapper
- MySQL 8
- Git

### 1. Clone the repository

```bash
git clone https://github.com/kshitijanshu29-pixel/IT_Support.git
cd IT_Support
```

### 2. Create the database

Create a MySQL database named `it_support_helpdesk`:

```sql
CREATE DATABASE it_support_helpdesk;
```

### 3. Configure environment variables

Configure the following values in your local environment:

```properties
DB_URL=jdbc:mysql://localhost:3306/it_support_helpdesk
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password
JWT_SECRET=your_base64_encoded_jwt_secret
SPRING_PROFILES_ACTIVE=dev
ADMIN_EMAIL=your_admin_email
ADMIN_PASSWORD=your_strong_admin_password
```

Use a strong, appropriately generated JWT secret compatible with the application's JWT implementation. Never commit real credentials.

### 4. Run the application

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

**Linux/macOS:**

```bash
./mvnw spring-boot:run
```

The application connects to the configured database, and Flyway applies pending migrations during startup.

The default local application URL is:

`http://localhost:8080`

## Testing

API endpoints have been tested using Postman, including authentication, ticket retrieval, and ticket management operations.

Important test scenarios include:

- Successful registration and login.
- Missing, invalid, or expired JWTs.
- Requests made without sufficient permissions.
- Ticket creation and retrieval.
- Attempts to accept an already-assigned ticket.
- Invalid ticket identifiers and request data.
- Authorized ticket status and priority updates.

Run automated tests using Maven:

**Windows:**

```powershell
.\mvnw.cmd test
```

**Linux/macOS:**

```bash
./mvnw test
```

## Deployment

The application is deployed on Railway and connected to a Railway MySQL service.

Production configuration includes:

- Environment-based database credentials.
- The active `prod` Spring profile.
- Flyway database migrations.
- Initial administrator bootstrapping.
- Public HTTPS access.

**Production URL:** https://itsupport-production.up.railway.app

## Future Improvements

- API documentation using OpenAPI/Swagger.
- Pagination and filtering for ticket listings.
- Ticket comments and conversation history.
- Email notifications for ticket updates.
- Centralized logging and monitoring.
- Expanded automated integration and security tests.

## Author

**Kshitij Routray**

GitHub: [kshitijanshu29-pixel](https://github.com/kshitijanshu29-pixel)

---

This project was developed to practice backend engineering concepts, REST API design, authentication and authorization, database persistence, testing, and production deployment using the Spring ecosystem.
