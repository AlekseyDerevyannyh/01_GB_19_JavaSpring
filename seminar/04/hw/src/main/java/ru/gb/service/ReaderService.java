package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.model.Book;
import ru.gb.model.Issue;
import ru.gb.model.Reader;
import ru.gb.repository.BookRepository;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.ReaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.getAllReaders();
    }

    public Reader getReaderById(long id) {
        Reader reader = readerRepository.getReaderById(id);
        if (reader == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + id + "\"");
        }
        return reader;
    }

    public Reader addReader(Reader reader) {
        return readerRepository.addReader(reader);
    }

    public void deleteReader(long id) {
        readerRepository.deleteReader(id);
    }

    public List<Issue> getIssuesByReaderId(long id) {
        return issueRepository.getIssues().stream()
                .filter(issue -> issue.getReaderId() == id)
                .toList();
    }

    public List<Book> getBooksByReaderId(long id) {
        List<Book> result = new ArrayList<>();
        for (Issue issue : issueRepository.getIssues()) {
            if (issue.getReaderId() == id) {
                result.add(bookRepository.getBookById(issue.getBookId()));
            }
        }
        return result;
    }
}
