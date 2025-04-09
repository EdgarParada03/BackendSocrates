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
        List<Servicio> existentes = servicioRepository.findByTecnicoIdAndFechaServicio(
                servicio.getTecnico().getId(),
                servicio.getFechaServicio()
        );

        LocalTime horaInicioNueva = servicio.getHoraServicio();
        LocalTime horaFinNueva = horaInicioNueva.plusMinutes(30);

        for (Servicio existente : existentes) {
            LocalTime horaInicioExistente = existente.getHoraServicio();
            LocalTime horaFinExistente = horaInicioExistente.plusMinutes(30);

            boolean seCruzan = !(horaFinNueva.isBefore(horaInicioExistente) || horaInicioNueva.isAfter(horaFinExistente));
            if (seCruzan) {
                throw new RuntimeException("El técnico ya tiene un servicio asignado en ese horario.");
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
