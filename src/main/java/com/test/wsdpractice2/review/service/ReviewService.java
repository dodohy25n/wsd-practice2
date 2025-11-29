package com.test.wsdpractice2.review.service;

import com.test.wsdpractice2.book.entity.Book;
import com.test.wsdpractice2.book.repository.BookRepository;
import com.test.wsdpractice2.common.exception.CustomException;
import com.test.wsdpractice2.common.exception.ErrorCode;
import com.test.wsdpractice2.common.response.PageResponse;
import com.test.wsdpractice2.review.dto.ReviewRequest;
import com.test.wsdpractice2.review.dto.ReviewResponse;
import com.test.wsdpractice2.review.entity.Review;
import com.test.wsdpractice2.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewResponse createReview(ReviewRequest.Create request) {
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
        Review savedReview = reviewRepository.save(Review.of(request, book));
        return ReviewResponse.of(savedReview);
    }

    public PageResponse<ReviewResponse> getAllReviews(Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAll(pageable);
        return PageResponse.of(reviewPage.map(ReviewResponse::of));
    }

    public ReviewResponse updateReview(Long reviewId, ReviewRequest.Update request) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        review.update(request);
        return ReviewResponse.of(review);
    }

    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        reviewRepository.delete(review);
    }
}