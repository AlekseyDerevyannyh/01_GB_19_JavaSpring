package ru.gb.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Reader {
    private static long sequence = 1L;

    private final long id;
    private String name;

    public Reader() {
        this.id = sequence++;
    }
    public Reader(String name) {
        this();
        this.name = name;
    }
}
