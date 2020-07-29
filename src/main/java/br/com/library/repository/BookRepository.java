package br.com.library.repository;

import br.com.library.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    List<Book> findByNameIgnoreCaseContaining(String name);
    List<Book> findByAuthorIgnoreCaseContaining(String author, Pageable pageable);
    List<Book> findByGenreIgnoreCaseContaining(String genre, Pageable pageable);
}
