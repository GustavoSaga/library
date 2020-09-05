package br.com.library.client;

import br.com.library.domain.Book;
import br.com.library.wrapper.PageableResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SpringClient {
    public static void main(String[] args) {
        //@formatter:off
        ResponseEntity<PageableResponse<Book>> exchangeBookList = new RestTemplate()
                .exchange("http://localhost:8080/books?sort=name,desc", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Book>>() {});
        //@formatter:on

        log.info("Book list {} ", exchangeBookList.getBody());
        Book book1 = Book.builder().name("Book 1").build();
        Book book2 = Book.builder().name("Book 2").build();

        Book book1Saved = new RestTemplate()
                .exchange("http://localhost:8080/books", HttpMethod.POST, new HttpEntity<>(book1, createJsonHeader()), Book.class)
                .getBody();

        log.info("Book 1 saved id: {} ", book1Saved.getId());
        book1Saved.setName("Neuromancer");

        ResponseEntity<Void> exchangeUpdated = new RestTemplate()
                .exchange("http://localhost:8080/books", HttpMethod.PUT, new HttpEntity<>(book1Saved, createJsonHeader()), Void.class);

        log.info("Book 1 updated status: {} ", exchangeUpdated.getStatusCode());

        ResponseEntity<Void> exchangeDeleted = new RestTemplate()
                .exchange("http://localhost:8080/books/{id}", HttpMethod.DELETE, null, Void.class, book1Saved.getId());

        log.info("Neuromancer deleted status: {} ", exchangeDeleted.getStatusCode());
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private static void testGetWithRestTemplate() {
        ResponseEntity<Book> bookResponseEntity = new RestTemplate().getForEntity("http://localhost:8080/books/{id}", Book.class, 2);
        log.info("Response Entity {}", bookResponseEntity);
        log.info("Response Data {}", bookResponseEntity.getBody());
        Book book = new RestTemplate().getForObject("http://localhost:8080/books/{id}", Book.class, 2);
        log.info("Anime {} ", book);
    }
}
