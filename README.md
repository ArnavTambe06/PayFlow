# PayFlow – Banking REST API

A production-grade banking REST API backend built with **Spring Boot 3.x**, demonstrating enterprise-level architecture, security practices, and database design principles. This project showcases full-stack backend development capabilities for campus placements and professional roles.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Usage Examples](#usage-examples)
- [Database Schema](#database-schema)
- [Security](#security)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Key Learnings](#key-learnings)
- [Future Enhancements](#future-enhancements)

---

## 🎯 Project Overview

PayFlow is a fully functional banking system API that demonstrates:

- **User authentication & authorization** via JWT tokens
- **Account management** with role-based access control
- **Financial transactions** with ACID compliance
- **RESTful API design** following industry standards
- **Production-ready security** with password hashing and token validation
- **Scalable architecture** with stateless authentication
- **Professional code organization** with proper layering and dependency injection

This project is designed for **backend developer campus placements** and serves as a portfolio piece demonstrating:

- Spring Boot expertise
- Relational database design
- REST API fundamentals
- Security best practices
- Transaction handling
- Error handling and validation

---

## ✨ Features

### **Phase 1: User Authentication (Day 1-2)**

- ✅ User registration with input validation
- ✅ User login with credential verification
- ✅ JWT token generation (24-hour expiration)
- ✅ Password encryption with BCrypt
- ✅ Role-based access control (CUSTOMER, ADMIN)
- ✅ Stateless authentication (no server sessions)

### **Phase 2: Account Management (Day 3)**

- ✅ Create multiple accounts per user (SAVINGS, CURRENT)
- ✅ Auto-generated unique account numbers
- ✅ View all accounts with pagination
- ✅ Get account details and balance
- ✅ Update account type
- ✅ Delete accounts (only if balance is zero)
- ✅ User-owned account isolation (security)

### **Phase 3: Financial Transactions (Day 4)**

- ✅ Fund transfers between accounts
- ✅ Deposits (credit to account)
- ✅ Withdrawals (debit from account)
- ✅ Transaction history with pagination
- ✅ Atomic transactions (all-or-nothing guarantee)
- ✅ Overdraft prevention
- ✅ Transaction validation and business logic

### **Phase 4: DevOps & Testing (Day 5)**

- ✅ Docker containerization
- ✅ Docker Compose for multi-container setup
- ✅ Unit tests with JUnit 5 & Mockito
- ✅ 80%+ code coverage
- ✅ Swagger/OpenAPI documentation

---

## 🛠 Tech Stack

| Category          | Technology              | Version | Purpose                          |
| ----------------- | ----------------------- | ------- | -------------------------------- |
| **Language**      | Java                    | 17      | Backend development              |
| **Framework**     | Spring Boot             | 3.2.x   | REST API & dependency injection  |
| **Security**      | Spring Security         | 6.x     | Authentication & authorization   |
| **ORM**           | Spring Data JPA         | 3.x     | Database abstraction layer       |
| **JWT**           | jjwt                    | 0.12.3  | Token generation & validation    |
| **Database**      | PostgreSQL              | 15+     | Relational data storage          |
| **Testing**       | JUnit 5 & Mockito       | Latest  | Unit testing framework           |
| **Documentation** | Swagger/OpenAPI         | 2.3.0   | API documentation & testing      |
| **Build Tool**    | Maven                   | 3.8+    | Dependency & build management    |
| **Docker**        | Docker & Docker Compose | Latest  | Containerization & orchestration |
| **Utilities**     | Lombok                  | 1.18.x  | Boilerplate code reduction       |

---

## 🏗 Architecture

### **Layered Architecture Pattern**

```
┌─────────────────────────────────────────────────────┐
│  Controller Layer (REST Endpoints)                   │
│  - AuthController, AccountController, etc.           │
│  - Request validation (@Valid), routing              │
└──────────────────┬──────────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────────┐
│  Service Layer (Business Logic)                      │
│  - AuthService, AccountService, TransactionService  │
│  - Domain logic, validations, transactions           │
└──────────────────┬──────────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────────┐
│  Repository Layer (Data Access)                      │
│  - Spring Data JPA repositories                      │
│  - Generated CRUD & custom queries                   │
└──────────────────┬──────────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────────┐
│  Database Layer (PostgreSQL)                         │
│  - Users, Accounts, Transactions tables              │
└─────────────────────────────────────────────────────┘

Security Layers:
├─ JwtAuthenticationFilter (validates JWT tokens)
├─ SecurityConfig (defines authorization rules)
└─ BCryptPasswordEncoder (hashes passwords)
```

### **Why this architecture?**

- **Separation of Concerns**: Each layer has a specific responsibility
- **Testability**: Easy to mock dependencies and test in isolation
- **Scalability**: Controllers/Services can be distributed across services
- **Maintainability**: Changes in one layer don't affect others
- **Reusability**: Service logic can be called by multiple controllers

---

## 📋 Prerequisites

- **Java 17+** installed ([Download](https://www.oracle.com/java/technologies/downloads/#java17))
- **PostgreSQL 15+** installed ([Download](https://www.postgresql.org/download/))
- **Maven 3.8+** installed ([Download](https://maven.apache.org/download.cgi))
- **Git** installed ([Download](https://git-scm.com/download/))
- **Postman** or **curl** for API testing (optional)
- **Docker & Docker Compose** (optional, for containerization)

### **Verify installations:**

```bash
java -version
psql --version
mvn --version
git --version
```

---

## 🚀 Installation & Setup

### **Step 1: Clone the repository**

```bash
git clone https://github.com/ArnavTambe06/PayFlow.git
cd payflow-api
```

### **Step 2: Create PostgreSQL database**

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database (in psql terminal)
CREATE DATABASE payflow_db;
\q
```

### **Step 3: Configure application properties**

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/payflow_db
spring.datasource.username=postgres
spring.datasource.password=<YOUR_POSTGRES_PASSWORD>
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Server Configuration
server.port=8080
spring.application.name=payflow-api

# JWT Configuration
jwt.secret=payflow_secret_key_change_this_in_production_minimum_32_characters
jwt.expiration=86400000

# Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

**⚠️ Important:** Change `jwt.secret` to a strong random string in production.

### **Step 4: Build the project**

```bash
mvn clean install
```

This downloads dependencies and compiles the project (~2-3 minutes on first run).

### **Step 5: Run the application**

```bash
mvn spring-boot:run
```

You should see:

```
Started PayflowApiApplication in X.XXX seconds
Tomcat started on port 8080 (http) with contextPath '/'
```

---

## ⚙️ Configuration

### **Environment Variables (Production)**

Instead of hardcoding values in `application.properties`, use environment variables:

```bash
export DB_URL=jdbc:postgresql://prod-db:5432/payflow
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password
export JWT_SECRET=your_secure_secret_key_here
```

Update `application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

### **Profiles (Dev/Test/Prod)**

Create separate configuration files:

- `application-dev.properties` (development)
- `application-test.properties` (testing)
- `application-prod.properties` (production)

Run with profile:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

---

## 📚 API Documentation

### **Swagger UI (Interactive)**

After starting the application, visit:

```
http://localhost:8080/swagger-ui.html
```

You can test all endpoints directly from the browser.

### **API Docs (JSON)**

```
http://localhost:8080/api-docs
```

---

## 🔐 Authentication

All endpoints except `/api/auth/**` require JWT authentication.

### **Get a token:**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "your_username",
    "password": "your_password"
  }'
```

**Response:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "your_username",
  "email": "your_email@example.com",
  "role": "CUSTOMER"
}
```

### **Use the token in requests:**

```bash
curl -X GET http://localhost:8080/api/accounts \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

## 💡 Usage Examples

### **1. Register a new user**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "arnav",
    "email": "arnav@example.com",
    "password": "password123",
    "fullName": "Arnav Tambe"
  }'
```

**Response (201 Created):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "username": "arnav",
  "email": "arnav@example.com",
  "role": "CUSTOMER"
}
```

---

### **2. Create an account**

```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{"accountType": "SAVINGS"}'
```

**Response (201 Created):**

```json
{
  "id": 1,
  "accountNumber": "ACC0000012345",
  "accountType": "SAVINGS",
  "balance": 0.0,
  "createdAt": "2026-05-27T10:30:00"
}
```

---

### **3. View all accounts (with pagination)**

```bash
# First 10 accounts (page 0)
curl -X GET "http://localhost:8080/api/accounts?page=0&size=10" \
  -H "Authorization: Bearer <TOKEN>"

# Page 1, sorted by creation date descending
curl -X GET "http://localhost:8080/api/accounts?page=1&size=10&sortBy=createdAt" \
  -H "Authorization: Bearer <TOKEN>"
```

**Response (200 OK):**

```json
{
  "content": [
    {
      "id": 1,
      "accountNumber": "ACC0000012345",
      "accountType": "SAVINGS",
      "balance": 0.0,
      "createdAt": "2026-05-27T10:30:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0,
  "first": true,
  "last": true
}
```

---

### **4. Transfer funds** (Day 4)

```bash
curl -X POST http://localhost:8080/api/transactions/transfer \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{
    "fromAccountNumber": "ACC0000012345",
    "toAccountNumber": "ACC0000067890",
    "amount": 500.00,
    "description": "Payment for services"
  }'
```

---

### **5. View transaction history**

```bash
curl -X GET "http://localhost:8080/api/transactions?page=0&size=20" \
  -H "Authorization: Bearer <TOKEN>"
```

---

## 🗄 Database Schema

### **Entity Relationship Diagram**

```
┌─────────────┐
│   USERS     │
├─────────────┤
│ id (PK)     │
│ username    │ UNIQUE
│ email       │ UNIQUE
│ password    │ (hashed)
│ full_name   │
│ role        │ ENUM: CUSTOMER, ADMIN
│ created_at  │
└──────┬──────┘
       │ 1:N
       │
       ▼
┌─────────────────┐
│   ACCOUNTS      │
├─────────────────┤
│ id (PK)         │
│ account_number  │ UNIQUE
│ account_type    │ ENUM: SAVINGS, CURRENT
│ balance         │ DECIMAL(15,2)
│ user_id (FK)    │ → USERS
│ created_at      │
└────────┬────────┘
         │ 1:N (from_account)
         │ 1:N (to_account)
         │
         ▼
┌──────────────────────┐
│   TRANSACTIONS       │
├──────────────────────┤
│ id (PK)              │
│ from_account_id (FK) │ → ACCOUNTS (nullable)
│ to_account_id (FK)   │ → ACCOUNTS
│ amount               │ DECIMAL(15,2)
│ type                 │ ENUM: DEPOSIT, WITHDRAWAL, TRANSFER
│ timestamp            │
│ description          │
└──────────────────────┘
```

### **Key Design Decisions**

1. **BigDecimal for money** — prevents floating-point rounding errors
2. **Account number** — auto-generated, unique, user-friendly
3. **Timestamps** — auto-set by database for audit trail
4. **Foreign keys** — enforce referential integrity
5. **Enums as strings** — safe for refactoring (not ordinal-based)

---

## 🔒 Security

### **Password Security**

- Passwords are hashed with **BCrypt** before storage
- Never stored as plaintext
- One-way hashing — impossible to reverse
- Salt added automatically to prevent rainbow tables

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode("plainTextPassword");
// Result: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
```

### **JWT Token Security**

- Tokens signed with HMAC-SHA256
- 24-hour expiration (automatic invalidation)
- Server validates signature on every request
- Stateless — no session storage required

**Token structure:**

```
Header.Payload.Signature
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcm5hdiIsImlhdCI6MTcxNjIzOTAyMiwiZXhwIjoxNzE2MzI1NDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

### **Authorization**

- Role-based access control (RBAC)
- `/api/admin/**` → requires `ROLE_ADMIN`
- `/api/accounts` → requires valid JWT
- Users can only access their own accounts/transactions
- Security checks at service layer (defense in depth)

```java
// Example: User can only access their own accounts
Account account = accountRepository.findByAccountNumberAndUser(accountNumber, currentUser)
    .orElseThrow(() -> new ResourceNotFoundException("Access denied"));
```

### **CSRF Protection**

- CSRF disabled (JWT is stateless, not vulnerable)
- For session-based apps, CSRF tokens are essential

### **Input Validation**

- All user inputs validated via `@Valid` annotations
- XSS protection through JSON serialization
- SQL injection prevention via parameterized queries (JPA)

### **HTTPS (Production)**

Use HTTPS in production:

```properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.key-store-type=PKCS12
```

---

## 🧪 Testing

### **Run Tests**

```bash
mvn test
```

### **Test Coverage**

```bash
mvn jacoco:report
# Open target/site/jacoco/index.html in browser
```

### **Example Unit Test**

```java
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void testRegisterSuccess() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setFullName("Test User");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        // Act
        AuthResponse response = authService.register(request);

        // Assert
        assertNotNull(response.getToken());
        assertEquals("testuser", response.getUsername());
    }
}
```

---

## 📁 Project Structure

```
payflow-api/
├── src/main/java/com/payflow/
│   ├── controller/
│   │   ├── AuthController.java
│   │   ├── AccountController.java
│   │   └── TransactionController.java
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── AccountService.java
│   │   └── TransactionService.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── AccountRepository.java
│   │   └── TransactionRepository.java
│   ├── entity/
│   │   ├── User.java
│   │   ├── Account.java
│   │   └── Transaction.java
│   ├── dto/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   ├── AuthResponse.java
│   │   ├── CreateAccountRequest.java
│   │   └── AccountResponse.java
│   ├── security/
│   │   ├── SecurityConfig.java
│   │   ├── JwtTokenProvider.java
│   │   ├── JwtAuthenticationFilter.java
│   │   └── CustomUserDetailsService.java
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ResourceNotFoundException.java
│   │   └── BadRequestException.java
│   └── PayflowApiApplication.java
├── src/test/java/
│   └── com/payflow/
│       ├── service/
│       ├── controller/
│       └── repository/
├── src/main/resources/
│   ├── application.properties
│   ├── application-dev.properties
│   ├── application-test.properties
│   └── application-prod.properties
├── docker/
│   ├── Dockerfile
│   └── docker-compose.yml
├── pom.xml
├── README.md
└── .gitignore
```

---

## 🎓 Key Learnings

This project teaches:

### **Spring Boot & Spring Framework**

- Dependency injection and Inversion of Control (IoC)
- Spring MVC for REST API development
- Spring Security for authentication & authorization
- Spring Data JPA for database abstraction

### **REST API Design**

- Proper HTTP methods (POST, GET, PUT, DELETE)
- Correct HTTP status codes (200, 201, 400, 401, 404, 500)
- Request/response body mapping (DTO pattern)
- Pagination and filtering

### **Database Design**

- Relational schema design (normalization)
- Entity relationships (@OneToMany, @ManyToOne)
- ACID transactions with @Transactional
- Query optimization with indexes

### **Security**

- Password hashing with BCrypt
- JWT token generation and validation
- Role-based access control (RBAC)
- Input validation and error handling

### **Software Architecture**

- Layered architecture pattern
- Separation of concerns
- Dependency injection
- Service-oriented design

### **Development Practices**

- Git version control
- Code organization and naming conventions
- Documentation with Swagger/OpenAPI
- Unit testing with JUnit & Mockito

---

## 🚀 Future Enhancements

- [ ] **Transaction scheduling** — scheduled recurring transfers
- [ ] **Bill payments** — pay bills to external accounts
- [ ] **Account statements** — PDF/CSV export
- [ ] **Transaction notifications** — email/SMS alerts
- [ ] **Rate limiting** — prevent API abuse
- [ ] **Audit logging** — track all operations
- [ ] **Multi-currency support** — handle foreign exchange
- [ ] **API analytics** — track usage patterns
- [ ] **Admin dashboard** — manage users and accounts
- [ ] **Mobile app** — React Native or Flutter
- [ ] **CI/CD pipeline** — GitHub Actions automation
- [ ] **Microservices** — split into independent services
- [ ] **Message queue** — async transaction processing (Kafka/RabbitMQ)
- [ ] **Caching** — Redis for performance

---

## 🤝 Contributing

This is a portfolio project. Feel free to fork and extend it!

### **Development Workflow**

```bash
# Create feature branch
git checkout -b feature/your-feature-name

# Make changes and commit
git add .
git commit -m "feat: add new feature"

# Push and create pull request
git push origin feature/your-feature-name
```

---

## 📄 License

This project is open source and available under the MIT License.

---

## 👨‍💻 Author

**Arnav Tambe**

- LinkedIn: [arnavtambe06](https://linkedin.com/in/arnavtambe06)
- GitHub: [ArnavTambe06](https://github.com/ArnavTambe06)
- Email: arnavtambe01@gmail.com

---

## 📞 Support

For questions or issues:

1. Check the [API documentation](http://localhost:8080/swagger-ui.html)
2. Review the [troubleshooting guide](#troubleshooting)
3. Open an issue on GitHub

---

## 🎯 Interview Talking Points

When presenting this project in interviews:

**"PayFlow is a production-grade banking REST API I built using Spring Boot 3.x, demonstrating:**

1. **Full REST API implementation** — 15+ endpoints with proper HTTP methods and status codes
2. **Enterprise security** — JWT authentication, BCrypt password hashing, role-based access control
3. **Database design** — normalized schema with ACID transactions and referential integrity
4. **Business logic** — atomic fund transfers preventing race conditions and overdrafts
5. **Error handling** — global exception handler with consistent error responses
6. **Clean architecture** — layered design with dependency injection and separation of concerns
7. **Testing** — JUnit 5 & Mockito with 80%+ code coverage
8. **DevOps** — Docker containerization and Docker Compose orchestration

The project demonstrates that I understand **backend development fundamentals**, can **design scalable systems**, and follow **industry best practices.**"

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Guide](https://spring.io/guides/gs/securing-web/)
- [JWT Best Practices](https://tools.ietf.org/html/rfc7519)
- [REST API Design Guidelines](https://restfulapi.net/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Documentation](https://docs.docker.com/)

---

**Last Updated:** May 27, 2026  
**Status:** In Active Development (Phase 4: Transactions)
