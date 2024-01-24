package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    List<Book> getAll();
//    Book getBookById(Long id);
//    Book addBook(Book book);
//    void deleteBook(Long id);
}
