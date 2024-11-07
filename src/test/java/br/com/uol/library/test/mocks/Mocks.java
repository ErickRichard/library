package br.com.uol.library.test.mocks;

import br.com.uol.library.application.dto.request.AuthorRequest;
import br.com.uol.library.application.dto.request.AuthorUpdateRequest;
import br.com.uol.library.application.dto.request.BookRequest;
import br.com.uol.library.application.dto.response.AuthorResponse;
import br.com.uol.library.application.dto.response.BookResponse;
import br.com.uol.library.domain.model.Author;
import br.com.uol.library.domain.model.Book;

import java.time.LocalDate;
import java.util.UUID;

public class Mocks {

    public static AuthorRequest createAuthorRequest() {
        return AuthorRequest.builder()
                .name("John Doe")
                .birthDate(LocalDate.now())
                .nationality("American")
                .build();
    }

    public static AuthorRequest createAuthorUpdateRequest() {
        return AuthorUpdateRequest.builder()
                .name("John Doe")
                .birthDate(LocalDate.now())
                .nationality("American")
                .build();
    }

    public static AuthorResponse createAuthorResponse() {
        return AuthorResponse.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .birthDate(LocalDate.now())
                .nationality("American")
                .build();
    }

    public static BookRequest createBookRequest() {
        return BookRequest.builder()
                .title("Sample Book Title")
                .isbn("123-4567890123")
                .publicationDate(LocalDate.now())
                .authorId(UUID.randomUUID())
                .build();
    }

    public static BookResponse createBookResponse() {
        return BookResponse.builder()
                .id(UUID.randomUUID())
                .title("Sample Book Title")
                .isbn("123-4567890123")
                .publicationDate(LocalDate.now())
                .author(createAuthorResponse())
                .build();
    }

    public static BookRequest createBookUpdateRequest() {
        return BookRequest.builder()
                .title("Sample Book Title")
                .isbn("123-4567890123")
                .publicationDate(LocalDate.now())
                .authorId(UUID.randomUUID())
                .build();
    }

    public static Author createAuthorEntity() {
        return Author.builder()
                .id(UUID.fromString("32378d2f-d88e-4a30-84c3-563ab3e8bd36"))
                .name("John Doe")
                .birthDate(LocalDate.now())
                .nationality("American")
                .build();
    }

    public static Book createBookEntity() {
        return Book.builder()
                .id(UUID.randomUUID())
                .title("Sample Book Title")
                .isbn("123-4567890123")
                .publicationDate(LocalDate.now())
                .author(createAuthorEntity())
                .build();
    }
}