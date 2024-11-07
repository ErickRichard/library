package br.com.uol.library.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false)
    private String nationality;
}