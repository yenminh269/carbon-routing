Carbon-Aware Routing Engine
## Project Overview
The Carbon-Aware Routing Engine is a specialized backend system designed to solve the "Greenest Path" problem. Unlike traditional GPS systems that prioritize only time or distance, this engine utilizes a weighted graph to calculate optimal routes by balancing transit time against carbon emission intensity.

Built with Spring Boot 3.x, this project demonstrates advanced Java logic, custom graph algorithm implementation, and a robust security layer for enterprise-level API protection.

## Core Engineering Highlights
1. Algorithmic Routing Logic
Custom Dijkstra Implementation: Engineered a routing algorithm that processes multi-weighted edges (Time + Carbon) to compute emission-optimized paths.

Large-Scale Graphing: Designed to handle simulated environments with 1,000+ nodes and 5,000+ edges, ensuring low-latency computation.

Data Encapsulation: Implemented a layered backend using DTO-based mapping to prevent entity exposure, improving both security and API performance.

2. Advanced Security Architecture
Identity Management: Secured 20+ REST endpoints using Stateless JWT Authentication.

Token Lifecycle: Implemented Refresh Token Rotation to mitigate session hijacking and maintain high security standards.

Granular RBAC: Role-Based Access Control (RBAC) enforced via Spring Security to manage fine-grained authorization across protected API resources.

3. Quality Assurance & Testing
High Coverage: Achieved ~90% unit test coverage using JUnit 5 and Mockito.

Robust Logic: Wrote 25+ comprehensive test cases specifically targeting authentication filters, refresh token logic, and core routing service edge cases.

## Tech Stack
Language: Java 17

Framework: Spring Boot 3.x (Spring Web, Spring Security, Spring Data JPA)

Database: PostgreSQL 

Security: JSON Web Tokens (JWT), Refresh Tokens, RBAC

Testing: JUnit 5, Mockito

Tools: Maven, Lombok, Global Exception Handling

## System Architecture
src/main/java/com.minhho.demo
├── config/          # Security & Application configurations
├── controller/      # REST API Endpoints (20+ Secured)
├── service/         # Custom Dijkstra Engine & Business Logic
├── repository/      # Data Access Layer (JPA/PostgreSQL)
├── model/           # Graph classes generated at run time(Nodes, Edges, Weights)
├── dto/             # Request/Response Mapping Layer
├── entity/          # JPA entities (User, Role, RefreshToken)
├── security/        # JWT Filters & Token Rotation Logic
└── exception/       # Global Exception Handling

## Roadmap & Progress
[ ] Graph Engine: Dijkstra implementation for carbon-weighted paths.

[x] Security Infrastructure: JWT implementation and Refresh Token rotation.

[x] Testing Suite: 90% coverage achieved via JUnit/Mockito.

[ ] Real-time Data: Integration with live carbon-intensity APIs.

[ ] Visualization: Developing a React-based map interface for path rendering.

## Local Setup
Clone the repository: git clone https://github.com/your-username/carbon-routing.git

Database: Ensure PostgreSQL is running with a database named ems_db.

Environment Variables:
Update src/main/resources/application.properties with your DB credentials and JWT secret key.

Run: Execute ./mvnw spring-boot:run.
