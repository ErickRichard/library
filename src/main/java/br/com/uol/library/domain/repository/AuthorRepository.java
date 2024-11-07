package br.com.uol.library.domain.repository;

import br.com.uol.library.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {

}
