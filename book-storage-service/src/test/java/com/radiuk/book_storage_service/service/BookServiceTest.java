package com.radiuk.book_storage_service.service;

import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_storage_service.repository.BookRepository;
import com.radiuk.book_storage_service.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;


    @BeforeEach
    public void createBook() {
        book = new Book(1, "11113311", "Book 1", "Fiction", "Description 1", "Author 1");
    }


    @Test
    public void createBookTest() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookService.create(book);

        verify(bookRepository).save(any(Book.class));
    }

    @Test
    public void findAllTest_ShouldBeNotEmpyty() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.findAll();

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTest_ShouldBeNotNull() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.ofNullable(book));

        Book bookToCheck = bookService.findById(book.getId());

        Assertions.assertThat(bookToCheck).isNotNull();
    }

    @Test
    public void findByIsbnTest_ShouldBeNotNull() {
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.ofNullable(book));

        Book bookToCheck = bookService.findByIsbn(book.getIsbn());

        Assertions.assertThat(bookToCheck).isNotNull();
    }

    @Test
    public void updateBookTest_ShouldBeEqualsToItself() {
        book.setTitle("Book new");
        bookService.update(book, 1);

        verify(bookRepository, times(1)).save(book);
        assertEquals(1, book.getId());
        assertEquals("Book new", book.getTitle());
    }
}
