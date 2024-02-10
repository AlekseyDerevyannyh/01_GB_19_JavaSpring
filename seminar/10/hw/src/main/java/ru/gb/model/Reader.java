package ru.gb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "readers")
@Data
@Schema(name = "Читатель")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(name = "Идентификатор")
    private Long id;

    @Column(nullable = false)
    @Schema(name = "Имя")
    private String name;

    public Reader() {
    }
    public Reader(String name) {
        this.name = name;
    }
}
