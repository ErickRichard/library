package br.com.uol.library.application.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class BookResponse {
    private UUID id;
    private String title;
    private String isbn;
    private LocalDate publicationDate;
    private AuthorResponse author;
}