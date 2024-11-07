package br.com.uol.library.application.dto.request;

import br.com.uol.library.domain.common.utils.WithId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorUpdateRequest extends AuthorRequest implements WithId {
    @Schema(hidden = true)
    private UUID id;

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
