package br.com.uol.library.domain.common.exception;

import br.com.uol.library.domain.common.enumeration.MessageType;
import br.com.uol.library.domain.common.utils.MessageResponse;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private MessageResponse buildMessageResponse(String code, String message) {
        return MessageResponse.builder()
                .messageType(MessageType.ERROR)
                .code(code)
                .message(message)
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> handleMissingParameter(MissingServletRequestParameterException ex) {
        MessageResponse messageResponse = buildMessageResponse("error-missing-parameter", "Missing required parameter: " + ex.getParameterName());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        MessageResponse messageResponse = buildMessageResponse("error-method-not-allowed", "Method not allowed for this URL.");
        return new ResponseEntity<>(messageResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CoreRuleException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> handleCoreRuleException(CoreRuleException ex) {
        MessageResponse messageResponse = ex.getMessageError();
        if (messageResponse == null) {
            messageResponse = buildMessageResponse("error-default", "An unexpected error occurred.");
        }
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if ("NOT_FOUND".equals(messageResponse.getCode())) {
            status = HttpStatus.NOT_FOUND;
        } else if ("UNAUTHORIZED".equals(messageResponse.getCode())) {
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(messageResponse, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        MessageResponse messageResponse = buildMessageResponse("error-illegal-argument", ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> handleConfigDataResourceNotFoundException(ConfigDataResourceNotFoundException ex) {
        MessageResponse messageResponse = buildMessageResponse("error-config-data-not-found", ex.getMessage());
        return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<MessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().stream().findFirst().orElse(null);
        if (Objects.nonNull(fieldError)) {
            String errorMessage = fieldError.getDefaultMessage();
            MessageResponse messageResponse = buildMessageResponse("validation-error", errorMessage);
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        MessageResponse messageResponse = buildMessageResponse("validation-error", "Unknown validation error");
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }
}