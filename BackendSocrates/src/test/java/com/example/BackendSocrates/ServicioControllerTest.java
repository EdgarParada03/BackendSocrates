package com.example.BackendSocrates;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.BackendSocrates.controllers.ServicioController;
import com.example.BackendSocrates.model.Cliente;
import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.model.TipoPlan;
import com.example.BackendSocrates.repositories.ServicioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@WebMvcTest(ServicioController.class)
@AutoConfigureMockMvc
public class ServicioControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private ServicioRepository servicioRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Servicio servicio;
    private TipoPlan tipoPlan;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        tipoPlan = new TipoPlan();
        tipoPlan.setId(1L);
        tipoPlan.setNombre("Plan Básico");

        cliente = new Cliente();
        cliente.setId(1L);

        servicio = new Servicio();
        servicio.setId(1L);
        servicio.setFechaServicio(new Date());
        servicio.setDescripcion("Descripción del servicio");
        servicio.setHoraServicio(new Time(System.currentTimeMillis()));
        servicio.setEstado("Pendiente");
        servicio.setTipoPlan(tipoPlan);
        servicio.setCliente(cliente);
    }

    @Test
    void getAllServicios() throws Exception {
        List<Servicio> listaServicios = Arrays.asList(servicio);
        when(servicioRepository.findAll()).thenReturn(listaServicios);

        try {
            mockMvc.perform(get("/api/v1/servicios"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].descripcion").value(servicio.getDescripcion()))
                    .andExpect(jsonPath("$[0].estado").value(servicio.getEstado()));
            System.out.println("Prueba getAllServicios: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba getAllServicios: Fracaso");
            throw e;
        }
    }

    @Test
    void createServicio() throws Exception {
        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicio);

        String servicioJson = objectMapper.writeValueAsString(servicio);

        try {
            mockMvc.perform(post("/api/v1/servicios")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(servicioJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.descripcion").value(servicio.getDescripcion()))
                    .andExpect(jsonPath("$.estado").value(servicio.getEstado()));
            System.out.println("Prueba createServicio: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba createServicio: Fracaso");
            throw e;
        }
    }

    @Test
    void getServicioById() throws Exception {
        when(servicioRepository.findById(anyLong())).thenReturn(Optional.of(servicio));

        try {
            mockMvc.perform(get("/api/v1/servicios/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.descripcion").value(servicio.getDescripcion()))
                    .andExpect(jsonPath("$.estado").value(servicio.getEstado()));
            System.out.println("Prueba getServicioById: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba getServicioById: Fracaso");
            throw e;
        }
    }

    @Test
    void updateServicio() throws Exception {
        Servicio updatedServicio = new Servicio();
        updatedServicio.setDescripcion("Descripción actualizada");
        updatedServicio.setEstado("Completado");
        updatedServicio.setFechaServicio(new Date());
        updatedServicio.setHoraServicio(new Time(System.currentTimeMillis()));
        updatedServicio.setTipoPlan(tipoPlan);
        updatedServicio.setCliente(cliente);

        when(servicioRepository.findById(anyLong())).thenReturn(Optional.of(servicio));
        when(servicioRepository.save(any(Servicio.class))).thenReturn(updatedServicio);

        String updatedServicioJson = objectMapper.writeValueAsString(updatedServicio);

        try {
            mockMvc.perform(put("/api/v1/servicios/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedServicioJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.descripcion").value(updatedServicio.getDescripcion()))
                    .andExpect(jsonPath("$.estado").value(updatedServicio.getEstado()));
            System.out.println("Prueba updateServicio: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba updateServicio: Fracaso");
            throw e;
        }
    }

    @Test
    void deleteServicio() throws Exception {
        when(servicioRepository.findById(anyLong())).thenReturn(Optional.of(servicio));
        Mockito.doNothing().when(servicioRepository).delete(any(Servicio.class));

        try {
            mockMvc.perform(delete("/api/v1/servicios/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.deleted").value(true));
            System.out.println("Prueba deleteServicio: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba deleteServicio: Fracaso");
            throw e;
        }
    }
}