package br.com.uol.library.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    @NotNull(message = "Title is mandatory")
    private String title;
    @NotNull(message = "ISBN is mandatory")
    @NotEmpty(message = "ISBN cannot be empty")
    private String isbn;
    @NotNull(message = "Publication Date is mandatory")
    private LocalDate publicationDate;
    @NotNull(message = "Author ID is mandatory")
    private UUID authorId;
}