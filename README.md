# 🚀 IT Support Ticket System - Backend

This is the **backend service** for the IT Support Ticket System, built with **Spring Boot** and **Dockerized** for easy deployment.  
It handles user authentication, ticket management, and IT support operations.

## 📌 Features
✔ **JWT Authentication** (Login & Register)  
✔ **Role-Based Access Control** (`EMPLOYEE`, `IT_SUPPORT`)  
✔ **CRUD Operations for Tickets**  
✔ **IT Support Actions** (Status Updates, Comments, Audit Logs)  
✔ **Docker Support** (Containerized Deployment)  
✔ **JUnit & Mockito Tests**  

---

## 🚀 **Getting Started**
### **1️⃣ Prerequisites**
- **Java 17+**
- **Maven**
- **Docker (for containerized deployment)**

### **2️⃣ Clone the Repository**

git clone https://github.com/Felix-theDev/Support-ticket.git
cd support-ticket-backend
🛠️ Running the Backend

## **Option 1: Run with Maven (Local Development)**

mvn clean spring-boot:run
The backend will start on port 9090.

## **Option 2: Run with Docker**
Build the Docker Image

mvn clean package
docker build -t support-ticket-backend .
Run the Container

docker run -p 9090:9090 support-ticket-backend


🛠️ Running with Docker Compose
For easy management, use docker-compose:

docker-compose up --build
This will run both the backend and an in-memory H2 database.

## **📜 Database Configuration**
By default, the backend uses H2 Database for development.

To use Oracle, configure application.properties:

spring.datasource.url=jdbc:oracle:thin:@your-oracle-host:1521:yourdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

✅ Testing
Run unit tests using:


mvn test
📄 API Documentation
Full API documentation is available in 📜 docs/API_DOCUMENTATION.md.
