# 🏦 NovaBank

> A modern full-stack banking management system built with Angular 21, Spring Boot, and MySQL, enabling users to manage multiple bank accounts, perform secure banking transactions, track financial analytics, and providing a foundation for AI-powered natural-language banking interactions.

# 📖 Overview

**NovaBank** is a full-stack banking management system that simulates a personal banking portal where users can manage multiple bank accounts across different banks from a single application. Each account maintains its own balance, transaction history, and account details, providing a realistic representation of day-to-day banking operations.

The application supports core banking workflows such as account creation, deposits, withdrawals, fund transfers, transaction tracking, and analytics dashboards. It follows a layered architecture using Angular 21, Spring Boot, and MySQL, with RESTful APIs connecting the frontend and backend through DTO-based communication.

Beyond traditional CRUD operations, NovaBank is designed with extensibility in mind. The project includes an AI-assisted banking module that aims to interpret natural-language banking requests (such as *"Transfer ₹2,000 from my HDFC account to my SBI account"*) and convert them into validated backend operations while ensuring that all business rules are enforced by the server.

Built as a learning-focused project, NovaBank emphasizes clean architecture, scalable backend design, responsive user interfaces, and real-world banking workflows, making it a practical demonstration of modern full-stack software engineering.

---

# ✨ Features

## 👤 Account Management

* Create bank accounts
* Update account details
* Delete accounts
* View all accounts
* Multiple account support
* Automatic account number generation

---

## 💰 Banking Operations

* Deposit money
* Withdraw money
* Transfer funds
* Transaction history
* Balance validation
* Insufficient balance protection
* Invalid transaction handling

---

## 📊 Dashboard & Analytics

* Total account balance
* Monthly deposits
* Monthly withdrawals
* Transaction summaries
* Recent transaction history
* Banking insights dashboard

---

## 🤖 AI Banking Assistant

Supports natural language banking commands such as:

> "Deposit ₹5000 into my HDFC account"

> "Transfer ₹1000 from SBI to ICICI"

The assistant interprets user requests and converts them into validated backend banking operations.

---

# 🛠 Tech Stack

### Frontend

* Angular 21
* TypeScript
* HTML5
* CSS3
* RxJS

### Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Maven

### Database

* MySQL

### Development Tools

* Git
* GitHub
* IntelliJ IDEA
* VS Code
* Postman

---

# 🏗 Project Architecture

                Angular 21 Frontend
                       │
              HTTP REST API Calls
                       │
             Spring Boot Backend
                       │
         Service Layer / Business Logic
                       │
            Spring Data JPA Repository
                       │
                   MySQL Database
---

# 📂 Project Structure

```text
NovaBank
│
├── Frontend
│   ├── src
│   ├── app
│   ├── components
│   ├── services
│   └── assets
│
├── Backend
│   ├── controller
│   ├── service
│   ├── repository
│   ├── model
│   ├── dto
│   ├── enums
│   ├── exception
│   └── config
│
└── README.md
```

---

# 🔄 Core Banking Workflow

```text
User
   │
   ▼
Angular UI
   │
HTTP Request
   │
Spring Boot REST API
   │
Business Validation
   │
Database Update
   │
Response
   │
Angular UI Refresh
```

---

# 📦 REST API Modules

### Account APIs

* Create Account
* Update Account
* Delete Account
* Fetch All Accounts

### Transaction APIs

* Deposit
* Withdraw
* Transfer
* Transaction History

### Analytics APIs

* Dashboard Summary
* Monthly Statistics
* Recent Transactions

---

# ⚙️ Installation

## Clone Repository

```bash
git clone https://github.com/ashish-sonagara/NovaBank.git
```

---

## Backend

```bash
cd Backend
```

Configure MySQL in:

```properties
application.properties
```

Run:

```bash
mvn spring-boot:run
```

---

## Frontend

```bash
cd Frontend
npm install
ng serve
```

Application:

```
http://localhost:4200
```

---

# 🗄 Database

The application uses **MySQL** with Spring Data JPA.

Primary entities include:

* BankAccount
* Transaction

---

# 🚀 Highlights

* Full-stack architecture
* RESTful API communication
* Layered backend design
* Exception handling
* Input validation
* Responsive Angular UI
* Clean separation of concerns
* Reusable components
* Modern Angular 21 implementation

---

# 🔮 Future Improvements

* JWT Authentication
* Role-Based Authorization
* Email Notifications
* Docker Deployment
* Redis Caching
* Unit & Integration Testing
* CI/CD Pipeline
* PDF Statement Generation
* Account Search & Filters
* Cloud Deployment (AWS / Azure)

---

# 👨‍💻 Author

**Ashish Sonagara**

* GitHub: https://github.com/ashish-sonagara
* LinkedIn: https://linkedin.com/in/ashish-sonagara

---

## ⭐ Support

If you found this project useful, consider giving it a ⭐ on GitHub.
