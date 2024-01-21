package ru.gb.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository {
    private final List<Book> books;

    public BookRepository() {
        this.books = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        books.addAll(List.of(
                new Book("война и мир"),
                new Book("мертвые души"),
                new Book("чистый код")
        ));
    }

    public List<Book> getAllBooks() {
        return List.copyOf(books);
    }

    public Book getBookById(long id) {
        return books.stream().filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    public void deleteBook(long id) {
        books.removeIf(book -> book.getId() == id);
    }
}
