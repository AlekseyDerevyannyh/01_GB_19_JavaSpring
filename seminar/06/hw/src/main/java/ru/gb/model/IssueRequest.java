package ru.gb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Запрос на выдачу
 */
@Data
@Schema(name = "Запрос на выдачу")
public class IssueRequest {

    /**
     * Идентификатор читателя
     */
    @Schema(name = "Идентификатор читателя")
    private Long readerId;

    /**
     * Идентификатор книги
     */
    @Schema(name = "Идентификатор книги")
    private Long bookId;
}
