package com.example.BackendSocrates.services;

import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.repositories.servicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private servicioRepository servicioRepository;

    public Servicio registrarServicio(Servicio servicio) {
        // Validaciones básicas
        if (servicio.getTecnico() == null) {
            throw new RuntimeException("El servicio debe tener un técnico asignado");
        }

        if (servicio.getHoraServicio() == null) {
            throw new RuntimeException("La hora del servicio es obligatoria");
        }

        // Validar horario laboral (8:00 - 18:00)
        LocalTime horaInicio = servicio.getHoraServicio();
        LocalTime horaFin = horaInicio.plusMinutes(30);

        if (horaInicio.isBefore(LocalTime.of(8, 0)) || horaFin.isAfter(LocalTime.of(18, 0))) {
            throw new RuntimeException("El horario del servicio debe estar entre las 8:00 y las 18:00");
        }

        // Buscar servicios en el rango de tiempo (hora ± 30 minutos)
        LocalTime rangoInicio = horaInicio.minusMinutes(30);
        LocalTime rangoFin = horaFin.plusMinutes(30);

        List<Servicio> serviciosExistentes = servicioRepository.findOverlappingServices(
                servicio.getTecnico().getId(),
                servicio.getFechaServicio(),
                rangoInicio,
                rangoFin
        );

        // Verificar si hay servicios que se solapan
        for (Servicio existente : serviciosExistentes) {
            LocalTime inicioExistente = existente.getHoraServicio();
            LocalTime finExistente = inicioExistente.plusMinutes(30);

            if (!(horaFin.isBefore(inicioExistente) || horaInicio.isAfter(finExistente))) {
                throw new RuntimeException(
                        String.format("Ya existe un servicio programado para el técnico en el horario de %s a %s",
                                inicioExistente, finExistente)
                );
            }
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