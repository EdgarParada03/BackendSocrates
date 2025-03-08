package PruebasUnitariasControllers;

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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ServicioController.class)
public class ServicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioRepository servicioRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Servicio servicio;
    private TipoPlan tipoPlan;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
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

        mockMvc.perform(get("/servicios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].descripcion").value(servicio.getDescripcion()))
                .andExpect(jsonPath("$[0].estado").value(servicio.getEstado()));
    }

    @Test
    void createServicio() throws Exception {
        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicio);

        String servicioJson = objectMapper.writeValueAsString(servicio);

        mockMvc.perform(post("/servicios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(servicioJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value(servicio.getDescripcion()))
                .andExpect(jsonPath("$.estado").value(servicio.getEstado()));
    }

    @Test
    void getServicioById() throws Exception {
        when(servicioRepository.findById(anyLong())).thenReturn(Optional.of(servicio));

        mockMvc.perform(get("/servicios/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.descripcion").value(servicio.getDescripcion()))
                .andExpect(jsonPath("$.estado").value(servicio.getEstado()));
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

        mockMvc.perform(put("/servicios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedServicioJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value(updatedServicio.getDescripcion()))
                .andExpect(jsonPath("$.estado").value(updatedServicio.getEstado()));
    }

    @Test
    void deleteServicio() throws Exception {
        when(servicioRepository.findById(anyLong())).thenReturn(Optional.of(servicio));
        Mockito.doNothing().when(servicioRepository).delete(any(Servicio.class));

        mockMvc.perform(delete("/servicios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deleted").value(true));
    }
}