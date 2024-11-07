package br.com.uol.library.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, unique = true)
    private String isbn;
    @Column
    private LocalDate publicationDate;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}