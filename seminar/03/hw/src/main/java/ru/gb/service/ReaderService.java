package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.model.Issue;
import ru.gb.model.Reader;
import ru.gb.repository.IssueRepository;
import ru.gb.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

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
}
