package com.example.BackendSocrates;

import com.example.BackendSocrates.controllers.TecnicoController;
import com.example.BackendSocrates.model.Tecnico;
import com.example.BackendSocrates.repositories.TecnicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TecnicoController.class)
public class TecnicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Tecnico tecnico1 = new Tecnico(1L, "Juan Perez", "Cedula", "12345678", "3112233445", "Calle 123", "juan@mail.com", true, "Masculino", "Técnico de Redes", "Redes y Telecomunicaciones");
    Tecnico tecnico2 = new Tecnico(2L, "Maria Lopez", "TI", "87654321", "3223344556", "Avenida 456", "maria@mail.com", true, "Femenino", "Técnico de Sistemas", "Soporte Técnico");

    @Test
    public void testGetAllTecnicos() throws Exception {
        Mockito.when(tecnicoRepository.findAll()).thenReturn(Arrays.asList(tecnico1, tecnico2));

        mockMvc.perform(get("/api/v1/tecnicos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"))
                .andExpect(jsonPath("$[1].nombre").value("Maria Lopez"));
    }

    @Test
    public void testGetTecnicoById() throws Exception {
        Mockito.when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico1));

        mockMvc.perform(get("/api/v1/tecnicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.especialidad").value("Redes y Telecomunicaciones"));
    }

    @Test
    public void testCreateTecnico() throws Exception {
        Mockito.when(tecnicoRepository.save(any(Tecnico.class))).thenReturn(tecnico1);

        String tecnicoJson = objectMapper.writeValueAsString(tecnico1);

        mockMvc.perform(post("/api/v1/tecnicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tecnicoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.especialidad").value("Redes y Telecomunicaciones"));
    }

    @Test
    public void testUpdateTecnico() throws Exception {
        Tecnico tecnicoActualizado = new Tecnico(1L, "Juan Perez", "Cedula", "12345678", "3112233445", "Calle 999", "juanperez@mail.com", false, "Masculino", "Técnico de Seguridad", "Seguridad Informática");

        Mockito.when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico1));
        Mockito.when(tecnicoRepository.save(any(Tecnico.class))).thenReturn(tecnicoActualizado);

        String tecnicoJson = objectMapper.writeValueAsString(tecnicoActualizado);

        mockMvc.perform(put("/api/v1/tecnicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tecnicoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccion").value("Calle 999"))
                .andExpect(jsonPath("$.correo").value("juanperez@mail.com"))
                .andExpect(jsonPath("$.especialidad").value("Seguridad Informática"));
    }

    @Test
    public void testDeleteTecnico() throws Exception {
        Mockito.when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico1));

        mockMvc.perform(delete("/api/v1/tecnicos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }
}
