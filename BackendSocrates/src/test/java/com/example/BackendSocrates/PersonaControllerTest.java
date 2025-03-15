package com.example.BackendSocrates;

import com.example.BackendSocrates.controllers.PersonaController;
import com.example.BackendSocrates.model.Persona;
import com.example.BackendSocrates.repositories.PersonaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonaController.class)
public class PersonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonaRepository personaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Persona persona1 = new Persona(1L, "Juan Perez", "12345678", "3112233445", "Calle 123", "juan@mail.com", true, "Ingeniero");
    Persona persona2 = new Persona(2L, "Maria Lopez", "87654321", "3223344556", "Carrera 456", "maria@mail.com", true, "Analista");

    @Test
    public void testGetAllPersonas() throws Exception {
        Mockito.when(personaRepository.findAll()).thenReturn(Arrays.asList(persona1, persona2));

        mockMvc.perform(get("/api/v1/personas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$[1].nombre").value("Maria Lopez"));
    }

    @Test
    public void testGetPersonaById() throws Exception {
        Mockito.when(personaRepository.findById(1L)).thenReturn(Optional.of(persona1));

        mockMvc.perform(get("/api/v1/personas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.correo").value("juan@mail.com"));
    }

    @Test
    public void testCreatePersona() throws Exception {
        Mockito.when(personaRepository.save(any(Persona.class))).thenReturn(persona1);

        String personaJson = objectMapper.writeValueAsString(persona1);

        mockMvc.perform(post("/api/v1/personas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.correo").value("juan@mail.com"));
    }

    @Test
    public void testUpdatePersona() throws Exception {
        Persona personaActualizada = new Persona(1L, "Juan Perez", "12345678", "3112233445", "Av. Principal 789", "juanp@mail.com", false, "Gerente");

        Mockito.when(personaRepository.findById(1L)).thenReturn(Optional.of(persona1));
        Mockito.when(personaRepository.save(any(Persona.class))).thenReturn(personaActualizada);

        String personaJson = objectMapper.writeValueAsString(personaActualizada);

        mockMvc.perform(put("/api/v1/personas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(personaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccion").value("Av. Principal 789"))
                .andExpect(jsonPath("$.correo").value("juanp@mail.com"))
                .andExpect(jsonPath("$.cargo").value("Gerente"));
    }

    @Test
    public void testDeletePersona() throws Exception {
        Mockito.when(personaRepository.findById(1L)).thenReturn(Optional.of(persona1));

        mockMvc.perform(delete("/api/v1/personas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }
}
