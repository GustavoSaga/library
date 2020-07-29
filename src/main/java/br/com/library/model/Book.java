package br.com.library.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class Book extends AbstractEntity {
    @NotEmpty(message = "Name can't be blank")
    @Column(name = "name_book")
    private String name;

    @NotEmpty(message = "Author can't be blank")
    @Column(name = "name_author")
    private String author;

    @NotEmpty(message = "Genre can't be blank")
    @Column(name = "genre_book")
    private String genre;

    public Book() {
    }
    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }
    public Book(String name, String author, String genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, author, genre);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
