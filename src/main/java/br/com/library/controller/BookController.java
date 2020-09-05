package br.com.library.controller;

import br.com.library.domain.Book;
import br.com.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("books")
@Slf4j
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Return all books paginated and sorted")
    public ResponseEntity<Page<Book>> listAllBooks(Pageable pageable){
        return ResponseEntity.ok(bookService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Return book with given ID")
    public ResponseEntity<Book> findBookById(@PathVariable int id){
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping(path = "/find")
    @Operation(summary = "Return book with given name (?name='book name')")
    public ResponseEntity<List<Book>> findBookByName(@RequestParam(value = "name") String name){
        return ResponseEntity.ok(bookService.findByName(name));
    }

    @GetMapping(path = "/author/{author}")
    @Operation(summary = "List all books of a given author")
    public ResponseEntity<List<Book>> findBookByAuthor(@PathVariable String author){
        return ResponseEntity.ok(bookService.findByAuthor(author));
    }

    @GetMapping(path = "/genre/{genre}")
    @Operation(summary = "List all books of a given genre")
    public ResponseEntity<List<Book>> findBookByGenre(@PathVariable String genre){
        return ResponseEntity.ok(bookService.findByGenre(genre));
    }

    @PostMapping
    @Operation(summary = "Save a book to the database")
    public ResponseEntity<Book> saveBook(@RequestBody @Valid Book book){
        return ResponseEntity.ok(bookService.create(book));
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a book from the database")
    public ResponseEntity<Void> deleteBook(@PathVariable int id){
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Operation(summary = "Update a book in the database")
    public ResponseEntity<Void> updateBook(@RequestBody Book book){
        bookService.update(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
