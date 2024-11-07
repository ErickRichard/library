package br.com.uol.library.application.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class AuthorResponse {
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private String nationality;
}