package com.test.wsdpractice2.review.controller;

import com.test.wsdpractice2.common.response.ApiResponse;
import com.test.wsdpractice2.common.response.PageResponse;
import com.test.wsdpractice2.review.dto.ReviewRequest;
import com.test.wsdpractice2.review.dto.ReviewResponse;
import com.test.wsdpractice2.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(@Valid @RequestBody ReviewRequest.Create request) {
        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("201", "리뷰 등록 성공", response));
    }

    // 리뷰 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponse>>> getAllReviews(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<ReviewResponse> response = reviewService.getAllReviews(pageable);
        return ResponseEntity.ok(ApiResponse.success("200", "리뷰 목록 조회 성공", response));
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewRequest.Update request) { // Update DTO는 @Valid 선택 사항
        ReviewResponse response = reviewService.updateReview(id, request);
        return ResponseEntity.ok(ApiResponse.success("200", "리뷰 수정 성공", response));
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(ApiResponse.success("200", "리뷰 삭제 성공", null));
    }
}
