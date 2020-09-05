package br.com.library.service;

import br.com.library.domain.Book;
import br.com.library.exception.ResourceNotFoundException;
import br.com.library.util.BookCreator;
import br.com.library.util.Utils;
import java.util.List;
import java.util.Optional;
import br.com.library.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepositoryMock;

    @Mock
    private Utils utilsMock;

    @BeforeEach
    public void setUp() {
        PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);
        BDDMockito.when(bookRepositoryMock.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(BookCreator.createValidBook()));
        BDDMockito.when(bookRepositoryMock.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(BookCreator.createValidBook()));
        BDDMockito.when(bookRepositoryMock.save(BookCreator.createBookToBeSaved())).thenReturn(BookCreator.createValidBook());
        BDDMockito.doNothing().when(bookRepositoryMock).delete(ArgumentMatchers.any(Book.class));
        BDDMockito.when(bookRepositoryMock.save(BookCreator.createValidBook())).thenReturn(BookCreator.createValidUpdatedBook());
        BDDMockito.when(utilsMock.findBookOrThrowNotFound(ArgumentMatchers.anyInt(), ArgumentMatchers.any(BookRepository.class)))
                .thenReturn(BookCreator.createValidBook());
    }

    @Test
    @DisplayName("listAll returns a pageable list of books when successful")
    public void listAll_ReturnListOfBooksInsidePageObject_WhenSuccessful() {
        String expectedName = BookCreator.createValidBook().getName();
        Page<Book> bookPage = bookService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(bookPage).isNotNull();
        Assertions.assertThat(bookPage.toList()).isNotEmpty();
        Assertions.assertThat(bookPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns a book when successful")
    public void findById_ReturnListOfBooksInsidePageObject_WhenSuccessful() {
        Integer expectedId = BookCreator.createValidBook().getId();
        Book book = bookService.findById(1);

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull();
        Assertions.assertThat(book.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a pageable list of books when successful")
    public void findByName_ReturnListOfBooksInsidePageObject_WhenSuccessful() {
        String expectedName = BookCreator.createValidBook().getName();
        List<Book> bookList = bookService.findByName("Book 1");

        Assertions.assertThat(bookList).isNotNull();
        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save creates a book when successful")
    public void save_CreatesBook_WhenSuccessful() {
        Integer expectedId = BookCreator.createValidBook().getId();
        Book bookToBeSaved = BookCreator.createBookToBeSaved();
        Book book = bookService.create(bookToBeSaved);

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull();
        Assertions.assertThat(book.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("delete removes the book when successful")
    public void delete_RemovesBook_WhenSuccessful() {
        Assertions.assertThatCode(() -> bookService.delete(1)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete throws ResourceNotFoundException when the book does not exist")
    public void delete_ThrowsResourceNotFoundException_WhenBookDoesNotExist() {
        BDDMockito.when(
                utilsMock.findBookOrThrowNotFound(ArgumentMatchers.anyInt(), ArgumentMatchers.any(BookRepository.class)))
                .thenThrow(new ResourceNotFoundException("Anime not found"));

        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> bookService.delete(1));
    }

    @Test
    @DisplayName("save updating update book when successful")
    public void save_SaveUpdatedBook_WhenSuccessful() {
        Book validUpdatedBook = BookCreator.createValidUpdatedBook();
        String expectedName = validUpdatedBook.getName();
        Book book = bookService.create(BookCreator.createValidBook());

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull();
        Assertions.assertThat(book.getName()).isEqualTo(expectedName);
    }
}