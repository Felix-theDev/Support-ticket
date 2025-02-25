# IT Support Ticket System - API Documentation

## Overview
The IT Support Ticket System API allows employees and IT support staff to manage support tickets.  
It includes **user authentication, ticket creation, status updates, comments, and audit logs**.

- **Employees** can create and view their own tickets.
- **IT Support** can view all tickets, update their status, and add comments.

---

## **🔐 Authentication**
All endpoints **except login and registration** require authentication via a **JWT Bearer Token**.

### **1️⃣ Register a New User**
**Endpoint:** `POST /api/auth/register`  
**Description:** Creates a new user (default role: `EMPLOYEE`)

**Request Body:**
```json
{
    "username": "john_doe",
    "password": "securepassword",
    "role": "EMPLOYEE"
}
```
**Response:**
```json
{
    "id": 1,
    "username": "john_doe",
    "role": "EMPLOYEE"
}
```

---

### **2️⃣ Login and Get Token**
**Endpoint:** `POST /api/auth/login`  
**Description:** Logs in a user and returns a JWT token for authentication.

**Request Body:**
```json
{
    "username": "john_doe",
    "password": "securepassword"
}
```
**Response:**
```json
{
    "token": "your_jwt_token_here"
}
```
**Use this token in all subsequent requests**:
```
Authorization: Bearer your_jwt_token_here
```

---

## **🎫 Ticket Management**
Tickets represent IT support issues reported by employees.

### **3️⃣ Create a New Ticket (Employees Only)**
**Endpoint:** `POST /api/tickets`  
**Authorization:** Bearer Token

**Request Body:**
```json
{
    "title": "Laptop won't start",
    "description": "My laptop won't turn on, even when plugged in.",
    "priority": "HIGH",
    "category": "HARDWARE"
}
```
**Response:**
```json
{
    "id": 1,
    "title": "Laptop won't start",
    "status": "OPEN",
    "priority": "HIGH",
    "category": "HARDWARE"
}
```

---

### **4️⃣ Get All Tickets (IT Support Only)**
**Endpoint:** `GET /api/tickets`  
**Authorization:** Bearer Token (Must be IT Support)

**Response:**
```json
[
    {
        "id": 1,
        "title": "Laptop won't start",
        "status": "OPEN",
        "priority": "HIGH",
        "category": "HARDWARE"
    },
    {
        "id": 2,
        "title": "WiFi Not Connecting",
        "status": "IN_PROGRESS",
        "priority": "MEDIUM",
        "category": "NETWORK"
    }
]
```

---

### **5️⃣ Get a Specific Ticket**
**Endpoint:** `GET /api/tickets/{id}`  
**Authorization:** Bearer Token
- Employees can only view their **own** tickets.
- IT Support can view **any** ticket.

**Response:**
```json
{
    "id": 1,
    "title": "Laptop won't start",
    "description": "My laptop won't turn on, even when plugged in.",
    "status": "OPEN",
    "priority": "HIGH",
    "category": "HARDWARE",
    "createdBy": "john_doe"
}
```

---

## **🛠️ IT Support Actions**
### **6️⃣ Update Ticket Status (IT Support Only)**
**Endpoint:** `PUT /api/tickets/{id}/status`  
**Authorization:** Bearer Token (Must be IT Support)

**Request Body:**
```json
{
    "status": "IN_PROGRESS"
}
```
**Response:**
```json
{
    "message": "Ticket status updated successfully"
}
```

---

### **7️⃣ Add a Comment to a Ticket (IT Support Only)**
**Endpoint:** `POST /api/tickets/{id}/comments`  
**Authorization:** Bearer Token (Must be IT Support)

**Request Body:**
```json
{
    "comment": "We are currently diagnosing the issue."
}
```
**Response:**
```json
{
    "message": "Comment added successfully"
}
```

---

## **📜 Audit Logs (IT Support Only)**
### **8️⃣ Get Audit Logs for a Ticket**
**Endpoint:** `GET /api/tickets/{id}/audit-log`  
**Authorization:** Bearer Token (Must be IT Support)

**Response:**
```json
[
    {
        "action": "STATUS_CHANGED",
        "performedBy": "admin",
        "timestamp": "2025-02-25T12:34:56",
        "details": "Ticket status changed to IN_PROGRESS"
    },
    {
        "action": "COMMENT_ADDED",
        "performedBy": "admin",
        "timestamp": "2025-02-25T12:40:10",
        "details": "We are currently diagnosing the issue."
    }
]
```

---

## **📌 Security & Roles**
| **User Role**   | **Create Ticket** | **View Own Ticket** | **View All Tickets** | **Update Status** | **Add Comment** | **View Audit Logs** |
|---------------|-----------------|-----------------|----------------|----------------|--------------|--------------|
| **EMPLOYEE**  | ✅ Yes           | ✅ Yes           | ❌ No           | ❌ No          | ❌ No        | ❌ No        |
| **IT SUPPORT** | ❌ No            | ✅ Yes           | ✅ Yes          | ✅ Yes         | ✅ Yes       | ✅ Yes       |

---

## **🚀 Running the API**
1️⃣ **Start the Spring Boot backend:**
```bash
mvn spring-boot:run
```
2️⃣ **Test API using Postman or Swagger:**  
👉 **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## **🚀 Conclusion**
- ✅ **Users can register & log in (JWT authentication).**
- ✅ **Employees can create tickets.**
- ✅ **IT Support can update tickets, add comments, and view logs.**
- ✅ **Swagger provides interactive API documentation.**


