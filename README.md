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

---

## 🛠️ CI/CD Pipeline with Jenkins (Docker-in-Docker Setup)

This project is equipped with a `Jenkinsfile` and a `Dockerfile.jenkins` to automate building, testing, packaging, and dockerizing the backend application.

### How it Works:
1. **`Dockerfile.jenkins`**: Builds a custom Jenkins image based on the official LTS release, installing `docker.io` under the `root` user so the Jenkins agent can execute `docker` CLI commands.
2. **`Jenkinsfile`**: Defines a declarative pipeline with the following stages:
   - **Clone Repository**: Cleans the workspace and checks out the source code.
   - **Build**: Compiles the Java Spring Boot source code.
   - **Run Tests**: Runs unit and integration tests.
   - **Package JAR**: Builds the executable production JAR file (skipping tests since they ran in the previous stage).
   - **Build Docker Image**: Builds the final backend production image (`carbon-routing-app`) using the project's root `Dockerfile`.

---

### Setup Instructions for Jenkins in Docker

Since Jenkins needs to build Docker images, you must run Jenkins in a container while giving it access to the host machine's Docker daemon (often referred to as a "sibling container" setup).

#### 1. Build the Custom Jenkins Image
From the root directory of the project, build the custom Jenkins image:
```bash
docker build -t custom-jenkins-agent -f Dockerfile.jenkins .
```

#### 2. Run the Jenkins Container
Launch the container, ensuring that the host's Docker socket is mounted inside the container. This allows the Jenkins container's Docker client to speak to your host's Docker engine:

```bash
docker run -d \
  --name jenkins-ci \
  -p 8081:8080 -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  -v /var/run/docker.sock:/var/run/docker.sock \
  custom-jenkins-agent
```

> [!NOTE]
> On host systems, the Jenkins user inside the container may need permissions to access `/var/run/docker.sock`. You can grant access by running:
> `docker exec -u 0 jenkins-ci chmod 666 /var/run/docker.sock`

#### 3. Configure Tools in Jenkins
Before running the pipeline, ensure the required build tools are registered in Jenkins:
1. Go to **Dashboard** -> **Manage Jenkins** -> **Tools**.
2. **JDK**:
   - Add a JDK installation.
   - Name it exactly: `JDK17` (matching `JDK17` defined in the `Jenkinsfile` tools block).
   - Configure it to install automatically or point it to your JDK path.
3. **Maven**:
   - Add a Maven installation.
   - Name it exactly: `Maven` (matching `Maven` defined in the `Jenkinsfile` tools block).
   - Set it to install automatically (e.g., version 3.9.x).

#### 4. Create the Pipeline Job
1. Go to **New Item** in Jenkins.
2. Select **Pipeline** and name it (e.g., `CARE-Pipeline`).
3. Under the **Pipeline** configuration section:
   - Select **Pipeline script from SCM** under **Definition**.
   - Choose **Git** as the SCM.
   - Enter your repository URL, set credentials if private, and specify your build branch (e.g., `*/main` or `*/master`).
   - Ensure the **Script Path** is set to `Jenkinsfile`.
4. Click **Save** and select **Build Now** to trigger the CI/CD pipeline!


