package br.com.uol.library.adapter.inbound.rest;

import br.com.uol.library.domain.common.utils.WithId;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Objects;
import java.util.UUID;

public abstract class BaseController {

    @InitBinder
    public void initBinder(WebDataBinder binder, @PathVariable(required = false) UUID id) {
        Object target = binder.getTarget();
        if (target instanceof WithId withId && Objects.nonNull(id)) {
            withId.setId(id);
        }
    }
}