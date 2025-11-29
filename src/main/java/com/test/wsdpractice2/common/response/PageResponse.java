package com.test.wsdpractice2.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class PageResponse<T> {

    private List<T> data;
    private Meta meta;
    private Links links;

    public static <T> PageResponse<T> of(Page<T> pageData) {
        return PageResponse.<T>builder()
                .data(pageData.getContent())
                .meta(Meta.builder()
                        .total((int) pageData.getTotalElements())
                        .page(pageData.getNumber() + 1)
                        .perPage(pageData.getSize())
                        .totalPages(pageData.getTotalPages())
                        .build())
                .links(Links.builder()
                        .self("?page=" + (pageData.getNumber() + 1) + "&size=" + pageData.getSize())
                        .next(pageData.hasNext() ? "?page=" + (pageData.getNumber() + 2) + "&size=" + pageData.getSize() : null)
                        .prev(pageData.hasPrevious() ? "?page=" + (pageData.getNumber()) + "&size=" + pageData.getSize() : null)
                        .build())
                .build();
    }

    @Getter
    @Builder
    public static class Meta {
        private int total;
        private int page;
        private int perPage;
        private int totalPages;
    }

    @Getter
    @Builder
    public static class Links {
        private String self;
        private String next;
        private String prev;
    }

}
