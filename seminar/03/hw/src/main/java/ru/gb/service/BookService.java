package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.model.Book;
import ru.gb.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book getBookById(long id) {
        Book book = bookRepository.getBookById(id);
        if (book == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + id + "\"");
        }
        return book;
    }

    public Book addBook(Book book) {
        return bookRepository.addBook(book);
    }

    public void deleteBook(long id) {
        bookRepository.deleteBook(id);
    }
}
