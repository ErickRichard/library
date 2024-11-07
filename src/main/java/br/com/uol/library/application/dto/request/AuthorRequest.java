package br.com.uol.library.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequest {
    @NotNull(message = "Name is mandatory")
    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "Name", example = "User")
    private String name;
    @NotNull(message = "Birth Date is mandatory")
    @Schema(description = "BirthDate", example = "2024-11-01")
    private LocalDate birthDate;
    @NotNull(message = "Nationality is mandatory")
    @NotEmpty(message = "Nationality cannot be empty")
    @Schema(description = "Nationality", example = "Brazilian")
    private String nationality;
}