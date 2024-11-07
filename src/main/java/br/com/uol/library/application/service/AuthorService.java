package br.com.uol.library.application.service;

import br.com.uol.library.application.dto.request.AuthorRequest;
import br.com.uol.library.application.dto.request.AuthorUpdateRequest;
import br.com.uol.library.application.dto.response.AuthorResponse;
import br.com.uol.library.application.port.AuthorServicePort;
import br.com.uol.library.domain.common.enumeration.MessageKey;
import br.com.uol.library.domain.common.exception.CoreRuleException;
import br.com.uol.library.domain.common.mapper.LibraryMapper;
import br.com.uol.library.domain.common.utils.MessageResponse;
import br.com.uol.library.domain.model.Author;
import br.com.uol.library.domain.repository.AuthorRepository;
import br.com.uol.library.domain.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AuthorService implements AuthorServicePort {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final LibraryMapper libraryMapper;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, LibraryMapper libraryMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.libraryMapper = libraryMapper;
    }

    @Override
    public List<AuthorResponse> findAll() {
        log.info("[findAll] - ini");
        var authors = authorRepository.findAll();
        log.info("[findAll] - end");
        if (authors.isEmpty()) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.NO_AUTHORS_FOUND));
        }
        return authors.stream().map(libraryMapper::toAuthorResponse).toList();
    }

    @Override
    public AuthorResponse findById(UUID id) {
        log.info("[findById] - ini, author ID: {}", id);
        var author = getAuthor(id);
        log.info("[findById] - end");
        return libraryMapper.toAuthorResponse(author);
    }

    @Override
    @Transactional
    public AuthorResponse save(AuthorRequest authorRequest) {
        try {
            log.info("[save] - ini, author name: {}", authorRequest.getName());
            var author = authorRepository.save(libraryMapper.toAuthorEntity(authorRequest));
            log.info("[save] - end");
            return libraryMapper.toAuthorResponse(author);
        } catch (CoreRuleException e) {
            throw e;
        } catch (Exception e) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.FAIL_TO_SAVE_AUTHOR));
        }
    }

    @Override
    @Transactional
    public AuthorResponse update(AuthorUpdateRequest authorUpdateRequest) {
        try {
            log.info("[update] - ini, author ID: {}", authorUpdateRequest.getId());
            var author = getAuthor(authorUpdateRequest.getId());
            libraryMapper.toUpdateAuthor(author, authorUpdateRequest);
            authorRepository.saveAndFlush(author);
            log.info("[update] - end");
            return libraryMapper.toAuthorResponse(author);
        } catch (CoreRuleException e) {
            throw e;
        } catch (Exception e) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.FAIL_TO_UPDATE_AUTHOR));
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("[delete] - ini, author ID: {}", id);
        var books = bookRepository.findByAuthorId(id);
        if (!books.isEmpty()) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.AUTHOR_HAS_BOOKS));
        }
        var author = getAuthor(id);
        authorRepository.delete(author);
        log.info("[delete] - end");
    }

    private Author getAuthor(UUID id) {
        log.info("[getAuthor] - ini, author ID: {}", id);
        Author author = authorRepository.findById(id).orElseThrow(() -> new CoreRuleException(MessageResponse.error(MessageKey.AUTHOR_NOT_FOUND)));
        log.info("[getAuthor] - end");
        return author;
    }
}