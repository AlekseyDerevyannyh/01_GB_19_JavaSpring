package ru.gb.repository;

import org.springframework.stereotype.Repository;
import ru.gb.model.Issue;

import java.util.ArrayList;
import java.util.List;

@Repository
public class IssueRepository {
    private final List<Issue> issues;

    public IssueRepository() {
        this.issues = new ArrayList<>();
    }

    public void save(Issue issue) {
        // insert into ....
        issues.add(issue);
    }

    public Issue getIssueById(long id) {
        return issues.stream()
                .filter(issue -> issue.getId() == id)
                .findFirst()
                .orElse(null);
    }

//    public Boolean isIssuesContainReaderById(long id) {
//        for (Issue issue : issues) {
//            if (issue.getReaderId() == id) {
//                return true;
//            }
//        }
//        return false;
//    }

    public int getCountBooksByReader(long readerId) {
        int count = 0;
        for (Issue issue : issues) {
            if (issue.getReaderId() == readerId) {
                count++;
            }
        }
        return count;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public Boolean returnBook(long issueId) {
        for (Issue issue : issues) {
            if (issue.getId() == issueId) {
                return issue.returnBook();
            }
        }
        return false;
    }
}
