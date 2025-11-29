package com.test.wsdpractice2.review.entity;

import com.test.wsdpractice2.book.dto.BookRequest;
import com.test.wsdpractice2.book.entity.Book;
import com.test.wsdpractice2.review.dto.ReviewRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String content;

    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public static Review of(ReviewRequest.Create request, Book book) {
        Review review = new Review();
        review.content = request.getContent();
        review.rating = request.getRating();
        review.book = book;
        return review;
    }

    public void update(ReviewRequest.Update request) {
        this.content = request.getContent();
        this.rating = request.getRating();
    }
}
