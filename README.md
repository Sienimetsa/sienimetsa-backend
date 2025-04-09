# Sienimetsa-backend
Backend project for the "Software Project 2" course application. This backend application manages the user data, including user findings. It also manages the mushroom data we're using in the frontend application. 

# Features
### Login
- `GET /login` - Shows the login page.
### Frontpage
- `GET /frontpage` - Shows the applications front-page. The page includes links to users, findings and mushrooms.
### Users
- `GET /allusers` - Shows all appusers and provides an option to add/edit/delete an user.
### Findings
- `GET /allfindings` - Shows all mushroom findings and who found them. Provides an option to add/edit/delete a finding.
### Mushrooms
- `GET /allmushrooms` - Shows all mushrooms in the database. Provides an option to add/edit/delete a mushroom.
  
# Security Features
- Authentication system for customer and admin users with Password hashing 
- Generates a JWT token upon successful authentication
- Two Security Filter Chains for admin and customer for handiling endpoints
  
# Admin Features
- User data management
- Users findings management
- Mushroom data management

# Requirements
- Java 17
- PostgreSQL database
- Spring Boot
  
# Installation Instructions
1. Clone the repository:
```bash
git clone https://github.com/Sienimetsa/sienimetsa-backend.git
cd sienimetsa-backend
```
2. Configure the database:
Set up a PostgreSQL database.
Update the application configuration files with your database details.
3. Start the application locally:
```bash
mvn spring-boot:run
  ```

# Technologies
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Thymeleaf
- AWS (Amazon Web Services)
