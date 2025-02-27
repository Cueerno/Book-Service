package com.radiuk.book_storage_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "book-tracker-service", url = "http://localhost:7070")
public interface BookTrackerClient {

    @PostMapping("/api/tracker/books")
    void createBookTracker(BookTrackerRequest request);

    @DeleteMapping("/api/tracker/books/{id}")
    void deleteBookTracker(@PathVariable int id);
}