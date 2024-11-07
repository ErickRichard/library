package br.com.uol.library.test.service;

import br.com.uol.library.application.dto.request.BookRequest;
import br.com.uol.library.application.dto.request.BookUpdateRequest;
import br.com.uol.library.application.service.AuthorService;
import br.com.uol.library.application.service.BookService;
import br.com.uol.library.domain.common.enumeration.MessageKey;
import br.com.uol.library.domain.common.exception.CoreRuleException;
import br.com.uol.library.domain.common.mapper.LibraryMapper;
import br.com.uol.library.domain.model.Book;
import br.com.uol.library.domain.repository.AuthorRepository;
import br.com.uol.library.domain.repository.BookRepository;
import br.com.uol.library.test.mocks.Mocks;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private static final UUID UUID_BOOK = UUID.randomUUID();

    @InjectMocks
    private BookService bookService;
    @Mock
    private AuthorService authorService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private LibraryMapper libraryMapper;

    private Book book;
    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        book = Mocks.createBookEntity();
        bookRequest = Mocks.createBookRequest();
    }

    @Test
    void shouldReturnAllBooks() {
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book));
        var response = bookService.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Mockito.verify(bookRepository).findAll();
    }

    @Test
    void shouldReturnBookResponseWhenBookExists() {
        Mockito.when(bookRepository.findById(UUID_BOOK)).thenReturn(Optional.of(book));
        Mockito.when(libraryMapper.toBookResponse(book)).thenReturn(Mocks.createBookResponse());
        var bookResponse = bookService.findById(UUID_BOOK);
        Assertions.assertNotNull(bookResponse);
        Assertions.assertEquals(book.getIsbn(), bookResponse.getIsbn());
        Mockito.verify(bookRepository).findById(UUID_BOOK);
    }

    @Test
    void shouldSaveBook() {
        Mockito.when(authorService.findById(Mockito.any())).thenReturn(Mocks.createAuthorResponse());
        Mockito.when(libraryMapper.toBookEntity(Mockito.any(), Mockito.any())).thenReturn(new Book());
        Mockito.when(libraryMapper.toBookResponse(Mockito.any())).thenReturn(Mocks.createBookResponse());
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(new Book());
        var response = bookService.save(Mocks.createBookRequest());
        Assertions.assertNotNull(response);
        Mockito.verify(bookRepository).save(Mockito.any());
    }

    @Test
    void shouldUpdateBook() {
        var bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setId(UUID_BOOK);
        bookUpdateRequest.setTitle("New Book");
        bookUpdateRequest.setPublicationDate(LocalDate.now());
        var existingBook = Mocks.createBookEntity();
        Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(Optional.of(existingBook));
        Mockito.when(libraryMapper.toBookResponse(Mockito.any())).thenReturn(Mocks.createBookResponse());
        var response = bookService.update(bookUpdateRequest);
        Assertions.assertNotNull(response);
        Mockito.verify(bookRepository).findById(Mockito.any());
        Mockito.verify(libraryMapper).toUpdateBook(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldDeleteBook() {
        Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(Optional.of(Mocks.createBookEntity()));
        bookService.delete(UUID_BOOK);
        Mockito.verify(bookRepository).delete(Mockito.any(Book.class));
    }

    @Test
    void shouldThrowExceptionNoBooksFound() {
        Mockito.when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            bookService.findAll();
        });
        Assertions.assertEquals(MessageKey.NO_BOOKS_FOUND.getCode(), exception.getMessageError().getCode());
        Mockito.verify(bookRepository).findAll();
    }

    @Test
    void shouldThrowExceptionBookNotFound() {
        Mockito.when(bookRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            bookService.delete(UUID_BOOK);
        });
        Assertions.assertEquals(MessageKey.BOOK_NOT_FOUND.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldThrowExceptionPublicationDate() {
        var futureDate = LocalDate.now().plusDays(1);
        bookRequest.setPublicationDate(futureDate);
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            bookService.save(bookRequest);
        });
        Assertions.assertEquals(MessageKey.PUBLICATION_DATE_IN_FUTURE.getCode(), exception.getMessageError().getCode());
        Mockito.verify(bookRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldThrowExceptionIsbnAlreadyExists() {
        String existingIsbn = "1234567890";
        bookRequest.setIsbn(existingIsbn);
        Mockito.when(bookRepository.existsByIsbn(existingIsbn)).thenReturn(true);
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            bookService.save(bookRequest);
        });
        Assertions.assertEquals(MessageKey.DUPLICATE_ISBN.getCode(), exception.getMessageError().getCode());
        Mockito.verify(bookRepository, Mockito.never()).save(Mockito.any());
    }
}
