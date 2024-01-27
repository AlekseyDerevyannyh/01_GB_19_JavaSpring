package ru.gb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Issue;
import ru.gb.model.IssueRequest;
import ru.gb.service.IssueService;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/issue")
@Tag(name = "Issue controller")
public class IssueController {
    @Autowired
    private IssueService service;

    @PostMapping
    @Operation(summary = "Add issue for book", description = "Добавляет факт выдачи книги")
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest request) {
        log.info("Получен запрос на выдачу: readerId = {}, bookId = {}", request.getReaderId(), request.getBookId());

        final Issue issue;
        try {
            issue = service.issue(request);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(issue);
    }

    @PutMapping("/{issueId}")
    @Operation(summary = "Return book", description = "Осуществляет запись факта возврата книги")
    public ResponseEntity<Void> returnBook(@PathVariable Long issueId) {
        if (service.returnBook(issueId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issue by id", description = "Загружает информацию о выдаче по идентификатору")
    public ResponseEntity<Issue> getIssue(@PathVariable Long id) {
        Issue issue = service.getIssueById(id);
        if (issue != null) {
            return new ResponseEntity<>(issue, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
