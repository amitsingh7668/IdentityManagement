# 👤 User Directory App

A full-stack web application for managing users. It allows you to view, add, edit, and delete users. Built with:

- **Backend**: Java Spring Boot + JPA
- **Frontend**: React + Axios + Bootstrap
- **Database**: PostgreSQL

---

## 🚀 Features

- View all users in a table
- Add a new user via modal form
- Edit user roles
- Delete users
- Client-side and server-side validation
- Toast popups for success and error messages

---

## 🛠️ Tech Stack

### Backend:
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- REST API

### Frontend:
- React
- Axios
- Bootstrap 5
- React Toastify

---

## 🗃️ Database

The backend uses a single table:

```java
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private RolesEnum role;
}
