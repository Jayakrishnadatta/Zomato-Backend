# Zomato-Backend
Backend service for a modern e-commerce application developed using Java, Spring Boot, Hibernate, Spring Data JPA, and MySQL. Implements authentication, authorization, product management, cart, wishlist, order management, and clean layered architecture for scalable development.


---

## 📌 Features

- 🔐 User Authentication & Authorization
- 👤 User Profile Management
- 📦 Product Management
- 🗂️ Category Management
- 🛒 Shopping Cart
- ❤️ Wishlist Management
- 📍 Address Management
- 📑 Order Management
- 🔍 Product Search & Filtering
- 🗄️ MySQL Database Integration
- ⚡ RESTful API Architecture
- 🔄 DTO Mapping
- 📝 Exception Handling
- ✅ Data Validation

---

## 🛠️ Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Validation
- Maven

### Database
- MySQL

### Development Tools
- IntelliJ IDEA
- Git & GitHub
- Postman

---

## 📂 Project Structure

```
src
├── main
│   ├── java
│   │   └── com.zentro
│   │       ├── config
│   │       ├── controller
│   │       ├── dto
│   │       ├── entity
│   │       ├── exception
│   │       ├── mapper
│   │       ├── repository
│   │       ├── service
│   │       └── util
│   └── resources
│       ├── application.properties
│       └── static
```

---

## ⚙️ Installation

### Clone Repository

```bash
git clone https://github.com/Jayakrishnadatta/Zomato-Backend.git
```

```bash
cd Zomato-Backend
```

### Configure Database

Create a MySQL database.

```sql
CREATE DATABASE zentro;
```

Update your `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/zentro
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ▶️ Run the Project

Using Maven

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

---

## 📡 API Modules

| Module | Description |
|---------|-------------|
| Authentication | User Registration & Login |
| Users | User Profile APIs |
| Products | CRUD Operations |
| Categories | Product Categories |
| Cart | Shopping Cart Management |
| Wishlist | Wishlist Operations |
| Orders | Place & Manage Orders |
| Addresses | Delivery Address Management |

---

## 🗄️ Database

- MySQL
- Hibernate ORM
- Spring Data JPA

---

## 🧪 API Testing

Use **Postman** or any REST client.

Example

```
GET /api/products
POST /api/auth/register
POST /api/auth/login
POST /api/cart/add
GET /api/orders
```

---

## 🚧 Future Improvements

- JWT Authentication
- Role-Based Authorization (Admin/User)
- Payment Gateway Integration
- Email Verification
- Product Reviews & Ratings
- Coupon System
- Inventory Management
- Docker Deployment
- AWS Deployment
- Redis Caching

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature-name
```

3. Commit your changes

```bash
git commit -m "Add new feature"
```

4. Push to GitHub

```bash
git push origin feature-name
```

5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Jaya Krishna Datta**

- GitHub: https://github.com/Jayakrishnadatta

---

⭐ If you like this project, consider giving it a star!
