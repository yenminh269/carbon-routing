Service Management System (SMS)
A Scalable Full-Stack Platform for Service-Based Businesses
📌 Project Overview
The Service Management System (SMS) is a robust, multi-role platform designed to bridge the gap between service providers and clients. Built with a clean layered architecture, the system is industry-agnostic—capable of managing salons, clinics, or consultancy firms.

I am currently developing this as a full-stack application to solve real-world scheduling conflicts, automate client notifications, and provide a seamless administrative dashboard.

Core Features (MVP)
1) Security & Identity (Current Focus)
Stateless Authentication: Implemented via Spring Security and JWT (JSON Web Tokens).

Granular RBAC: Role-Based Access Control supporting four distinct tiers:

ADMIN: System oversight and global configuration.

MANAGER: Personnel management (CRUD), schedule auditing, and content editing.

EMPLOYEE: Personal schedule management and portfolio uploads.

CUSTOMER: Service discovery and appointment booking.

2) Core Functionality
Dynamic Scheduling: Conflict-aware booking engine to manage service provider availability.

Public Marketplace: Unauthenticated access for users to browse services, pricing, and visual portfolios.

Automated Alerts: Integration with SMS gateways (Twilio/AWS SNS) for booking confirmations and reminders.

Portfolio Management: Staff can upload and showcase completed work to a public gallery.

3) Tech Stack
Backend
Language: Java 17

Framework: Spring Boot 3.x (Spring Web, Spring Data JPA, Spring Security)

Database: PostgreSQL (Relational schema with 6+ core entities)

Tools: Maven, JWT, Lombok, Global Exception Handling

Frontend
Library: React.js

State Management: Context API / Redux

Styling: Tailwind CSS / Material UI

📂 Project Structure
Plaintext
com.yourname.sms
├── config/          # Security & Application configurations
├── controller/      # REST API Endpoints
├── service/         # Business Logic Layer
├── repository/      # Data Access Layer (JPA)
├── entity/          # JPA Entities (User, Role, Appointment, Service, Portfolio)
├── dto/             # Data Transfer Objects for optimized API responses
├── security/        # JWT Filters, Auth Providers, & Security Logic
└── exception/       # Custom Global Exception Handling
📈 Roadmap & Progress
[x] Database Schema Design: Relational mapping for multi-role support.

[x] Security Infrastructure: JWT implementation and Filter Chain configuration.

[/] User Authentication: Login/Signup flow (Current Task).

[ ] Service & Booking Logic: Implementation of scheduling algorithms.

[ ] Frontend Integration: Building the React dashboard.

🛠️ Local Setup
Clone the repository.

Ensure you have PostgreSQL running with a database named sms_db.

Update src/main/resources/application.properties with your credentials.

Run ./mvnw spring-boot:run.