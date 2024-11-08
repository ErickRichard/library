package br.com.uol.library.application.port;

import br.com.uol.library.application.dto.request.AuthorRequest;
import br.com.uol.library.application.dto.request.AuthorUpdateRequest;
import br.com.uol.library.application.dto.response.AuthorResponse;

import java.util.List;
import java.util.UUID;

public interface AuthorServicePort {
    List<AuthorResponse> findAll();
    AuthorResponse findById(UUID id);
    AuthorResponse save(AuthorRequest authorRequest);
    AuthorResponse update(AuthorUpdateRequest authorUpdateRequest);
    void delete(UUID id);
}
