package com.test.wsdpractice2.book.entity;

import com.test.wsdpractice2.book.dto.BookRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private String isbn;

    public static Book of(BookRequest.Create bookRequest) {
        Book book = new Book();
        book.title = bookRequest.getTitle();
        book.author = bookRequest.getAuthor();
        book.isbn = bookRequest.getIsbn();
        return book;
    }

    public void update(BookRequest.Update request) {
        this.title = request.getTitle();
        this.author = request.getAuthor();
        this.isbn = request.getIsbn();
    }
}
