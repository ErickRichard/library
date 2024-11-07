package br.com.uol.library.application.port;

import br.com.uol.library.application.dto.request.BookRequest;
import br.com.uol.library.application.dto.request.BookUpdateRequest;
import br.com.uol.library.application.dto.response.BookResponse;
import br.com.uol.library.domain.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookServicePort {
    List<BookResponse> findAll();
    BookResponse findById(UUID id);
    BookResponse save(BookRequest bookRequest);
    BookResponse update(BookUpdateRequest bookUpdateRequest);
    void delete(UUID id);
}