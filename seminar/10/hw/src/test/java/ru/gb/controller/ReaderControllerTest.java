package ru.gb.controller;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.TestSpringBootBase;
import ru.gb.model.Reader;
import ru.gb.repository.ReaderRepository;

import java.util.List;
import java.util.Objects;

public class ReaderControllerTest extends TestSpringBootBase {
    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ReaderRepository readerRepository;

    @Data
    static class TestReader {
        private Long id;
        private String name;
    }

    @Data
    static class TestReaderSave {
        private String name;

        public TestReaderSave(String name) {
            this.name = name;
        }
    }

    @BeforeEach
    void setUp() {
        readerRepository.deleteAll();
    }

    @Test
    void testFindByIdSuccess() {
        Reader expected = readerRepository.save(new Reader("reader"));

        TestReader responseBody = webTestClient.get()
                .uri("/reader/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TestReader.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getName(), responseBody.getName());
    }

    @Test
    void testFindByIdNotFound() {
        webTestClient.get()
                .uri("/reader/1")
                .exchange()
                .expectStatus().isNotFound();
    }



    @Test
    void testGetAll() {
        readerRepository.saveAll(List.of(
                new Reader("reader 1"),
                new Reader("reader 2")
        ));
        List<Reader> expected = readerRepository.findAll();

        List<TestReader> responseBody = webTestClient.get()
                .uri("/reader")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<TestReader>>() {})
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (TestReader readerResponse : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), readerResponse.getId()))
                    .anyMatch((it -> Objects.equals(it.getName(), readerResponse.getName())));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void testSave() {
        TestReaderSave request = new TestReaderSave("new reader");

        Reader responseBody = webTestClient.post()
                .uri("/reader")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Reader.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertTrue(readerRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void testDelete() {
        readerRepository.save(new Reader("reader"));
        Long requestId = readerRepository.findAll().stream().findFirst().get().getId();

        webTestClient.delete()
                .uri("/reader/" + requestId)
                .exchange()
                .expectStatus().isNoContent();
        webTestClient.get()
                .uri("/reader/" + requestId)
                .exchange()
                .expectStatus().isNotFound();
    }
}
