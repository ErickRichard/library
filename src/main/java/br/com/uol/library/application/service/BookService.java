package br.com.uol.library.application.service;

import br.com.uol.library.application.dto.request.BookRequest;
import br.com.uol.library.application.dto.request.BookUpdateRequest;
import br.com.uol.library.application.dto.response.BookResponse;
import br.com.uol.library.application.port.AuthorServicePort;
import br.com.uol.library.application.port.BookServicePort;
import br.com.uol.library.domain.common.enumeration.MessageKey;
import br.com.uol.library.domain.common.exception.CoreRuleException;
import br.com.uol.library.domain.common.mapper.LibraryMapper;
import br.com.uol.library.domain.common.utils.MessageResponse;
import br.com.uol.library.domain.model.Book;
import br.com.uol.library.domain.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BookService implements BookServicePort {

    private final BookRepository bookRepository;
    private final AuthorServicePort authorServicePort;
    private final LibraryMapper libraryMapper;

    public BookService(BookRepository bookRepository, AuthorServicePort authorServicePort, LibraryMapper libraryMapper) {
        this.bookRepository = bookRepository;
        this.authorServicePort = authorServicePort;
        this.libraryMapper = libraryMapper;
    }

    @Override
    public List<BookResponse> findAll() {
        log.info("[findAll] - ini");
        var books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.NO_BOOKS_FOUND));
        }
        log.info("[findAll] - end");
        return books.stream().map(libraryMapper::toBookResponse).toList();
    }

    @Override
    public BookResponse findById(UUID id) {
        log.info("[findById] - ini, book ID: {}", id);
        var book = getBook(id);
        log.info("[findById] - end");
        return libraryMapper.toBookResponse(book);
    }

    @Override
    @Transactional
    public BookResponse save(BookRequest bookRequest) {
        try {
            log.info("[save] - ini, book ISBN: {}", bookRequest.getIsbn());
            validateDuplicateIsbn(bookRequest.getIsbn());
            validatePublicationDate(bookRequest.getPublicationDate());
            var author = libraryMapper.toAuthor(authorServicePort.findById(bookRequest.getAuthorId()));
            var entity = libraryMapper.toBookEntity(bookRequest, author);
            var book = bookRepository.save(entity);
            log.info("[save] - end");
            return libraryMapper.toBookResponse(book);
        } catch (CoreRuleException e) {
            throw e;
        } catch (Exception e) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.FAIL_TO_SAVE_BOOK));
        }
    }

    @Override
    @Transactional
    public BookResponse update(BookUpdateRequest bookUpdateRequest) {
        try {
            log.info("[update] - ini, book ID: {}", bookUpdateRequest.getId());
            validateDuplicateIsbn(bookUpdateRequest.getIsbn());
            validatePublicationDate(bookUpdateRequest.getPublicationDate());
            var book = getBook(bookUpdateRequest.getId());
            libraryMapper.toUpdateBook(book, bookUpdateRequest);
            bookRepository.saveAndFlush(book);
            log.info("[update] - end");
            return libraryMapper.toBookResponse(book);
        } catch (CoreRuleException e) {
            throw e;
        } catch (Exception e) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.FAIL_TO_UPDATE_AUTHOR));
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("[delete] - ini, book ID: {}", id);
        var book = getBook(id);
        bookRepository.delete(book);
        log.info("[delete] - end");
    }

    private Book getBook(UUID id) {
        log.info("[getBook] - ini, book ID: {}", id);
        return bookRepository.findById(id).orElseThrow(() -> new CoreRuleException(MessageResponse.error(MessageKey.BOOK_NOT_FOUND)));
    }

    private void validatePublicationDate(LocalDate publicationDate) {
        log.info("[validatePublicationDate] - date: {}", publicationDate);
        if (publicationDate.isAfter(LocalDate.now())) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.PUBLICATION_DATE_IN_FUTURE));
        }
    }

    private void validateDuplicateIsbn(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new CoreRuleException(MessageResponse.error(MessageKey.DUPLICATE_ISBN));
        }
    }
}
