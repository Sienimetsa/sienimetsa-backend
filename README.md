# Sienimetsa-backend
Backend project for the "Software Project 2" course application. This backend project manages mushroom and customer data. It functions as the backend for the application's frontend.
# Features

# Security Features
- Authentication system for customer and admin users with Password hashing 
- Generates a JWT token upon successful authentication
- Two Security Filter Chains for admin and customer for handiling endpoints
# Admin Features
- Customer data management 
- Mushroom data management

 (More feattures will be added later)
 
# Requirements
- Java 17
- PostgreSQL database
- Spring Boot
# Installation Instructions
1. Clone the repository:
git clone <repository-url>
2. Configure the database:
Set up a PostgreSQL database.
Update the application configuration files with your database details.
3. Start the application locally:
mvn spring-boot:run
# Technologies
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
