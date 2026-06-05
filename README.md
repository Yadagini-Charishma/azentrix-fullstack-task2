# Task Management System

---

## Project Information

**Project Name:** Task Management System

**Assignment:** Azentrix Full Stack Developer Assignment – Task 2

**Developer:** Yadagini Charishma

**Technology Stack:** Spring Boot, Spring MVC, Spring Data JPA, Hibernate, Spring Security, JWT Authentication, Thymeleaf, WebSocket, MySQL, Maven, Java 17

---

## Project Overview

The Task Management System is a collaborative web application developed using Spring Boot and MySQL.

The application allows users to create and manage boards, organize tasks, assign work, track task progress, and collaborate with team members. Authentication and authorization are implemented using Spring Security and JWT-based authentication. Real-time updates are supported through WebSocket integration.

---

## Features

### Authentication & Authorization

* User Registration
* User Login
* JWT Authentication
* Role-Based Access Control
* Secure Password Management

### Board Management

* Create Boards
* View Boards
* Update Boards
* Delete Boards
* Manage Board Information

### Task Management

* Create Tasks
* Edit Tasks
* Delete Tasks
* Assign Tasks to Users
* Update Task Status
* Track Task Progress

### User Management

* View Users
* Manage User Access
* Assign Users to Boards
* Admin Controls

### Real-Time Updates

* WebSocket Integration
* Live Board Updates
* Real-Time Task Status Changes

### Dashboard

* Board Overview
* Task Tracking
* User Activity Monitoring

---

## Project Structure

```text
src/main/java
│
├── controller
├── service
├── service/impl
├── repository
├── entity
├── dto
├── security
├── websocket
└── config

src/main/resources
│
├── templates
├── static
└── application.properties
```

---

## Database

**Database:** MySQL

Main tables include:

* users
* boards
* tasks
* board_members
* roles

---

## Technologies Used

### Backend

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* Spring Security
* JWT Authentication
* WebSocket

### Frontend

* Thymeleaf
* HTML
* CSS
* JavaScript

### Database

* MySQL

### Build Tool

* Maven

---

## Setup Instructions

### Clone Repository

```bash
git clone <repository-url>
```

### Configure Database

Update the database configuration in:

```properties
src/main/resources/application.properties
```

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

Application will start on:

```text
http://localhost:8080
```

---

## Developer

**Name:** Yadagini Charishma

**Role:** Full Stack Java Developer
