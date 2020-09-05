package br.com.library.integration;

import java.util.List;
import java.util.Optional;
import br.com.library.domain.Book;
import br.com.library.repository.BookRepository;
import br.com.library.util.BookCreator;
import br.com.library.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @MockBean
    private BookRepository bookRepositoryMock;

    @BeforeEach
    public void setUp() {
        PageImpl<Book> bookPage = new PageImpl<>(List.of(BookCreator.createValidBook()));

        BDDMockito.when(bookRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(bookPage);
        BDDMockito.when(bookRepositoryMock.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(BookCreator.createValidBook()));
        BDDMockito.when(bookRepositoryMock.findByName(ArgumentMatchers.anyString())).thenReturn(List.of(BookCreator.createValidBook()));
        BDDMockito.when(bookRepositoryMock.save(BookCreator.createBookToBeSaved())).thenReturn(BookCreator.createValidBook());
        BDDMockito.doNothing().when(bookRepositoryMock).delete(ArgumentMatchers.any(Book.class));
        BDDMockito.when(bookRepositoryMock.save(BookCreator.createValidBook())).thenReturn(BookCreator.createValidUpdatedBook());
    }

    @Test
    @DisplayName("listAll returns a pageable list of books when successful")
    public void listAll_ReturnListOfBooksInsidePageObject_WhenSuccessful() {
        String expectedName = BookCreator.createValidBook().getName();

        //@formatter:off
        Page<Book> bookPage = testRestTemplate.exchange("/books", HttpMethod.GET,
                null, new ParameterizedTypeReference<PageableResponse<Book>>() {}).getBody();
        //@formatter:on

        Assertions.assertThat(bookPage).isNotNull();
        Assertions.assertThat(bookPage.toList()).isNotEmpty();
        Assertions.assertThat(bookPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns a book when successful")
    public void findById_ReturnListOfBooksInsidePageObject_WhenSuccessful() {
        Integer expectedId = BookCreator.createValidBook().getId();
        Book book = testRestTemplate.getForObject("/books/1", Book.class);

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull();
        Assertions.assertThat(book.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns book with given name when successful")
    public void findByName_ReturnListOfAnimes_WhenSuccessful() {
        String expectedName = BookCreator.createValidBook().getName();

        //@formatter:off
        List<Book> bookList = testRestTemplate.exchange("/books/find?name='book 1'",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {}).getBody();
        //@formatter:on

        Assertions.assertThat(bookList).isNotNull();
        Assertions.assertThat(bookList).isNotEmpty();
        Assertions.assertThat(bookList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save creates a book when successful")
    public void save_CreatesBook_WhenSuccessful() {
        Integer expectedId = BookCreator.createValidBook().getId();
        Book bookToBeSaved = BookCreator.createBookToBeSaved();
        Book book = testRestTemplate.exchange("/books", HttpMethod.POST, createJsonHttpEntity(bookToBeSaved), Book.class).getBody();

        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book.getId()).isNotNull();
        Assertions.assertThat(book.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("delete removes book with given id when successful")
    public void delete_RemovesBook_WhenSuccessful() {
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/books/1", HttpMethod.DELETE, null, Void.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("update save updated anime when successful")
    public void update_SaveUpdatedAnime_WhenSuccessful() {
        Book validBook = BookCreator.createValidBook();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/books", HttpMethod.PUT, createJsonHttpEntity(validBook), Void.class);

        Assertions.assertThat(responseEntity).isNotNull();
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    private HttpEntity<Book> createJsonHttpEntity(Book book) {
        return new HttpEntity<>(book, createJsonHeader());
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
