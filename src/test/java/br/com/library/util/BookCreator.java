package br.com.library.util;

import br.com.library.domain.Book;

public class BookCreator {

    public static Book createBookToBeSaved() {
        return Book.builder()
                .name("Book 1")
                .author("Gustavo")
                .genre("Horror")
                .build();
    }

    public static Book createValidBook() {
        return Book.builder()
                .name("Book 1")
                .author("Gustavo")
                .genre("Horror")
                .id(1)
                .build();
    }

    public static Book createValidUpdatedBook() {
        return Book.builder()
                .name("Book 2")
                .author("Gustavo")
                .genre("Horror")
                .id(1)
                .build();
    }
}
