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

import com.example.BackendSocrates.controllers.ClienteController;
import com.example.BackendSocrates.model.Cliente;
import com.example.BackendSocrates.repositories.ClienteRepository;
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

@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private ClienteRepository clienteRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setTipoServicio("Servicio Básico");
        cliente.setFechaRegistro(new Date());
    }

    @Test
    void getAllCliente() throws Exception {
        List<Cliente> listaClientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(listaClientes);

        try {
            mockMvc.perform(get("/api/v1/clientes"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].tipoServicio").value(cliente.getTipoServicio()))
                    .andExpect(jsonPath("$[0].fechaRegistro").value(cliente.getFechaRegistro().toInstant().toString()));
            System.out.println("Prueba getAllCliente: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba getAllCliente: Fracaso");
            throw e;
        }
    }

    @Test
    void createCliente() throws Exception {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        String clienteJson = objectMapper.writeValueAsString(cliente);

        try {
            mockMvc.perform(post("/api/v1/clientes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(clienteJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.tipoServicio").value(cliente.getTipoServicio()))
                    .andExpect(jsonPath("$.fechaRegistro").value(cliente.getFechaRegistro().toInstant().toString()));
            System.out.println("Prueba createCliente: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba createCliente: Fracaso");
            throw e;
        }
    }

    @Test
    void getClienteById() throws Exception {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));

        try {
            mockMvc.perform(get("/api/v1/clientes/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.tipoServicio").value(cliente.getTipoServicio()))
                    .andExpect(jsonPath("$.fechaRegistro").value(cliente.getFechaRegistro().toInstant().toString()));
            System.out.println("Prueba getClienteById: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba getClienteById: Fracaso");
            throw e;
        }
    }

    @Test
    void updateCliente() throws Exception {
        Cliente updatedCliente = new Cliente();
        updatedCliente.setTipoServicio("Servicio Actualizado");
        updatedCliente.setFechaRegistro(new Date());

        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(updatedCliente);

        String updatedClienteJson = objectMapper.writeValueAsString(updatedCliente);

        try {
            mockMvc.perform(put("/api/v1/clientes/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedClienteJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.tipoServicio").value(updatedCliente.getTipoServicio()))
                    .andExpect(jsonPath("$.fechaRegistro").value(updatedCliente.getFechaRegistro().toInstant().toString()));
            System.out.println("Prueba updateCliente: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba updateCliente: Fracaso");
            throw e;
        }
    }

    @Test
    void deleteCliente() throws Exception {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));
        Mockito.doNothing().when(clienteRepository).delete(any(Cliente.class));

        try {
            mockMvc.perform(delete("/api/v1/clientes/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.deleted").value(true));
            System.out.println("Prueba deleteCliente: Éxito");
        } catch (AssertionError e) {
            System.out.println("Prueba deleteCliente: Fracaso");
            throw e;
        }
    }
}
