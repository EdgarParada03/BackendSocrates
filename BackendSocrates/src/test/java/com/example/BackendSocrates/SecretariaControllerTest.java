package com.example.BackendSocrates;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(SecretariaController.class)
@AutoConfigureMockMvc
public class SecretariaControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private SecretariaRepository secretariaRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Secretaria secretaria;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        secretaria = new Secretaria();
        secretaria.setId(1L);
        secretaria.setFechaContratacion(new Date());
    }

    @Test
    void getAllSecretaria() throws Exception {
        List<Secretaria> listaSecretarias = Arrays.asList(secretaria);
        when(secretariaRepository.findAll()).thenReturn(listaSecretarias);

        try {
            mockMvc.perform(get("/api/v1/secretarias"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].fechaContratacion").value(secretaria.getFechaContratacion().toInstant().toString()));
            System.out.println("Prueba getAllSecretaria: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba getAllSecretaria: Fracaso");
            throw e;
        }
    }

    @Test
    void createSecretaria() throws Exception {
        when(secretariaRepository.save(any(Secretaria.class))).thenReturn(secretaria);

        String secretariaJson = objectMapper.writeValueAsString(secretaria);

        try {
            mockMvc.perform(post("/api/v1/secretarias")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(secretariaJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.fechaContratacion").value(secretaria.getFechaContratacion().toInstant().toString()));
            System.out.println("Prueba createSecretaria: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba createSecretaria: Fracaso");
            throw e;
        }
    }

    @Test
    void getSecretariaById() throws Exception {
        when(secretariaRepository.findById(anyLong())).thenReturn(Optional.of(secretaria));

        try {
            mockMvc.perform(get("/api/v1/secretarias/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.fechaContratacion").value(secretaria.getFechaContratacion().toInstant().toString()));
            System.out.println("Prueba getSecretariaById: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba getSecretariaById: Fracaso");
            throw e;
        }
    }

    @Test
    void updateSecretaria() throws Exception {
        Secretaria updatedSecretaria = new Secretaria();
        updatedSecretaria.setFechaContratacion(new Date());

        when(secretariaRepository.findById(anyLong())).thenReturn(Optional.of(secretaria));
        when(secretariaRepository.save(any(Secretaria.class))).thenReturn(updatedSecretaria);

        String updatedSecretariaJson = objectMapper.writeValueAsString(updatedSecretaria);

        try {
            mockMvc.perform(put("/api/v1/secretarias/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedSecretariaJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.fechaContratacion").value(updatedSecretaria.getFechaContratacion().toInstant().toString()));
            System.out.println("Prueba updateSecretaria: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba updateSecretaria: Fracaso");
            throw e;
        }
    }

    @Test
    void deleteSecretaria() throws Exception {
        when(secretariaRepository.findById(anyLong())).thenReturn(Optional.of(secretaria));
        Mockito.doNothing().when(secretariaRepository).delete(any(Secretaria.class));

        try {
            mockMvc.perform(delete("/api/v1/secretarias/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.deleted").value(true));
            System.out.println("Prueba deleteSecretaria: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba deleteSecretaria: Fracaso");
            throw e;
        }
    }
}