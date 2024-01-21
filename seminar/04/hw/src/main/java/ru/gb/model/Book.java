package ru.gb.model;

import lombok.Data;

@Data
public class Book {
    private static long sequence = 1L;

    private final long id;
    private String name;

    public Book() {
        this.id = sequence++;
    }
    public Book(String name) {
        this();
        this.name = name;
    }
}
