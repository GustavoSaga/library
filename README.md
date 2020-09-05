# Library

Small project designed to manage the books of a given library.

## Details

Web API developed using:

* [Java 11](https://www.java.com/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Hibernate](https://hibernate.org/)
* [Swagger](https://swagger.io/)
* [MySQL](https://www.mysql.com/)
* [Docker](https://www.docker.com/)

## Clone Project

    git clone https://github.com/GustavoSaga/library.git
    
## Run With Docker

    docker-compose up

## URL's

|  URL      |  Method       | Description   |
|-----------|---------------|---------------|
|`http://localhost:8080/books`                 |GET   |Return all books in the database            |
|`http://localhost:8080/books/{id}`            |GET   |Return the book with the given "id"         |
|`http://localhost:8080/books/find?name={name}`|GET   |Return the book with the given "name"       |
|`http://localhost:8080/books/author/{author}` |GET   |Return all the books of a given "author"    |
|`http://localhost:8080/books/genre/{genre}`   |GET   |Return all the books of a given "genre"     |
|`http://localhost:8080/books`                 |POST  |Create a new book with the given information|
|`http://localhost:8080/books`                 |PUT   |Update a book with the given information    |
|`http://localhost:8080/books/{id}`            |DELETE|Delete the book with the given "id"         |

## Swagger Test

`http://localhost:8080/swagger-ui.html#/`

## Entities

### Book

| Name      | Not Null      | Type          |
|-----------|---------------|---------------|
|**id**     |Yes|Integer|   
|**name**   |Yes|String |
|**author** |Yes|String |
|**genre**  |Yes|String |

## Endpoints

* List all books:
    * `GET - http://localhost:8080/books/`
    
* List book with a given "id":
    * `GET - http://localhost:8080/books/{id}`
    
* List book with a given "name":
    * `GET - http://localhost:8080/books/find?name={name}`
    
* List all books of a given "author":
    * `GET - http://localhost:8080/books/author/{author}`
    
* List all books of a given "genre":
    * `GET - http://localhost:8080/books/genre/{genre}`
    
* Delete a book from database using the book "id":
    * `DELETE - http://localhost:8080/books/{id}`
    
* Add a new book to the database:
    * `POST - http://localhost:8080/books/`
    * `JSON example`
        ```json
      {
          "name": "Book",
          "author": "Gustavo",
          "genre": "Horror"
      }
      
* Update a book in the database:
    * `PUT - http://localhost:8080/books/`
    * `JSON example`
        ```json
      {
          "id": 7,
          "name": "New Name",
          "author": "New author",
          "genre": "New genre"
      }