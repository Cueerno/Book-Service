package com.radiuk.book_storage_service.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radiuk.book_storage_service.client.BookTrackerClient;
import com.radiuk.book_storage_service.dto.BookDTO;
import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_storage_service.service.BookService;
import com.radiuk.book_storage_service.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookServiceImpl bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book1;
    private Book book2;

    private BookDTO bookDTO;

    @BeforeEach
    public void createBooks() {
        book1 = new Book(1, "123456789", "Book 1", "Fiction", "Description 1", "Author 1");
        book2 = new Book(2, "987654321", "Book 2", "Non-fiction", "Description 2", "Author 2");

        bookDTO = new BookDTO("123456789", "Book 1", "Fiction", "Description 1", "Author 1");
    }

    @Test
    public void getAllBooksTest() throws Exception {
        List<Book> books = Arrays.asList(book1, book2);
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/book/storage/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getBookByIdTest() throws Exception {
        when(bookService.findById(1)).thenReturn(book1);

        mockMvc.perform(get("/api/book/storage/{id}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void getBookByIsbnTest() throws Exception {
        when(bookService.findByIsbn("123456789")).thenReturn(book1);

        mockMvc.perform(get("/api/book/storage/isbn/123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("123456789"));
    }

    @Test
    public void createBookTest() throws Exception {
        doNothing().when(bookService).create(any(Book.class));

        mockMvc.perform(post("/api/book/storage/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateBookTest() throws Exception {
        int id = 1;
        doNothing().when(bookService).update(any(Book.class), eq(id));

        mockMvc.perform(patch("/api/book/storage/{id}/update", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookTest() throws Exception {
        int id = 1;
        when(bookService.findById(id)).thenReturn(book1);

        mockMvc.perform(delete("/api/book/storage/{id}/delete", id))
                .andExpect(status().isOk());
    }
}
