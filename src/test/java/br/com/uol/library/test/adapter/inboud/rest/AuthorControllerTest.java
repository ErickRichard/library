package br.com.uol.library.test.adapter.inboud.rest;

import br.com.uol.library.adapter.inbound.rest.AuthorController;
import br.com.uol.library.application.dto.request.AuthorRequest;
import br.com.uol.library.application.dto.request.AuthorUpdateRequest;
import br.com.uol.library.application.dto.response.AuthorResponse;
import br.com.uol.library.application.port.AuthorServicePort;
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
class AuthorControllerTest {

    private static final UUID UUID_AUTHOR = UUID.randomUUID();

    private MockMvc mockMvc;

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorServicePort authorServicePort;

    private AuthorRequest authorRequest;
    private AuthorResponse authorResponse;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        authorRequest = Mocks.createAuthorRequest();
        authorResponse = Mocks.createAuthorResponse();
    }

    @Test
    void shouldCreateAuthorSuccessfully() throws Exception {
        Mockito.when(authorServicePort.save(Mockito.any(AuthorRequest.class))).thenReturn(authorResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetAllAuthorsSuccessfully() throws Exception {
        List<AuthorResponse> authors = List.of(authorResponse);
        Mockito.when(authorServicePort.findAll()).thenReturn(authors);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/authors"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAuthorByIdSuccessfully() throws Exception {
        Mockito.when(authorServicePort.findById(authorResponse.getId())).thenReturn(authorResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/authors/{id}", authorResponse.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateAuthorSuccessfully() throws Exception {
        Mockito.when(authorServicePort.update(Mockito.any(AuthorUpdateRequest.class))).thenReturn(authorResponse);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/authors/{id}", UUID_AUTHOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Mocks.createAuthorUpdateRequest())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAuthorSuccessfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/authors/{id}", UUID_AUTHOR))
                .andExpect(status().isNoContent());
    }
}