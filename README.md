# Library Management System

## Project Description

This project aims to develop a Library Management System API using Spring Boot. The system facilitates librarians in managing books, patrons, and borrowing records efficiently.

## Entities

- **Book**: Represents a book in the library. Includes attributes such as ID, title, author, publication year, ISBN, etc.
- **Patron**: Represents a library patron. Contains details like ID, name, contact information, etc.
- **Borrowing Record**: Tracks the association between books and patrons, including borrowing and return dates.

## Running the Application

- Clone the repository to your local machine.
```
git clone https://github.com/mado2002/Library-Management-System.git
```
- Set up the database according to the configured database properties or use your own user name and password.
```
schema name: library
user name: root
password: Mohamedw2002#
```
- Run the jar file in /target file
```
cd library-management/target
```
```
java -jar library-management-0.0.1-SNAPSHOT.jar
```
- Access the API endpoints using a REST client like Postman or cURL.

## API Endpoints

### Book Management Endpoints:
- **GET /api/books:** Retrieve a list of all books.
- **GET /api/books/{id}:** Retrieve details of a specific book by ID.
- **POST /api/books:** Add a new book to the library.
- **PUT /api/books/{id}:** Update an existing book's information.
- **DELETE /api/books/{id}:** Remove a book from the library.

### Patron Management Endpoints:
- **GET /api/patrons:** Retrieve a list of all patrons.
- **GET /api/patrons/{id}:** Retrieve details of a specific patron by ID.
- **POST /api/patrons:** Add a new patron to the system.
- **PUT /api/patrons/{id}:** Update an existing patron's information.
- **DELETE /api/patrons/{id}:** Remove a patron from the system.

### Borrowing Endpoints:
- **POST /api/borrow/{bookId}/patron/{patronId}:** Allow a patron to borrow a book.
- **PUT /api/return/{bookId}/patron/{patronId}:** Record the return of a borrowed book by a patron.

## Authentication
There are two types of users in this system:
- Normal User
```
username: user
password: 9999
```
- Admin
```
username: admin
password: 1234
```
**Note:** Before hitting any endpoint you must authorize your self first
<br>

1. **POST /api/auth/authentcation:** authorization endpoint by specifying username and password in Request Body.

2. The response will be a JWT Token
3. Put this token in the Authorization header of all coming requests
4. Authorization type is **Bearer**
5. The allowed Endpoints to each user:
- Normal User
```
GET /api/books
GET /api/books/{id}
POST /api/borrow/{bookId}/patron/{patronId}
PUT /api/return/{bookId}/patron/{patronId}
```

- Admin
```
All of the EndPoints Mentioned Above
```


