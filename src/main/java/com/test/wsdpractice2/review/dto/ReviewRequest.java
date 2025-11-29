package com.test.wsdpractice2.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class ReviewRequest {

    @Getter
    public static class Create {
        @NotNull(message = "도서 ID는 필수입니다.")
        private Long bookId;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        private String content;

        @Min(value = 1, message = "평점은 최소 1점이어야 합니다.")
        @Max(value = 5, message = "평점은 최대 5점이어야 합니다.")
        private Integer rating;
    }

    @Getter
    public static class Update {
        @NotBlank(message = "수정할 내용은 필수입니다.")
        private String content;

        @Min(value = 1, message = "평점은 최소 1점이어야 합니다.")
        @Max(value = 5, message = "평점은 최대 5점이어야 합니다.")
        private Integer rating;
    }
}
