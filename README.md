# Library

Small project designed to manage the books of a given library.

## Details

Web API developed using:

* [Java 11](https://www.java.com/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Hibernate](https://hibernate.org/)
* [Swagger](https://swagger.io/)
* [MySQL](https://www.mysql.com/)

## URL's

|  URL      |  Method       | Description   |
|-----------|---------------|---------------|
| `http://localhost:8080/v1/protected/books/`                   | GET    | Return all books in the database             |
| `http://localhost:8080/v1/protected/books/{id}`               | GET    | Return the book with the given "id"          |
| `http://localhost:8080/v1/protected/books/name/{name}`        | GET    | Return the book with the given "name"        |
| `http://localhost:8080/v1/protected/books/author/{author}`    | GET    | Return all the books of a given "author"     |
| `http://localhost:8080/v1/protected/books/genre/{genre}`      | GET    | Return all the books of a given "genre"      |
| `http://localhost:8080/v1/admin/books/`                       | POST   | Create a new book with the given information |
| `http://localhost:8080/v1/admin/books/`                       | PUT    | Update a book with the given information     |
| `http://localhost:8080/v1/admin/books/{id}`                   | DELETE | Delete the book with the given "id"          |

## Swagger Test

`http://localhost:8080/swagger-ui.html#/`

## Entities

### User

| Name      | Not Null      | Type          |
|-----------|---------------|---------------|
|**username**|Yes|String |
|**password**|Yes|String |
|**name**    |Yes|String |
|**admin**   |Yes|Boolean|

### Book

| Name      | Not Null      | Type          |
|-----------|---------------|---------------|
|**name**    |Yes|String |
|**author**  |Yes|String |
|**genre**   |Yes|String |

## Endpoints

* List all books:
    * `GET - http://localhost:8080/v1/protected/books/`
    
* List book with a given "id":
    * `GET - http://localhost:8080/v1/protected/books/{id}`
    
* List book with a given "name":
    * `GET - http://localhost:8080/v1/protected/books/name/{id}`
    
* List all books of a given "author":
    * `GET - http://localhost:8080/v1/protected/books/author/{author}`
    
* List all books of a given "genre":
    * `GET - http://localhost:8080/v1/protected/books/genre/{genre}`
    
* Delete a book from database using the book "id":
    * `DELETE - http://localhost:8080/v1/admin/books/{id}`
    
* Add a new book to the database:
    * `POST - http://localhost:8080/v1/admin/books/`
    * `JSON example`
        ```json
      {
          "name": "Book",
          "author": "Gustavo",
          "genre": "Horror"
      }
      
* Update a book in the database:
    * `PUT - http://localhost:8080/v1/admin/books/`
    * `JSON example`
        ```json
      {
          "id": 7,
          "name": "New Name",
          "author": "New author",
          "genre": "New genre"
      }