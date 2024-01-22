package ru.gb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.gb.model.User;

import java.util.List;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

  @Query(nativeQuery = true, value = "SELECT * FROM USERS") // select u from Users u
  List<Object[]> myquery2();

  // jql
  @Query("select u from User u where u.name = :myvalue")
  List<User> myquery(String myvalue);

  List<User> findByNameOrAge(String name, Integer age);

  Page<User> findByAgeGreaterThan(Pageable pageable, int value);

  List<User> findTop3ByAgeGreaterThanOrderByAgeDesc(int value);

}
