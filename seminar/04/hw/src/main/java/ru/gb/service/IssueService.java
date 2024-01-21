package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.model.Book;
import ru.gb.model.IssueRequest;
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

    public List<String[]> getAllIssuesToStringArray() {
        List<String[]> result = new ArrayList<>();
        String[] issueArray;
        for (Issue issue : issueRepository.getIssues()) {
            issueArray = new String[5];
            issueArray[0] = String.valueOf(issue.getId());
            for (Book book : bookRepository.getAllBooks()) {
                if (issue.getBookId() == book.getId()) {
                    issueArray[1] = book.getName();
                    break;
                }
            }
            if (issueArray[1].isEmpty()) {
                issueArray[1] = String.valueOf(issue.getBookId());
            }
            for (Reader reader : readerRepository.getAllReaders()) {
                if (issue.getReaderId() == reader.getId()) {
                    issueArray[2] = reader.getName();
                    break;
                }
            }
            if (issueArray[2].isEmpty()) {
                issueArray[2] = String.valueOf(issue.getReaderId());
            }
            issueArray[3] = String.valueOf(issue.getIssuedAt());
            if (issue.getReturnedAt() == null) {
                issueArray[4] = "";
            } else {
                issueArray[4] = String.valueOf(issue.getReturnedAt());
            }
            result.add(issueArray);
        }
        return result;
    }
}
