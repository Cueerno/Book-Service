package com.radiuk.book_tracker_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radiuk.book_storage_service.client.BookTrackerClient;
import com.radiuk.book_storage_service.client.BookTrackerRequest;
import com.radiuk.book_storage_service.controller.BookController;
import com.radiuk.book_storage_service.dto.BookDTO;
import com.radiuk.book_storage_service.model.Book;
import com.radiuk.book_storage_service.service.BookService;
import com.radiuk.book_tracker_service.service.BookTrackerService;
import com.radiuk.book_tracker_service.service.impl.BookTrackerServiceImpl;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookTrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookTrackerServiceImpl bookTrackerService;

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
    public void getAvailableBooksTest() throws Exception {
        List<Book> books = Arrays.asList(book1, book2);
        when(bookTrackerService.findAvailableBooks()).thenReturn(books);

        mockMvc.perform(get("/api/book/tracker/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void newEntryFromBookStorageTest() throws Exception {
        BookTrackerRequest bookTrackerRequest = new BookTrackerRequest(book1.getId());
        doNothing().when(bookTrackerService).create(bookTrackerRequest.getBookId());

        mockMvc.perform(post("/api/book/tracker/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void borrowBookTest() throws Exception {
        doNothing().when(bookTrackerService).borrow(book1.getId());

        mockMvc.perform(patch("/api/book/tracker/{id}/borrow", book1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void returnBookTest() throws Exception {
        doNothing().when(bookTrackerService).returnBook(book1.getId());

        mockMvc.perform(patch("/api/book/tracker/{id}/return", book1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBookTest() throws Exception {
        doNothing().when(bookTrackerService).delete(book1.getId());

        mockMvc.perform(delete("/api/book/tracker/{id}/delete", book1.getId()))
                .andExpect(status().isOk());
    }
}
