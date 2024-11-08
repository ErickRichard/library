package br.com.uol.library.domain.common.utils;

import br.com.uol.library.domain.common.enumeration.MessageKey;
import br.com.uol.library.domain.common.enumeration.MessageType;
import lombok.*;
import org.springframework.context.MessageSource;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private MessageType messageType;
    private String code;
    private String message;
    private List<Detail> details;

    public MessageResponse(MessageType messageType, String code, String message) {
        super();
        this.messageType = messageType;
        this.code = code;
        this.message = message;
    }

    public static MessageResponse error(MessageKey messageKey, Locale locale, MessageSource messageSource) {
        String translatedMessage = messageSource.getMessage(messageKey.getCode(), null, locale);
        return new MessageResponse(MessageType.ERROR, messageKey.getCode(), translatedMessage);
    }

    public static MessageResponse warning(MessageKey messageKey, Locale locale, MessageSource messageSource) {
        String translatedMessage = messageSource.getMessage(messageKey.getCode(), null, locale);
        return new MessageResponse(MessageType.WARNING, messageKey.getCode(), translatedMessage);
    }

    public static MessageResponse success(MessageKey messageKey, Locale locale, MessageSource messageSource) {
        String translatedMessage = messageSource.getMessage(messageKey.getCode(), null, locale);
        return new MessageResponse(MessageType.SUCCESS, messageKey.getCode(), translatedMessage);
    }
}
