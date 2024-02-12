package ru.gb.controller;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.TestSpringBootBase;
import ru.gb.model.Book;
import ru.gb.model.Issue;
import ru.gb.model.Reader;
import ru.gb.repository.BookRepository;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.ReaderRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class IssueControllerTest extends TestSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReaderRepository readerRepository;

    @Value("${application.max-allowed-books:1}")
    private int maxAllowedBooks;

    @Data
    static class TestIssue {
        private Long id;
        private Long bookId;
        private Long readerId;
        private LocalDateTime issuedAt;
        private LocalDateTime returnedAt;
    }

    @Data
    static class TestIssueSave {
        private Long bookId;
        private Long readerId;

        public TestIssueSave(Long bookId, Long readerId) {
            this.bookId = bookId;
            this.readerId = readerId;
        }
    }

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        readerRepository.deleteAll();
        issueRepository.deleteAll();
    }

    @Test
    void testFindByIdSuccess() {
        bookRepository.save(new Book("book"));
        readerRepository.save(new Reader("reader"));
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        Long readerId = readerRepository.findAll().stream().findFirst().get().getId();
        Issue expected = issueRepository.save(new Issue(bookId, readerId));

        TestIssue responseBody = webTestClient.get()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestIssue.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expected.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expected.getIssuedAt().truncatedTo(ChronoUnit.SECONDS), responseBody.getIssuedAt().truncatedTo(ChronoUnit.SECONDS));
        Assertions.assertNull(responseBody.getReturnedAt());
    }

    @Test
    void testFindByIdNotFound() {
        webTestClient.get()
                .uri("/issue/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAll() {
        bookRepository.saveAll(List.of(
                new Book("book1"),
                new Book("book2")
        ));
        readerRepository.saveAll(List.of(
                new Reader("reader1"),
                new Reader("reader2")
        ));
        Long bookId1 = bookRepository.findAll().stream()
                .filter(book -> book.getName().equals("book1"))
                .findFirst().get().getId();
        Long bookId2 = bookRepository.findAll().stream()
                .filter(book -> book.getName().equals("book2"))
                .findFirst().get().getId();
        Long readerId1 = readerRepository.findAll().stream()
                .filter(reader -> reader.getName().equals("reader1"))
                .findFirst().get().getId();
        Long readerId2 = readerRepository.findAll().stream()
                .filter(reader -> reader.getName().equals("reader2"))
                .findFirst().get().getId();
        issueRepository.saveAll(List.of(
                new Issue(bookId1, readerId1),
                new Issue(bookId2, readerId2)
        ));
        List<Issue> expected = issueRepository.findAll();

        List<TestIssue> responseBody = webTestClient.get()
                .uri("/issue")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<TestIssue>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (TestIssue issueResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), issueResponse.getId()))
                    .filter(it -> Objects.equals(it.getBookId(), issueResponse.getBookId()))
                    .filter(it -> Objects.equals(it.getReaderId(), issueResponse.getReaderId()))
                    .anyMatch(it -> Objects.equals(it.getIssuedAt().truncatedTo(ChronoUnit.SECONDS),
                            issueResponse.getIssuedAt().truncatedTo(ChronoUnit.SECONDS)));
            Assertions.assertTrue(found);
        }
        for (TestIssue issueResponse : responseBody) {
            Assertions.assertNull(issueResponse.getReturnedAt());
        }
    }

    @Test
    void testSaveSuccess() {
        bookRepository.save(new Book("book"));
        readerRepository.save(new Reader("reader"));
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        Long readerId = readerRepository.findAll().stream().findFirst().get().getId();
        TestIssueSave request = new TestIssueSave(bookId, readerId);

        Issue responseBody = webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Issue.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertTrue(issueRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void testSaveConflict() {
        readerRepository.save(new Reader("reader"));
        Long readerId = readerRepository.findAll().stream().findFirst().get().getId();
        for (int i = 0; i <= maxAllowedBooks; i ++) {
            bookRepository.save(new Book("book" + i));
        }
        for (int i = 0; i < maxAllowedBooks; i ++) {
            issueRepository.save(new Issue(bookRepository.findAll().get(i).getId(), readerId));
        }
        TestIssueSave request = new TestIssueSave(bookRepository.findAll().get(maxAllowedBooks).getId(), readerId);

        Issue responseBody = webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT)
                .expectBody(Issue.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void testSaveNoFound() {
        bookRepository.save(new Book("book"));
        readerRepository.save(new Reader("reader"));
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        Long readerId = readerRepository.findAll().stream().findFirst().get().getId();
        TestIssueSave request = new TestIssueSave(bookId + 1, readerId);

        Issue responseBody = webTestClient.post()
                .uri("/issue")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Issue.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void testReturnBookSuccess() {
        bookRepository.save(new Book("book"));
        readerRepository.save(new Reader("reader"));
        Long bookId = bookRepository.findAll().stream().findFirst().get().getId();
        Long readerId = readerRepository.findAll().stream().findFirst().get().getId();
        issueRepository.save(new Issue(bookId, readerId));
        Long requestId = issueRepository.findAll().stream().findFirst().get().getId();

        webTestClient.put()
                .uri("/issue/" + requestId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testReturnBookNotFound() {
        webTestClient.put()
                .uri("/issue/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}
