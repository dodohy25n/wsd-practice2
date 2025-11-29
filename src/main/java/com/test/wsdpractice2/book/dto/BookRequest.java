package com.test.wsdpractice2.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class BookRequest {

    @Getter
    public static class Create {
        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "저자는 필수입니다.")
        private String author;

        @NotBlank(message = "ISBN은 필수입니다.")
        private String isbn;
    }

    @Getter
    public static class Update {

        private String title;

        private String author;

        private String isbn;
    }
}
