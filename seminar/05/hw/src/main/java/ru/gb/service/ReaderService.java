package ru.gb.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.model.Book;
import ru.gb.model.Issue;
import ru.gb.model.Reader;
import ru.gb.repository.ReaderRepository;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;

    @PostConstruct
    public void generateData() {
        readerRepository.save(new Reader("Игорь"));
        readerRepository.save(new Reader("Александр"));
        readerRepository.save(new Reader("Наташа"));
    }

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader getReaderById(Long id) {
        Reader reader = readerRepository.findById(id).orElse(null);
        if (reader == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\"");
        }
        return reader;
    }

    public Reader addReader(Reader reader) {
        return readerRepository.save(reader);
    }

    public void deleteReader(Long id) {
        readerRepository.deleteById(id);
    }

    public List<Issue> getIssuesByReaderId(Long id) {
        return issueRepository.findAll().stream()
                .filter(issue -> issue.getReaderId().equals(id))
                .toList();
    }

    public List<Book> getBooksByReaderId(Long id) {
        List<Book> result = new ArrayList<>();
        for (Issue issue : issueRepository.findAll()) {
            if (issue.getReaderId().equals(id)) {
                result.add(bookRepository.findById(issue.getBookId()).orElse(null));
            }
        }
        return result;
    }
}
