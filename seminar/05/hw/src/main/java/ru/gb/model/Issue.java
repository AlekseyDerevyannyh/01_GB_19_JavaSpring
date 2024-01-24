package ru.gb.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Entity
@Table(name = "issues")
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private Long readerId;
    /**
     * Дата выдачи
     */
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime issuedAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime returnedAt;

    public Issue() {
    }

    public Issue(long bookId, long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.issuedAt = LocalDateTime.now();
        this.returnedAt = null;
    }

    public Boolean returnBook() {
        if (returnedAt == null) {
            returnedAt = LocalDateTime.now();
            return true;
        }
        return false;
    }
}
