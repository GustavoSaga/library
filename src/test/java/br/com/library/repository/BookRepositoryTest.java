package br.com.library.repository;

import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;
import br.com.library.domain.Book;
import br.com.library.util.BookCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Book Repository Tests")
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Save creates book when successful")
    public void save_PersistBook_WhenSuccessful() {
        Book book = BookCreator.createBookToBeSaved();
        Book savedBook = this.bookRepository.save(book);

        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getName()).isNotNull();
        Assertions.assertThat(savedBook.getName()).isEqualTo(book.getName());
    }

    @Test
    @DisplayName("Save updates book when successful")
    public void save_UpdateBook_WhenSuccessful() {
        Book book = BookCreator.createBookToBeSaved();
        Book savedBook = this.bookRepository.save(book);
        savedBook.setName("Neuromancer");
        Book updatedAnime = this.bookRepository.save(savedBook);

        Assertions.assertThat(savedBook.getId()).isNotNull();
        Assertions.assertThat(savedBook.getName()).isNotNull();
        Assertions.assertThat(savedBook.getName()).isEqualTo(updatedAnime.getName());
    }

    @Test
    @DisplayName("Delete removes book when successful")
    public void delete_RemoveBook_WhenSuccessful() {
        Book book = BookCreator.createBookToBeSaved();
        Book savedBook = this.bookRepository.save(book);
        this.bookRepository.delete(book);
        Optional<Book> bookOptional = this.bookRepository.findById(savedBook.getId());

        Assertions.assertThat(bookOptional.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Find by name returns book when successful")
    public void findByName_ReturnBook_WhenSuccessful() {
        Book book = BookCreator.createBookToBeSaved();
        Book savedBook = this.bookRepository.save(book);
        String name = savedBook.getName();
        List<Book> bookList = this.bookRepository.findByName(name);

        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList).contains(savedBook);
    }

    @Test
    @DisplayName("Find by name returns empty list when no book is found")
    public void findByName_ReturnEmptyList_WhenAnimeNotFound() {
        String name = "fake-name";
        List<Book> bookList = this.bookRepository.findByName(name);

        Assertions.assertThat(bookList).isEmpty();
    }
}