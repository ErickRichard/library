package br.com.uol.library.domain.repository;

import br.com.uol.library.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsByIsbn(String isbn);
    Set<Book> findByAuthorId(UUID uuid);
}