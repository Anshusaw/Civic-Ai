# 🚀 Civic AI – Smart Civic Issue Management System

Civic AI is an AI-powered civic issue reporting and management platform that allows citizens to report public problems and enables authorities to manage, track, and resolve them efficiently.

---

## 🧠 Key Highlights

- 🤖 AI-powered issue analysis & response generation  
- 🔐 Secure authentication using Spring Security & JWT  
- 👥 Multi-role system (Citizen, Officer, Admin)  
- 📍 Complaint tracking & updates  
- 🗂 Department-based issue management  
- 🌐 Full-stack web application with server-side rendering  

---

## 🛠 Tech Stack

- **Backend:** Spring Boot  
- **Security:** Spring Security + JWT  
- **Database:** MySQL  
- **ORM:** Hibernate (JPA)  
- **Frontend:** Thymeleaf (HTML templates)  
- **Build Tool:** Maven  

---

## 🏗 Project Architecture


com.incapp
├── controller # REST & MVC controllers
├── entity # Database entities
├── repo # JPA repositories
├── service # Business logic + AI services
├── security # Security configuration
├── jwt # JWT handling


---

## 👨‍💻 Features

### 👤 Citizen
- Register & login
- Submit complaints
- Track complaint status
- View updates

### 🏢 Admin
- Manage departments
- Assign officers
- Monitor system

### 👮 Officer
- View assigned complaints
- Update complaint status

### 🤖 AI Module
- Analyze complaint descriptions
- Generate structured responses
- Improve categorization (future scope)

---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository
```bash
git clone https://github.com/Anshusaw/Civic-Ai.git
cd Civic-Ai
2️⃣ Configure Database

Create:

src/main/resources/application.properties

Add:

spring.datasource.url=jdbc:mysql://localhost:3306/civic_ai
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
3️⃣ Run Application
mvn spring-boot:run
🌐 Access Application
http://localhost:9090
🔐 Authentication
Spring Security enabled
JWT-based authentication implemented
Role-based access control
📂 Important Modules
AuthController → Authentication logic
CivicAiController → Core app flow
AIService → AI processing
SecurityConfig → Security rules
JwtUtil → Token handling
🔮 Future Enhancements
React frontend integration
Real-time notifications (WebSockets)
AI-based image recognition (complaints)
Deployment on cloud (AWS / Render)
💡 Why This Project Stands Out

Unlike basic CRUD apps, Civic AI integrates:

AI-based processing 🤖
Role-based secure system 🔐
Real-world civic problem solving 🌍
👨‍💻 Author

Anshu Saw

⭐ If you like this project

Give it a ⭐ on GitHub and feel free to connect!
