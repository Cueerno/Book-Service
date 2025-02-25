package com.radiuk.book_storage_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private String isbn;

    private String title;

    private String genre;

    private String description;

    private String author;
}
