package com.example.BackendSocrates.services;

import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public Servicio registrarServicio(Servicio servicio) {
        // Calcular la hora fin estimada (30 minutos después de la hora de inicio)
        LocalTime horaInicio = servicio.getHoraServicio();
        LocalTime horaFin = horaInicio.plusMinutes(30); //

        // Buscar si ya hay otro servicio asignado al técnico en ese rango
        List<Servicio> conflictos = servicioRepository.verificarDisponibilidad(
                servicio.getTecnico().getId(),
                servicio.getFechaServicio(),
                servicio.getHoraServicio()
        );

        for (Servicio existente : conflictos) {
            LocalTime existenteInicio = existente.getHoraServicio();
            LocalTime existenteFin = existenteInicio.plusMinutes(30);

            // Validamos si hay cruce de horarios
            boolean hayConflicto = !(horaFin.isBefore(existenteInicio) || horaInicio.isAfter(existenteFin));
            if (hayConflicto) {
                throw new RuntimeException("El técnico ya tiene un servicio asignado entre " +
                        existenteInicio + " y " + existenteFin + ".");
            }
        }

        // Si no hay conflictos, guardar el servicio
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
