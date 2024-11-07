package br.com.uol.library.application.dto.request;

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
    private String name;
    @NotNull(message = "Birth Date is mandatory")
    private LocalDate birthDate;
    @NotNull(message = "Nationality is mandatory")
    @NotEmpty(message = "Nationality cannot be empty")
    private String nationality;
}