package com.radiuk.book_storage_service.client;


public class BookTrackerRequest {

    private Integer BookId;

    public BookTrackerRequest(Integer bookId) {
        BookId = bookId;
    }

    public Integer getBookId() {
        return BookId;
    }

    public void setBookId(Integer bookId) {
        BookId = bookId;
    }
}
