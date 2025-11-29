package com.test.wsdpractice2.book.repository;

import com.test.wsdpractice2.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsBookByIsbn(String isbn);
}
