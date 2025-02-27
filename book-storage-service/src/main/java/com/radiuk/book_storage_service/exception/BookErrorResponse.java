package com.radiuk.book_storage_service.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookErrorResponse {
    private String message;

    private LocalDateTime messageTime;

    public BookErrorResponse(String message, LocalDateTime messageTime) {
        this.message = message;
        this.messageTime = messageTime;
    }
}
