package br.com.library.service;

import br.com.library.domain.Book;
import br.com.library.repository.BookRepository;
import br.com.library.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookService {
    private final Utils utils;
    private final BookRepository bookRepository;

    public Page<Book> listAll(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    public Book findById(int id){
        return utils.findBookOrThrowNotFound(id, bookRepository);
    }

    public List<Book> findByName(String name){
        return bookRepository.findByName(name);
    }

    public List<Book> findByAuthor(String author){
        return bookRepository.findByAuthor(author);
    }

    public List<Book> findByGenre(String genre){
        return bookRepository.findByGenre(genre);
    }

    public Book create(Book book){
        return bookRepository.save(book);
    }

    public void delete(int id){
        bookRepository.delete(utils.findBookOrThrowNotFound(id, bookRepository));
    }

    @Transactional
    public void update(Book book){
        bookRepository.save(book);
    }
}
