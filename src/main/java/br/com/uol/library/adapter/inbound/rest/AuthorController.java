package br.com.uol.library.adapter.inbound.rest;

import br.com.uol.library.application.dto.request.AuthorRequest;
import br.com.uol.library.application.dto.request.AuthorUpdateRequest;
import br.com.uol.library.application.dto.response.AuthorResponse;
import br.com.uol.library.application.port.AuthorServicePort;
import br.com.uol.library.domain.common.utils.ApiResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/authors")
public class AuthorController extends BaseController {

    @Autowired
    AuthorServicePort authorServicePort;

    @ApiResponseWrapper(successDescription = "Authors fetched successfully", errorDescription = "Failed to fetch authors")
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> findAllAuthors() {
        return ResponseEntity.ok(authorServicePort.findAll());
    }

    @ApiResponseWrapper(successDescription = "Author details fetched", errorDescription = "Failed to fetch author")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> findByAuthorId(@PathVariable UUID id) {
        return ResponseEntity.ok(authorServicePort.findById(id));
    }

    @ApiResponseWrapper(successDescription = "Author created successfully", errorDescription = "Failed to create author")
    @PostMapping
    public ResponseEntity<AuthorResponse> includeAuthor(@RequestBody @Valid AuthorRequest authorRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorServicePort.save(authorRequest));
    }

    @ApiResponseWrapper(successDescription = "Author updated successfully", errorDescription = "Failed to update author")
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable UUID id, @RequestBody AuthorUpdateRequest authorUpdateRequest) {
        return ResponseEntity.ok(authorServicePort.update(authorUpdateRequest));
    }

    @ApiResponseWrapper(successDescription = "Author deleted successfully", errorDescription = "Failed to delete author")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        authorServicePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}