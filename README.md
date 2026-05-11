---

## Project Startup and Docker Environment

### Overview

This project uses Docker to run the MySQL database server in an isolated container environment.  
Using Docker ensures that the application can run on any machine without requiring a manual MySQL installation or additional configuration.

The Java Spring Boot application communicates with the MySQL database through a standard JDBC connection.

---

## Project Startup

### 1. Start Docker Desktop

Before running the project, make sure Docker Desktop is installed and running on the system.

Docker Desktop provides the container environment required for the MySQL server.

### Docker Installation

If Docker Desktop is not installed on the system, it can be installed directly from Windows PowerShell.

Open PowerShell as Administrator and run:

```powershell
winget install -e --id Docker.DockerDesktop
```

### 2. Open Terminal in the Project Root Directory

Navigate to the project root directory:

<li>UHK_DataSportStore_VM</li>

### 3.Start the Database Server

Run the following command:

```powershell
docker compose up -d
```
## Technologies Used

<ul>
  <li>Java</li>
  <li>Spring Boot</li>
  <li>MySQL 8.0</li>
  <li>Docker + Docker Compose</li>
  <li>Maven</li>
  <li>IntelliJ IDEA</li>
  <li>Git / GitHub</li>
</ul>

## Project Structure

```
UHK_DataSportStore_VM/
├── database/
│
├── src/
│   ├── main/
│   └── test/
├── docker-compose.yml
├── pom.xml
└── README.md