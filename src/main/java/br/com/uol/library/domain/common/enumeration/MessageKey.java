package br.com.uol.library.domain.common.enumeration;

import br.com.uol.library.domain.common.utils.ValidateMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum MessageKey implements ValidateMessage {

    AUTHOR_HAS_BOOKS("author-has-books", "Cannot delete author with associated books."),
    AUTHOR_NOT_FOUND("author-not-found", "Author not found."),
    BOOK_ALREADY_REGISTERED("book-already-registered", "Book already registered with the same ISBN."),
    BOOK_NOT_FOUND("book-not-found", "Book not found."),
    DUPLICATE_ISBN("duplicate-isbn", "Cannot register a book with a duplicate ISBN."),
    ERROR_DEFAULT("error-default", "Default error."),
    FAIL_TO_GENERATE_TOKEN("fail-to-generate-token", "Failed to generate token."),
    FAIL_TO_SAVE_AUTHOR("fail-to-save-author", "Failed to save the author."),
    FAIL_TO_SAVE_BOOK("fail-to-save-book", "Failed to save the book."),
    FAIL_TO_UPDATE_AUTHOR("fail-to-update-author", "Failed to update the author."),
    FAIL_TO_UPDATE_BOOK("fail-to-update-book", "Failed to update the book."),
    INVALID_TOKEN_EXPIRED("invalid-token-expired", "Invalid or expired token."),
    NO_AUTHORS_FOUND("no-authors-found", "No authors found."),
    NO_BOOKS_FOUND("no-books-found", "No books found."),
    NO_BOOKS_FOUND_FOR_AUTHOR("no-books-found-for-author", "No books entered by the respective author."),
    PUBLICATION_DATE_IN_FUTURE("publication-date-in-future", "The book's publication date must be in the past.");

    private final String code;
    private final String message;

    public static MessageKey findByCode(String code) {
        return Arrays.stream(MessageKey.values())
                .filter(keyEnum -> Objects.equals(keyEnum.getCode(), code))
                .findFirst()
                .orElse(ERROR_DEFAULT);
    }
}
