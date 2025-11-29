package com.test.wsdpractice2.book.dto;

import com.test.wsdpractice2.book.entity.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class BookResponse {
    Long bookId;
    String title;
    String author;
    String isbn;

    public static BookResponse of(Book book) {
        return BookResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .build();
    }
}
