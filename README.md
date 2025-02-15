# Central library spring boot

The system manages libraries, books, publishers, and authors.

---

## Overview

The system provides the following core functionalities:

- **Library Management:**  
  Maintain libraries with names, addresses, and inventories of books.  

- **Book Catalog:**  
  - Each book is uniquely identified by its ISBN.
  - Books have titles, authors, publishers, publication dates, genres, etc.
  - On adding a new book, the system verifies that the ISBN is unique and that the specified author has an existing contract with the publisher.

- **Publisher Management:**  
  Tracks publisher details including name, address, published books, and the authors they contract with.

- **Author Management:**  
  Stores author details (first name, last name, e-mail) and manages their contracts with various publishers.

---

## Implemented Exercises

### Exercise 1 – Project Setup & UML Modeling
- **Environment Setup:**  
  Configured IntelliJ IDEA, JDK 8+, Maven, and Git.
- **Project Initialization:**  
  Created a Maven project using [Spring Boot Initializr](https://start.spring.io/).
- **UML Class Diagram:**  
  Designed a UML class diagram to model the data structure of libraries, books, publishers, and authors.

---

### Exercise 2 – Domain Model & Data Access Layer
- **ORM Implementation:**  
  Implemented the object-relational mapping (ORM) using JPA.
- **Entity Creation:**  
  Created Java classes for Library, Book, Publisher, and Author with appropriate attributes, getters/setters (using Lombok), and relationships (1:N, N:M, 1:1).
- **Data Integrity:**  
  Ensured uniqueness of ISBNs and validated contracts between authors and publishers using Bean Validation (e.g., `@NotNull`).
- **DAO/Repository Layer:**  
  Developed a CRUD repository layer for data operations.

---

### Exercise 3 – Service Layer & System Operations
- **Business Logic:**  
  Implemented core business services:
  - **Author Contracting:** Allowing an author to sign a contract with a publisher.
  - **Book Publishing:** Enabling a publisher to release a new book.
  - **Library Book Addition:** Managing the process of adding a book to a library.
- **UML Sequence Diagrams:**  
  Documented the system operations with UML sequence diagrams.
- **Service Implementation:**  
  Built the service layer with interfaces and implementations, including comprehensive logging (error, warning, debug, info, trace).
- **Testing:**  
  Tested service methods using JUnit.
---

## Technologies Used

- **Java 8+**
- **Spring Boot**
- **Maven**
- **JPA / Hibernate**
- **H2** 
- **IntelliJ IDEA**

---

