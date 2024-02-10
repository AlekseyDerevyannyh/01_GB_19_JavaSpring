package ru.gb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Issue;
import ru.gb.model.Reader;
import ru.gb.service.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reader")
@Tag(name = "Reader controller")
public class ReaderController {
    private final ReaderService service;

    public ReaderController(ReaderService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all readers", description = "Загружает список всех читателей")
    public ResponseEntity<List<Reader>> getAllReaders() {
        return new ResponseEntity<>(service.getAllReaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reader by id", description = "Загружает информацию о читателе по его идентификатору")
    public ResponseEntity<Reader> getReaderById(@PathVariable Long id) {
        Reader reader;
        try {
            reader = service.getReaderById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reader, HttpStatus.OK);
    }

    @GetMapping("/{id}/issue")
    @Operation(summary = "Get issue by reader id", description = "Загружает список всех выдач для читателя по его идентификатору")
    public ResponseEntity<List<Issue>> getIssueByReaderId(@PathVariable Long id) {
        List<Issue> issues = service.getIssuesByReaderId(id);
        if (!issues.isEmpty()) {
            return new ResponseEntity<>(issues, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(summary = "Add reader", description = "Добавляет нового читателя")
    public ResponseEntity<Reader> addReader(@RequestBody Reader reader) {
        return new ResponseEntity<>(service.addReader(reader), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete reader", description = "Удаляет читателя по его идентификатору")
    public ResponseEntity<Void> deleteReader(@PathVariable Long id) {
        service.deleteReader(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
