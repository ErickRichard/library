package br.com.uol.library.test.service;

import br.com.uol.library.application.dto.request.AuthorRequest;
import br.com.uol.library.application.dto.request.AuthorUpdateRequest;
import br.com.uol.library.application.dto.response.AuthorResponse;
import br.com.uol.library.application.service.AuthorService;
import br.com.uol.library.domain.common.enumeration.MessageKey;
import br.com.uol.library.domain.common.exception.CoreRuleException;
import br.com.uol.library.domain.common.mapper.LibraryMapper;
import br.com.uol.library.domain.model.Author;
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
class AuthorServiceTest {

    private static final UUID UUID_AUTHOR = UUID.randomUUID();

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private LibraryMapper libraryMapper;

    private Author author;
    private AuthorRequest authorRequest;
    private AuthorUpdateRequest authorUpdateRequest;

    @BeforeEach
    void setUp() {
        author = Mocks.createAuthorEntity();
        authorRequest = Mocks.createAuthorRequest();
        Mockito.reset(authorRepository, bookRepository, libraryMapper);
        authorUpdateRequest = new AuthorUpdateRequest();
        authorUpdateRequest.setId(UUID.randomUUID());
        authorUpdateRequest.setName("Compass author");
        authorUpdateRequest.setBirthDate(LocalDate.now());
        authorUpdateRequest.setNationality("Brazilian");
    }

    @Test
    void shouldReturnAuthors() {
        Mockito.when(authorRepository.findAll()).thenReturn(List.of(author));
        var response = authorService.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
        Mockito.verify(authorRepository).findAll();
    }

    @Test
    void shouldReturnAuthorWhenFound() {
        Mockito.when(authorRepository.findById(UUID_AUTHOR)).thenReturn(Optional.of(author));
        AuthorResponse authorResponse = new AuthorResponse(author.getId(), author.getName(), author.getBirthDate(), author.getNationality());
        Mockito.when(libraryMapper.toAuthorResponse(author)).thenReturn(authorResponse);
        AuthorResponse response = authorService.findById(UUID_AUTHOR);
        Assertions.assertEquals(author.getId(), response.getId());
        Mockito.verify(authorRepository).findById(UUID_AUTHOR);
        Mockito.verify(libraryMapper).toAuthorResponse(author);
    }

    @Test
    void shouldSaveAuthor() {
        Mockito.when(libraryMapper.toAuthorEntity(Mockito.any())).thenReturn(new Author());
        Mockito.when(libraryMapper.toAuthorResponse(Mockito.any())).thenReturn(new AuthorResponse());
        var response = authorService.save(authorRequest);
        Assertions.assertNotNull(response);
        Mockito.verify(authorRepository).save(Mockito.any());
    }

    @Test
    void shouldDeleteAuthor() {
        Mockito.when(bookRepository.findByAuthorId(Mockito.any())).thenReturn(Collections.emptySet());
        Mockito.when(authorRepository.findById(Mockito.any())).thenReturn(Optional.of(Mocks.createAuthorEntity()));
        authorService.delete(UUID_AUTHOR);
        Mockito.verify(authorRepository).delete(Mockito.any(Author.class));
    }

    @Test
    void shouldThrowExceptionWhenAuthorHasBooks() {
        Mockito.when(bookRepository.findByAuthorId(UUID_AUTHOR)).thenReturn(Collections.singleton(new Book()));
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            authorService.delete(UUID_AUTHOR);
        });
        Assertions.assertEquals(MessageKey.AUTHOR_HAS_BOOKS.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldThrowExceptionWhenAuthorNotFound() {
        Mockito.when(authorRepository.findById(UUID_AUTHOR)).thenReturn(Optional.empty());
        CoreRuleException exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            authorService.findById(UUID_AUTHOR);
        });
        Assertions.assertEquals(MessageKey.AUTHOR_NOT_FOUND.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldUpdateAuthor() {
        authorUpdateRequest.setId(UUID.randomUUID());
        authorUpdateRequest.setBirthDate(LocalDate.now().plusDays(1));
        Mockito.when(authorRepository.findById(Mockito.any())).thenReturn(Optional.of(author));
        Mockito.doNothing().when(libraryMapper).toUpdateAuthor(Mockito.any(), Mockito.any());
        Mockito.when(libraryMapper.toAuthorResponse(Mockito.any())).thenReturn(new AuthorResponse());
        var response = authorService.update(authorUpdateRequest);
        Assertions.assertNotNull(response);
        Mockito.verify(authorRepository).saveAndFlush(Mockito.any(Author.class));
    }

    @Test
    void shouldThrowExceptionNoAuthorsFound() {
        Mockito.when(authorRepository.findAll()).thenReturn(Collections.emptyList());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            authorService.findAll();
        });
        Assertions.assertEquals(MessageKey.NO_AUTHORS_FOUND.getCode(), exception.getMessageError().getCode());
    }

    @Test
    void shouldThrowExceptionUpdateAuthorNotFound() {
        Mockito.when(authorRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        var exception = Assertions.assertThrows(CoreRuleException.class, () -> {
            authorService.update(authorUpdateRequest);
        });
        Assertions.assertEquals(MessageKey.AUTHOR_NOT_FOUND.getCode(), exception.getMessageError().getCode());
    }
}