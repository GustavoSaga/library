package br.com.library.endpoint;

import br.com.library.error.ResourceNotFoundException;
import br.com.library.model.Book;
import br.com.library.repository.BookRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v1")
@Api("API REST Library")
public class BookEndpoint {
    private final BookRepository bookDAO;

    @Autowired
    public BookEndpoint(BookRepository bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping(path = "protected/books")
    @ApiOperation(value = "Return a list with all books", response = Book[].class, produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    public ResponseEntity<?> listBooks (Pageable pageable) {
        return new ResponseEntity<>(bookDAO.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Return book by given id", response = Book.class)
    @GetMapping(path = "protected/books/{id}")
    public ResponseEntity<?> getBookById (@PathVariable("id") Long id) {
        verifyifbookexistsbyId(id);
        Optional<Book> book = bookDAO.findById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @ApiOperation(value = "Return book by given name", response = Book.class)
    @GetMapping(path = "protected/books/name/{name}")
    public ResponseEntity<?> getBookByName (@PathVariable("name") String name) {
        return new ResponseEntity<>(bookDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    @ApiOperation(value = "Return all books by given author", response = Book.class)
    @GetMapping(path = "protected/books/author/{author}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    public ResponseEntity<?> listBooksByAuthor (@PathVariable("author") String author, Pageable pageable) {
        return new ResponseEntity<>(bookDAO.findByAuthorIgnoreCaseContaining(author, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Return all books of a given genre", response = Book.class)
    @GetMapping(path = "protected/books/genre/{genre}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")
    })
    public ResponseEntity<?> listBooksByGenre (@PathVariable("genre") String genre, Pageable pageable) {
        return new ResponseEntity<>(bookDAO.findByGenreIgnoreCaseContaining(genre, pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Save given book", response = Book.class, produces="application/json", consumes="application/json")
    @PostMapping(path = "admin/books")
    public ResponseEntity<?> save (@Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookDAO.save(book), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update given book")
    @PutMapping(path = "admin/books")
    public ResponseEntity<?> updateById (@RequestBody Book book) {
        verifyifbookexistsbyId(book.getId());
        bookDAO.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete given book")
    @DeleteMapping(path = "admin/books/{id}")
    public ResponseEntity<?> deleteBookId (@PathVariable Long id) {
        verifyifbookexistsbyId(id);
        bookDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyifbookexistsbyId(Long id) {
        if (bookDAO.findById(id).isEmpty())
            throw new ResourceNotFoundException("Book not found for ID: "+id);
    }
//    private void verifyifbookexistsbyName(String name) {
//        if (!bookDAO.findByNameIgnoreCaseContaining(name).isEmpty())
//            throw new ResourceNotFoundException("Book not found");
//    }
}
