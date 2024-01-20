package ru.gb.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Data
public class Issue {
    private static long sequence = 1L;

    private final long id;
    private final long bookId;
    private final long readerId;
    /**
     * Дата выдачи
     */
    private final LocalDateTime issuedAt;
    private LocalDateTime returnedAt;

    public Issue(long bookId, long readerId) {
        this.id = sequence++;
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
