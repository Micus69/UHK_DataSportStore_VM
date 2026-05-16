---

# Project Description

This project represents a complete sports equipment rental management system developed as a university database application project.

The system allows customers to create reservations for sports equipment while employees manage rentals, returns, and equipment states through a desktop GUI application.

The application uses a MySQL relational database together with Java Swing GUI and JDBC repository architecture.

The project demonstrates:

- relational database design
- SQL procedures
- SQL triggers
- SQL functions
- database views
- role-based access
- transaction processing
- Java GUI development
- repository pattern architecture
- Docker containerization

---

# Main Features

## Customer Features

- View available sports equipment
- Create reservations
- Browse equipment inventory

## Employee Features

- Employee login authentication
- View active reservations
- Convert reservation into rental
- View active rentals
- Return rented equipment

## Administrator Features

- Manage equipment inventory
- Manage employees
- Manage equipment states
- View system statistics
- Manage business data

---

# Database Features

The database layer contains advanced SQL functionality directly implemented inside MySQL.

## Implemented Database Logic

### Triggers

Triggers automatically manage equipment states:

- reservation → equipment becomes reserved
- rental → equipment becomes rented
- return → equipment becomes available

### Procedures

Implemented procedures:

- `VytvorVypujckuZRezervace`
- `VratVypujcku`

### Functions

Implemented functions:

- `PocetDniVypujcky`
- `VypocetCenyPolozky`

### Views

Implemented database views:

- `Pohled_AktivniVypujcky`
- `Pohled_DostupneVybaveni`
- `Pohled_AktivniRezervace`

---

# Authentication and Roles

The system supports role-based authentication.

## Roles

### ADMIN

Administrator can:

- manage equipment
- manage employees
- manage statistics
- manage equipment states

### EMPLOYEE

Employee can:

- create rentals
- process returns
- manage active reservations
- view active rentals

---

# Default Test Accounts

## Administrator

```text
Login: admin
Password: admin123
```

## Employee

```text
Login: employee
Password: employee123
```

---

# Database Startup

After Docker is running, start the MySQL container:

```powershell
docker compose up -d
```

Verify running containers:

```powershell
docker ps
```

---

# Running the Application

Run the Java application from IntelliJ IDEA:

```text
src/main/java/Main.java
```

or run using Maven:

```powershell
mvn spring-boot:run
```

---

# Database Backup

Database backup can be created using:

```powershell
docker exec pujcovna-mysql mysqldump -u root -p pujcovna > backup.sql
```

---

# System Architecture

The application uses layered architecture:

```text
GUI Layer
↓
Repository Layer
↓
MySQL Database Layer
↓
Triggers / Procedures / Functions
```

---

# GUI Localization

The entire application GUI was localized into the Czech language including:

- forms
- tables
- dialogs
- system messages
- buttons
- administration panels
