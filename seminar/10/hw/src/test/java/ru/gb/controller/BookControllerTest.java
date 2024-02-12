package ru.gb.controller;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.TestSpringBootBase;
import ru.gb.model.Book;
import ru.gb.repository.BookRepository;

import java.util.List;
import java.util.Objects;

public class BookControllerTest extends TestSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    BookRepository bookRepository;

    @Data
    static class TestBook {
        private Long id;
        private String name;
    }

    @Data
    static class TestBookSave {
        private String name;

        public TestBookSave (String name) {
            this.name = name;
        }
    }

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void testFindByIdSuccess() {
        Book expected = bookRepository.save(new Book("book"));

        TestBook responseBody = webTestClient.get()
                .uri("/book/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestBook.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }

    @Test
    void testFindByIdNotFound() {
        webTestClient.get()
                .uri("/book/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAll() {
        bookRepository.saveAll(List.of(
                new Book("book 1"),
                new Book("book 2")
        ));
        List<Book> expected = bookRepository.findAll();

        List<TestBook> responseBody  = webTestClient.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<TestBook>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (TestBook bookResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), bookResponse.getId()))
                    .anyMatch(it -> Objects.equals(it.getName(), bookResponse.getName()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void testSave() {
        TestBookSave request = new TestBookSave("new book");

        Book responseBody = webTestClient.post()
                .uri("/book")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertTrue(bookRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void testDelete() {
        bookRepository.save(new Book("book"));
        Long requestId = bookRepository.findAll().stream().findFirst().get().getId();

        webTestClient.delete()
                .uri("/book/" + requestId)
                .exchange()
                .expectStatus().isNoContent();
        webTestClient.get()
                .uri("/book/" + requestId)
                .exchange()
                .expectStatus().isNotFound();
    }
}
