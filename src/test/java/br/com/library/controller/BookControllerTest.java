package br.com.library.controller;

import java.util.List;
import br.com.library.domain.Book;
import br.com.library.service.BookService;
import br.com.library.util.BookCreator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Book Controller Tests")
class BookControllerTest {
    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookServiceMock;

    @BeforeEach
    public void setUp() {
        PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookServiceMock.listAll(ArgumentMatchers.any())).thenReturn(bookPage);
        BDDMockito.when(bookServiceMock.findById(ArgumentMatchers.anyInt())).thenReturn(BookCreator.createValidBook());
        BDDMockito.when(bookServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(BookCreator.createValidBook()));
        BDDMockito.when(bookServiceMock.create(BookCreator.createBookToBeSaved())).thenReturn(BookCreator.createValidBook());
        BDDMockito.doNothing().when(bookServiceMock).delete(ArgumentMatchers.anyInt());
        BDDMockito.when(bookServiceMock.create(BookCreator.createValidBook())).thenReturn(BookCreator.createValidUpdatedBook());
    }

    @Test
    @DisplayName("listAll returns a pageable list of books when successful")
    public void listAll_ReturnListOfBooksInsidePageObject_WhenSuccessful() {
        String expectedName = BookCreator.createValidBook().getName();
        Page<Book> bookPage = bookController.listAllBooks(null).getBody();

        Assertions.assertThat(bookPage).isNotNull();
        Assertions.assertThat(bookPage.toList()).isNotEmpty();
        Assertions.assertThat(bookPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns book with given id when successful")
    public void findById_ReturnListOfBooksInsidePageObject_WhenSuccessful() {
        Integer expectedId = BookCreator.createValidBook().getId();
        Book book = bookController.findBookById(1).getBody();

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull();
        Assertions.assertThat(book.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns book with given name when successful")
    public void findByName_ReturnListOfBooks_WhenSuccessful() {
        String expectedName = BookCreator.createValidBook().getName();
        List<Book> bookList = bookController.findBookByName("Book 1").getBody();

        Assertions.assertThat(bookList).isNotNull();
        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns list of books from given author when successful")
    public void findByAuthor_ReturnListOfBooks_WhenSuccessful() {
        String expectedAuthor = BookCreator.createValidBook().getAuthor();
        List<Book> bookList = bookController.findBookByName("Book 1").getBody();

        Assertions.assertThat(bookList).isNotNull();
        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList.get(0).getAuthor()).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("findByName returns list of books of a given genre when successful")
    public void findByGenre_ReturnListOfBooks_WhenSuccessful() {
        String expectedGenre = BookCreator.createValidBook().getGenre();
        List<Book> bookList = bookController.findBookByName("Book 1").getBody();

        Assertions.assertThat(bookList).isNotNull();
        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList.get(0).getGenre()).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("save creates a book when successful")
    public void save_CreatesBook_WhenSuccessful() {
        Integer expectedId = BookCreator.createValidBook().getId();
        Book bookToBeSaved = BookCreator.createBookToBeSaved();
        Book book = bookController.saveBook(bookToBeSaved).getBody();

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull();
        Assertions.assertThat(book.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("delete removes book with given id when successful")
    public void delete_RemovesBook_WhenSuccessful() {
        ResponseEntity<Void> responseEntity = bookController.deleteBook(1);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("update save updated book when successful")
    public void update_SaveUpdatedBook_WhenSuccessful() {
        ResponseEntity<Void> responseEntity = bookController.updateBook(BookCreator.createValidBook());

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }
}
