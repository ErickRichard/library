package br.com.uol.library.domain.common.mapper;

import br.com.uol.library.application.dto.request.AuthorRequest;
import br.com.uol.library.application.dto.request.AuthorUpdateRequest;
import br.com.uol.library.application.dto.request.BookRequest;
import br.com.uol.library.application.dto.request.BookUpdateRequest;
import br.com.uol.library.application.dto.response.AuthorResponse;
import br.com.uol.library.application.dto.response.BookResponse;
import br.com.uol.library.domain.model.Author;
import br.com.uol.library.domain.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LibraryMapper {
    Author toAuthorEntity(AuthorRequest authorRequest);
    Author toAuthor(AuthorResponse authorResponse);
    AuthorResponse toAuthorResponse(Author author);
    void toUpdateAuthor(@MappingTarget Author author, AuthorUpdateRequest authorUpdateRequest);

    @Mapping(target = "author", source = "author")
    Book toBookEntity(BookRequest bookRequest, Author author);
    BookResponse toBookResponse(Book book);
    void toUpdateBook(@MappingTarget Book book, BookUpdateRequest bookUpdateRequest);
}