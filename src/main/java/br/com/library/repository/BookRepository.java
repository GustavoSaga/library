package br.com.library.repository;

import br.com.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByName(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);
}
