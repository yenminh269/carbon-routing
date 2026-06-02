# Carbon-Aware Routing Engine (CARE)

A comprehensive full-stack application designed to solve the "Greenest Path" problem. Unlike traditional route finders that prioritize only speed or distance, this engine calculates optimal routes by balancing transit time against carbon emission intensity across multi-weighted graphs.

The project features a high-performance **Spring Boot** backend paired with a modern, interactive **React + TypeScript + Tailwind CSS** frontend.

---

## 🚀 Key Features

*   **Custom Dijkstra Algorithm**: A multi-weighted routing processor that computes paths based on time, carbon emissions, or a balanced metric.
*   **Dynamic Graph Visualization**: An interactive React canvas to dynamically add nodes, draw edges, define distances/emission levels, select vehicle profiles, and trace the routing path visually.
*   **Granular Authentication & Security**: Stateless JWT Authentication, Refresh Token Rotation, and Role-Based Access Control (RBAC).
*   **Automated Database Seeding**: Automatic creation of user roles (`ADMIN`, `MANAGER`, `EMPLOYEE`) and a default admin user on startup.
*   **Docker Containerization**: Simple setup using Docker Compose to orchestrate the backend application and PostgreSQL database.
*   **CI/CD Integration**: Pre-configured Jenkinsfile and Dockerfile setups for automated builds, testing, and container packaging.

---

## 📂 Project Structure

```
.
├── front/                       # React Frontend Application (Vite + TS + Tailwind v4)
│   ├── src/
│   │   ├── components/          # Reusable UI components (Navbar, ProtectedRoute)
│   │   ├── pages/               # Application Pages (LandingPage, LoginRegister, Profile, RouteVisualization)
│   │   ├── services/            # Axios API config with automatic JWT refresh handling
│   │   ├── main.tsx
│   │   └── index.css
│   ├── package.json
│   └── vite.config.ts
│
├── src/main/java/com/minhho/demo/ # Spring Boot Backend Application
│   ├── algorithm/               # Core routing engine (DijkstraRouter, custom graph edges)
│   ├── config/                  # Initial data seeder (DataInitializer)
│   ├── controller/              # REST Controller endpoints (Auth, Routes)
│   ├── dto/                     # Data Transfer Objects for clean API request/response mapping
│   ├── entity/                  # JPA Database Entities (User, Role, RefreshToken)
│   ├── exception/               # Global exception handling and custom API errors
│   ├── model/                   # Routing models (Node, Edge, Path, Vehicle)
│   ├── repository/              # JPA repositories (UserRepository, RoleRepository, etc.)
│   ├── security/                # JWT services, Filter layers, and Security configurations
│   └── service/                 # Business logic, Routing algorithms, and User administration
│
├── Dockerfile                   # Multi-stage production build definition for the backend
├── Dockerfile.jenkins           # Custom Jenkins agent image setup
├── Jenkinsfile                  # CI/CD pipeline definition (Compile -> Test -> Package -> Dockerize)
├── docker-compose.yml           # Local database & app orchestration
├── pom.xml                      # Maven dependencies and plugin configs
└── .env                         # Backend environment variables
```

---

## 🛠️ Tech Stack

### Backend
*   **Java 17 / Spring Boot 4.x** (Web, Security, Data JPA)
*   **PostgreSQL** (Relational Database)
*   **Spring Security** with Stateless JWT Authentication & Refresh Token Rotation
*   **Springdoc OpenAPI (Swagger)** for API testing and documentation
*   **Lombok** & **Spring Dotenv** (Loads configurations dynamically from `.env`)

### Frontend
*   **React 19**
*   **TypeScript**
*   **Vite** (Build tool)
*   **Tailwind CSS v4** (Modern styling engine)
*   **Lucide React** (Icons)
*   **Axios** (With interceptors to automatically renew access tokens via refresh endpoint)

---

## ⚙️ Setup & Running Instructions

Follow these steps to set up and run the application locally.

### Prerequisites
*   **Java Development Kit (JDK) 17+** (Recommended: 17 or 21)
*   **Node.js** (v18+) and npm
*   **PostgreSQL** (running locally or via Docker)
*   *Optional*: **Docker** and **Docker Compose**

---

### Step 1: Environment Variables

At the root directory of the project, create or modify the `.env` file to configure your credentials:

```env
DB_USERNAME=postgres
DB_PASSWORD=your_postgres_password
DB_NAME=ems_db
JWT_SECRET=your_32_character_jwt_secret_key_here_for_security
JWT_ACCESS_TOKEN_EXPIRATION_MS=3600000
JWT_REFRESH_TOKEN_EXPIRATION_MS=86400000
```

---

### Step 2: Database Configuration

#### Option A: Using Docker (Recommended)
If you have Docker installed, simply start the PostgreSQL database container:
```bash
docker-compose up -d db
```
This starts PostgreSQL on port `5432` and creates the database `ems_db`.

#### Option B: Running Local PostgreSQL Instance
1. Start your local PostgreSQL server.
2. Create a database named `ems_db`.
3. Verify that your `.env` username and password match your local database settings.

---

### Step 3: Starting the Backend Server

1. From the project root, build and run the Spring Boot backend:
   ```bash
   ./mvnw spring-boot:run
   ```
2. The backend server will run on `http://localhost:8080`.
3. **Database Seeding**: On the initial startup, the backend automatically seeds:
   *   Roles: `ADMIN`, `MANAGER`, `EMPLOYEE`.
   *   Default administrator credentials:
       *   **Username**: `admin`
       *   **Password**: `admin123`

#### Interactive API Documentation
Once running, you can explore and execute the backend APIs using Swagger UI:
*   [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

### Step 4: Starting the Frontend Application

1. Open a new terminal and navigate to the `front` directory:
   ```bash
   cd front
   ```
2. Install npm dependencies:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
4. Open your browser and navigate to the frontend:
   *   [http://localhost:5173](http://localhost:5173)

---

## 🐳 Docker Compose Deployment (Full Stack Backend + DB)

To run both the PostgreSQL database and the Spring Boot backend inside Docker containers:

1. Build and run the entire ecosystem:
   ```bash
   docker-compose up --build
   ```
2. The database will start up and compile the Spring Boot application, serving it on `http://localhost:8080`.
3. Follow Step 4 to run the frontend locally and point it to the containerized backend.

---

## 🧪 Testing

To run the automated backend test suite (unit and integration tests with JUnit & Mockito):
```bash
./mvnw test
```

