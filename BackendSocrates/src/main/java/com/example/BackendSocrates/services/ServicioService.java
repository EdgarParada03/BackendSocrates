package com.example.BackendSocrates.services;

import com.example.BackendSocrates.model.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private com.example.BackendSocrates.repositories.servicioRepository servicioRepository;

    public Servicio registrarServicio(Servicio servicio) {
        // Calcular la hora de fin (30 minutos después de la hora de inicio)
        LocalTime horaInicio = servicio.getHoraServicio();
        LocalTime horaFin = horaInicio.plusMinutes(30);

        // Buscar servicios que se solapan
        List<Servicio> serviciosSolapados = servicioRepository.findOverlappingServices(
                servicio.getTecnico().getId(),
                servicio.getFechaServicio(),
                horaInicio,
                horaFin
        );

        if (!serviciosSolapados.isEmpty()) {
            throw new RuntimeException("El técnico no está disponible en el horario seleccionado. " +
                    "Ya tiene un servicio programado entre las " +
                    horaInicio + " y las " + horaFin);
        }

        return servicioRepository.save(servicio);
    }

    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    public Servicio obtenerPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
    }

    public void eliminarServicio(Long id) {
        servicioRepository.deleteById(id);
    }
}