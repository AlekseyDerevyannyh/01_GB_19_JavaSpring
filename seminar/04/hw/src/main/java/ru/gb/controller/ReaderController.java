package ru.gb.controller;

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
public class ReaderController {
    private final ReaderService service;

    public ReaderController(ReaderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Reader>> getAllReaders() {
        return new ResponseEntity<>(service.getAllReaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable long id) {
        Reader reader;
        try {
            reader = service.getReaderById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reader, HttpStatus.OK);
    }

    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getIssueByReaderId(@PathVariable long id) {
        List<Issue> issues = service.getIssuesByReaderId(id);
        if (issues.isEmpty()) {
            return new ResponseEntity<>(issues, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody Reader reader) {
        return new ResponseEntity<>(service.addReader(reader), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable long id) {
        service.deleteReader(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
