package br.com.uol.library.domain.common.exception;


import br.com.uol.library.domain.common.utils.MessageResponse;

import java.io.Serial;

public class CoreRuleException extends ValidationException {
    @Serial
    private static final long serialVersionUID = 1L;


    public CoreRuleException(MessageResponse messageError) {
        super(messageError);
    }

    public CoreRuleException(MessageResponse messageError, Exception cause) {
        super(messageError, cause);
    }
}
