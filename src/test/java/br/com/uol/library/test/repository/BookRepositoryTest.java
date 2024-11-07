package br.com.uol.library.test.repository;

import br.com.uol.library.domain.model.Author;
import br.com.uol.library.domain.model.Book;
import br.com.uol.library.domain.repository.AuthorRepository;
import br.com.uol.library.domain.repository.BookRepository;
import br.com.uol.library.test.mocks.Mocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Transactional
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = Mocks.createAuthorEntity();
        author = authorRepository.save(author);
        book = Mocks.createBookEntity();
        book.setIsbn("123-4567890123");
        book.setAuthor(author);
    }

    @Test
    void BookExistsByIsbn() {
        bookRepository.save(book);
        boolean exists = bookRepository.existsByIsbn("123-4567890123");
        assertTrue(exists);
    }

    @Test
    void BooksByAuthorId() {
        bookRepository.save(book);
        Set<Book> books = bookRepository.findByAuthorId(author.getId());
        assertEquals(1, books.size());
    }

    @Test
    void BooksForNonExistentAuthor() {
        UUID nonExistentAuthorId = UUID.randomUUID();
        Set<Book> books = bookRepository.findByAuthorId(nonExistentAuthorId);
        assertTrue(books.isEmpty());
    }
}