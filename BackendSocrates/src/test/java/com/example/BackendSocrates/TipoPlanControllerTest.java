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

import com.example.BackendSocrates.controllers.TipoPlanController;
import com.example.BackendSocrates.model.TipoPlan;
import com.example.BackendSocrates.repositories.TipoPlanRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(TipoPlanController.class)
@AutoConfigureMockMvc
public class TipoPlanControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private TipoPlanRepository tipoPlanRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private TipoPlan tipoPlan;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        tipoPlan = new TipoPlan();
        tipoPlan.setId(1L);
        tipoPlan.setNombre("Plan Básico");
        tipoPlan.setDescripcion("Descripción del plan básico");
        tipoPlan.setPrecio(100.0);
        tipoPlan.setEstado(true);
    }

    @Test
    void getAllTipoPlan() throws Exception {
        List<TipoPlan> listaTipoPlanes = Arrays.asList(tipoPlan);
        when(tipoPlanRepository.findAll()).thenReturn(listaTipoPlanes);

        try {
            mockMvc.perform(get("/api/v1/tipos_planes"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].nombre").value(tipoPlan.getNombre()))
                    .andExpect(jsonPath("$[0].descripcion").value(tipoPlan.getDescripcion()));
            System.out.println("Prueba getAllTipoPlan: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba getAllTipoPlan: Fracaso");
            throw e;
        }
    }

    @Test
    void createTipoPlan() throws Exception {
        when(tipoPlanRepository.save(any(TipoPlan.class))).thenReturn(tipoPlan);

        String tipoPlanJson = objectMapper.writeValueAsString(tipoPlan);

        try {
            mockMvc.perform(post("/api/v1/tipos_planes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(tipoPlanJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombre").value(tipoPlan.getNombre()))
                    .andExpect(jsonPath("$.descripcion").value(tipoPlan.getDescripcion()));
            System.out.println("Prueba createTipoPlan: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba createTipoPlan: Fracaso");
            throw e;
        }
    }

    @Test
    void getTipoPlanById() throws Exception {
        when(tipoPlanRepository.findById(anyLong())).thenReturn(Optional.of(tipoPlan));

        try {
            mockMvc.perform(get("/api/v1/tipos_planes/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.nombre").value(tipoPlan.getNombre()))
                    .andExpect(jsonPath("$.descripcion").value(tipoPlan.getDescripcion()));
            System.out.println("Prueba getTipoPlanById: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba getTipoPlanById: Fracaso");
            throw e;
        }
    }

    @Test
    void updateTipoPlan() throws Exception {
        TipoPlan updatedTipoPlan = new TipoPlan();
        updatedTipoPlan.setNombre("Plan Actualizado");
        updatedTipoPlan.setDescripcion("Descripción actualizada");
        updatedTipoPlan.setPrecio(150.0);
        updatedTipoPlan.setEstado(true);

        when(tipoPlanRepository.findById(anyLong())).thenReturn(Optional.of(tipoPlan));
        when(tipoPlanRepository.save(any(TipoPlan.class))).thenReturn(updatedTipoPlan);

        String updatedTipoPlanJson = objectMapper.writeValueAsString(updatedTipoPlan);

        try {
            mockMvc.perform(put("/api/v1/tipos_planes/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedTipoPlanJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombre").value(updatedTipoPlan.getNombre()))
                    .andExpect(jsonPath("$.descripcion").value(updatedTipoPlan.getDescripcion()));
            System.out.println("Prueba updateTipoPlan: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba updateTipoPlan: Fracaso");
            throw e;
        }
    }

    @Test
    void deleteTipoPlan() throws Exception {
        when(tipoPlanRepository.findById(anyLong())).thenReturn(Optional.of(tipoPlan));
        Mockito.doNothing().when(tipoPlanRepository).delete(any(TipoPlan.class));

        try {
            mockMvc.perform(delete("/api/v1/tipos_planes/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.deleted").value(true));
            System.out.println("Prueba deleteTipoPlan: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba deleteTipoPlan: Fracaso");
            throw e;
        }
    }
}
