package com.test.wsdpractice2.book.service;

import com.test.wsdpractice2.book.dto.BookRequest;
import com.test.wsdpractice2.book.dto.BookResponse;
import com.test.wsdpractice2.book.entity.Book;
import com.test.wsdpractice2.book.repository.BookRepository;
import com.test.wsdpractice2.common.exception.CustomException;
import com.test.wsdpractice2.common.exception.ErrorCode;
import com.test.wsdpractice2.common.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookResponse createBook(@Valid BookRequest.Create request) {

        boolean isExist = bookRepository.existsBookByIsbn(request.getIsbn());
        if (isExist) {
            throw new CustomException(ErrorCode.DUPLICATE_BOOK_ISBN);
        }

        Book savedBook = bookRepository.save(Book.of(request));
        return BookResponse.of(savedBook);
    }

    public PageResponse<BookResponse> getAllBooks(Pageable pageable) {

        Page<Book> bookPage = bookRepository.findAll(pageable);

        Page<BookResponse> responsePage = bookPage.map(BookResponse::of);

        return PageResponse.of(responsePage);
    }

    public BookResponse getBook(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        return BookResponse.of(book);
    }


    public BookResponse updateBook(Long id, BookRequest.Update request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsBookByIsbn(request.getIsbn())) {
            throw new CustomException(ErrorCode.DUPLICATE_BOOK_ISBN);
        }

        book.update(request);

        return BookResponse.of(book);
    }
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        bookRepository.delete(book);
    }

}
