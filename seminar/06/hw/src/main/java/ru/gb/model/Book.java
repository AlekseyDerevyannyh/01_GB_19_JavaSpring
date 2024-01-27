package ru.gb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
@Schema(name = "Книга")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(name = "Идентификатор")
    private Long id;

    @Column(nullable = false)
    @Schema(name = "Название")
    private String name;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }
}
