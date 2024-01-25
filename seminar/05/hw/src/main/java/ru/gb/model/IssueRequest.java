package ru.gb.model;

import lombok.Data;

/**
 * Запрос на выдачу
 */
@Data
public class IssueRequest {

    /**
     * Идентификатор читателя
     */
    private Long readerId;

    /**
     * Идентификатор книги
     */
    private Long bookId;
}
