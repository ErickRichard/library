package br.com.uol.library.test.adapter.inboud.rest;

import br.com.uol.library.adapter.inbound.rest.AuthorController;
import br.com.uol.library.adapter.inbound.rest.BookController;
import br.com.uol.library.application.dto.request.BookRequest;
import br.com.uol.library.application.dto.response.BookResponse;
import br.com.uol.library.application.port.BookServicePort;
import br.com.uol.library.test.mocks.Mocks;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    private static final UUID UUID_BOOK = UUID.randomUUID();

    private MockMvc mockMvc;

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookServicePort bookServicePort;

    private BookRequest bookRequest;
    private BookResponse bookResponse;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        bookRequest = Mocks.createBookRequest();
        bookResponse = Mocks.createBookResponse();
    }

    @Test
    void shouldCreateBooksSuccessfully() throws Exception {
        Mockito.when(bookServicePort.save(Mockito.any())).thenReturn(bookResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetListOfBooksSuccessfully() throws Exception {
        var bookResponseList = List.of(bookResponse);
        Mockito.when(bookServicePort.findAll()).thenReturn(bookResponseList);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetBookByIdSuccessfully() throws Exception {
        Mockito.when(bookServicePort.findById(bookResponse.getId())).thenReturn(bookResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/books/{id}", bookResponse.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateBookSuccessfully() throws Exception {
        Mockito.when(bookServicePort.update(Mockito.any())).thenReturn(bookResponse);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/books/{id}", UUID_BOOK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Mocks.createBookUpdateRequest())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAuthorSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/books/{id}", UUID_BOOK))
                .andExpect(status().isNoContent());
    }
}