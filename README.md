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
## Screenshots

### Login Page

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/76feb524-e326-436e-899f-db4da19bc1f9" />


### Registration Page

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/493a4caf-863f-4076-a3ed-eff89bf5c7c4" />


### Dashboard

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/46e59775-bd1a-42c4-bcd1-204d157a748a" />


### Board Management

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/161d78ac-e851-4fc9-81cd-a13bf80aa0b9" />


### Task Management

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/c891fcd0-f9fc-44e3-8f12-c59438da75ba" />


### User Management

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/ceba7389-7858-4653-9f5f-ec479c43c380" />


### Real-Time Updates

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/4635b111-db47-472a-b042-c9dd44ea5109" />


---

## Live Demo

**Application URL:**
Deployment Link Here

**Status:** Active

---

## API Testing

The application APIs were tested using Postman.

### Tested Functionalities

* User Registration
* User Login
* JWT Authentication
* Board Management APIs
* Task Management APIs
* User Management APIs

---

## Developer

**Name:** Yadagini Charishma

**Role:** Full Stack Java Developer
