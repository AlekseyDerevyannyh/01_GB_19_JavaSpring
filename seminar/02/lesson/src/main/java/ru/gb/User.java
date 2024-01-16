package ru.gb;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class User {

    private static long idCounter = 1L;

    private final long id;
    private String name;

    @JsonCreator
    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this.id = idCounter++;
        this.name = name;
    }

}
