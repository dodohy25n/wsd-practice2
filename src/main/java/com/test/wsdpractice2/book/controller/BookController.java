package com.test.wsdpractice2.book.controller;

import com.test.wsdpractice2.book.dto.BookRequest;
import com.test.wsdpractice2.book.dto.BookResponse;
import com.test.wsdpractice2.book.service.BookService;
import com.test.wsdpractice2.common.response.ApiResponse;
import com.test.wsdpractice2.common.response.PageResponse;
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
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    // 도서 등록
    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody BookRequest.Create request) {
        BookResponse bookResponse = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("201", "도서 등록 성공", bookResponse));
    }
    
    // 도서 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BookResponse>>> getAllBooks(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<BookResponse> response = bookService.getAllBooks(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("200", "도서 목록 조회 성공", response));
    }
    
    // 도서 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBook(@PathVariable Long id) {
        BookResponse response = bookService.getBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("200", "도서 상세 조회 성공", response));
    }

    // 도서 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable Long id, @RequestBody BookRequest.Update request) {
        BookResponse response = bookService.updateBook(id, request);
        return ResponseEntity.ok(ApiResponse.success("200", "도서 수정 성공", response));
    }
    
    // 도서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("200", "도서 삭제 성공", null));
    }}
