package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.model.IssueRequest;
import ru.gb.model.Issue;
import ru.gb.repository.BookRepository;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.ReaderRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    @Value("${application.max-allowed-books:1}")
    private int maxAllowedBooks;

    public Issue issue(IssueRequest request) {
        if (bookRepository.getBookById(request.getBookId()) == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + request.getBookId() + "\"");
        }
        if (readerRepository.getReaderById(request.getReaderId()) == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + request.getReaderId() + "\"");
        }
        // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)
//        if (issueRepository.isIssuesContainReaderById(request.getReaderId())) {
//            throw new IllegalArgumentException("У читателя с идентификатором \"" +
//                    request.getReaderId() + "\" уже есть книга");
//        }

        if (issueRepository.getCountBooksByReader(request.getReaderId()) >= maxAllowedBooks) {
            throw new IllegalArgumentException("У читателя с идентификатором \"" +
                    request.getReaderId() + "\" уже есть максимально допустимое число книг (" +
                    maxAllowedBooks + ")");
        }

        Issue issue = new Issue(request.getBookId(), request.getReaderId());
        issueRepository.save(issue);
        return issue;
    }

    public Issue getIssueById(long id) {
        return issueRepository.getIssueById(id);
    }

    public Boolean returnBook(long issueId) {
        return issueRepository.returnBook(issueId);
    }
}
