package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.model.Reader;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
//    List<Reader> getAllReaders();
//    Reader getReaderById(Long id);
//    Reader addReader(Reader reader);
//    void deleteReader(Long id);
}
