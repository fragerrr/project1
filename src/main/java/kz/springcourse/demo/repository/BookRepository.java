package kz.springcourse.demo.repository;

import kz.springcourse.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTookUser(Integer id);
}
