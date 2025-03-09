package com.example.BackendSocrates;

import com.example.BackendSocrates.controllers.AdministradorController;
import com.example.BackendSocrates.model.Administrador;
import com.example.BackendSocrates.repositories.AdministradorRepository;
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

@WebMvcTest(AdministradorController.class)
public class AdministradorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdministradorRepository administradorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Administrador admin1 = new Administrador(1, "Recursos Humanos", "Alto");
    Administrador admin2 = new Administrador(2, "Finanzas", "Medio");

    @Test
    public void testGetAllAdministradores() throws Exception {
        Mockito.when(administradorRepository.findAll()).thenReturn(Arrays.asList(admin1, admin2));

        mockMvc.perform(get("/api/v1/administradores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].departamento").value("Recursos Humanos"))
                .andExpect(jsonPath("$[1].departamento").value("Finanzas"));
    }

    @Test
    public void testGetAdministradorById() throws Exception {
        Mockito.when(administradorRepository.findById(1L)).thenReturn(Optional.of(admin1));

        mockMvc.perform(get("/api/v1/administradores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departamento").value("Recursos Humanos"))
                .andExpect(jsonPath("$.nivelJerarquico").value("Alto"));
    }

    @Test
    public void testCreateAdministrador() throws Exception {
        Mockito.when(administradorRepository.save(any(Administrador.class))).thenReturn(admin1);

        String adminJson = objectMapper.writeValueAsString(admin1);

        mockMvc.perform(post("/api/v1/administradores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departamento").value("Recursos Humanos"))
                .andExpect(jsonPath("$.nivelJerarquico").value("Alto"));
    }

    @Test
    public void testUpdateAdministrador() throws Exception {
        Administrador adminActualizado = new Administrador(1, "Tecnología", "Bajo");

        Mockito.when(administradorRepository.findById(1L)).thenReturn(Optional.of(admin1));
        Mockito.when(administradorRepository.save(any(Administrador.class))).thenReturn(adminActualizado);

        String adminJson = objectMapper.writeValueAsString(adminActualizado);

        mockMvc.perform(put("/api/v1/administradores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(adminJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departamento").value("Tecnología"))
                .andExpect(jsonPath("$.nivelJerarquico").value("Bajo"));
    }

    @Test
    public void testDeleteAdministrador() throws Exception {
        Mockito.when(administradorRepository.findById(1L)).thenReturn(Optional.of(admin1));

        mockMvc.perform(delete("/api/v1/administradores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }
}
