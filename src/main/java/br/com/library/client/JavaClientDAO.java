package br.com.library.client;

import br.com.library.handler.RestResponseExceptionHandler;
import br.com.library.model.Book;
import br.com.library.model.PageableResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JavaClientDAO {
    private RestTemplate restTemplate = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/protected/books")
            .basicAuthentication("user","user")
            .errorHandler(new RestResponseExceptionHandler())
            .build();

    private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/v1/admin/books")
            .basicAuthentication("admin","admin")
            .errorHandler(new RestResponseExceptionHandler())
            .build();

    public List<Book> listAll () {
        return restTemplate.exchange("/", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Book>>() {}).getBody().getContent();
    }
    public Book findById(Long id){
        return restTemplate.getForObject("/{id}", Book.class, id);
    }
    public Book findByName(String name){
        return restTemplate.getForObject("name/{name}", Book.class, name);
    }
    public Book findByAuthor(String author){
        return restTemplate.getForObject("author/{author}", Book.class, author);
    }
    public Book findByGenre(String genre){
        return restTemplate.getForObject("genre/{genre}", Book.class, genre);
    }
    public Book save (Book book) {
        return restTemplateAdmin.exchange("/", HttpMethod.POST, new HttpEntity<>(book, createJSONHeader()), Book.class).getBody();
    }
    public void update (Book book) {
        restTemplateAdmin.put("/", book);
    }
    public void deleteById (int id) {
        restTemplateAdmin.delete("/{id}", id);
    }

    private static HttpHeaders createJSONHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
