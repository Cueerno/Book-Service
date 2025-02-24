package com.radiuk.book_storage_service.exception;

public class BookNotCreatedException extends RuntimeException {
    public BookNotCreatedException(String message) {
        super(message);
    }
}
