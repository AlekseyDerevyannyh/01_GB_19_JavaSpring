package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.model.Issue;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
//    List<Issue> getAllIssues();
//    Issue getIssueById(Long id);
//    Issue addIssue(Issue issue);
    Long countBooksByReaderId(Long readerId);
}
