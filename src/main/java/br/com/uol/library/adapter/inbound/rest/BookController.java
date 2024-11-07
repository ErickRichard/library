package br.com.uol.library.adapter.inbound.rest;

import br.com.uol.library.application.dto.request.BookRequest;
import br.com.uol.library.application.dto.request.BookUpdateRequest;
import br.com.uol.library.application.dto.response.BookResponse;
import br.com.uol.library.application.port.BookServicePort;
import br.com.uol.library.domain.common.utils.ApiResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/books")
public class BookController extends BaseController {

    @Autowired
    BookServicePort bookServicePort;

    @ApiResponseWrapper(successDescription = "Book fetched successfully", errorDescription = "Failed to fetch books")
    @GetMapping
    public ResponseEntity<List<BookResponse>> findAllAuthors() {
        return ResponseEntity.ok(bookServicePort.findAll());
    }

    @ApiResponseWrapper(successDescription = "Book details fetched", errorDescription = "Failed to fetch book")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> findByBookId(@PathVariable UUID id) {
        return ResponseEntity.ok(bookServicePort.findById(id));
    }

    @ApiResponseWrapper(successDescription = "Book created successfully", errorDescription = "Failed to create book")
    @PostMapping
    public ResponseEntity<BookResponse> includeBook(@RequestBody @Valid BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookServicePort.save(bookRequest));
    }

    @ApiResponseWrapper(successDescription = "Book updated successfully", errorDescription = "Failed to update book")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable UUID id, @RequestBody BookUpdateRequest bookUpdateRequest) {
        return ResponseEntity.ok(bookServicePort.update(bookUpdateRequest));
    }

    @ApiResponseWrapper(successDescription = "Book deleted successfully", errorDescription = "Failed to delete book")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookServicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
