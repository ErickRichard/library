package br.com.uol.library.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Title", example = "Book title description")
    private String title;
    @NotNull(message = "ISBN is mandatory")
    @NotEmpty(message = "ISBN cannot be empty")
    @Schema(description = "ISBN", example = "123456789")
    private String isbn;
    @NotNull(message = "Publication Date is mandatory")
    @Schema(description = "PublicationDate", example = "2024-11-01")
    private LocalDate publicationDate;
    @NotNull(message = "Author ID is mandatory")
    @Schema(description = "AuthorId", example = "565fcb1a-bc03-4938-833e-3563b8642b6e")
    private UUID authorId;
}