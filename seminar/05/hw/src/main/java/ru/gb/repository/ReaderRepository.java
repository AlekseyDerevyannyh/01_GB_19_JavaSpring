package ru.gb.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.model.Reader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ReaderRepository {
    private final List<Reader> readers;

    public ReaderRepository() {
        this.readers = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        readers.addAll(List.of(
                new Reader("Игорь"),
                new Reader("Александр"),
                new Reader("Наташа")
        ));
    }

    public List<Reader> getAllReaders() {
        return List.copyOf(readers);
    }

    public Reader getReaderById(long id) {
        return readers.stream().filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public Reader addReader(Reader reader) {
        readers.add(reader);
        return reader;
    }

    public void deleteReader(long id) {
        readers.removeIf(reader -> reader.getId() == id);
    }
}
