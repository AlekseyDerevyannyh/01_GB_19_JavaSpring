package ru.gb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Entity
@Table(name = "issues")
@Data
@Schema(name = "Выдача книги")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(name = "Идентификатор")
    private Long id;

    @Column(nullable = false)
    @Schema(name = "Идентификатор книги")
    private Long bookId;

    @Column(nullable = false)
    @Schema(name = "Идентификатор читателя")
    private Long readerId;
    /**
     * Дата выдачи
     */
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    @Schema(name = "Дата выдачи книги")
    private LocalDateTime issuedAt;

    @Column(columnDefinition = "TIMESTAMP")
    @Schema(name = "Дата возврата книги")
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
