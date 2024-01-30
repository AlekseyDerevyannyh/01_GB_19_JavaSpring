package ru.gb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Book;
import ru.gb.service.BookService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/book")
@Tag(name = "Book controller")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Загружает все книги, которые есть в библиотеке")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(service.getAllBooks(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Загружает книгу из библиотеки по идентификатору")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book;
        try {
            book = service.getBookById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add book", description = "Добавляет новую книгу в библиотеку")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(service.addBook(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book by id", description = "Удаляет книгу из библиотеки по её идентификатору")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        service.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
