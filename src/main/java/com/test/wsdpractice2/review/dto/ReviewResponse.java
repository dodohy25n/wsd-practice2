package com.test.wsdpractice2.review.dto;

import com.test.wsdpractice2.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {
    private Long reviewId;
    private Long bookId;
    private String bookTitle;
    private String content;
    private Integer rating;

    public static ReviewResponse of(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getId())
                .bookId(review.getBook().getId())
                .bookTitle(review.getBook().getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }
}
