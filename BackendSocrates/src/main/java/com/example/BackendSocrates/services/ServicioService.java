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
        // Validar si el técnico ya tiene un servicio en ese horario
        List<Servicio> conflictos = servicioRepository.verificarDisponibilidad(
                servicio.getTecnico().getId(),
                servicio.getFechaServicio(),
                servicio.getHoraServicio()
        );

        if (!conflictos.isEmpty()) {
            throw new RuntimeException("El técnico ya tiene un servicio asignado en ese horario.");
        }

        // Calcular y mostrar horaFin (no se guarda, solo informativo si se necesita)
        LocalTime horaFin = servicio.getHoraServicio().plusMinutes(30);
        System.out.println("Hora fin calculada: " + horaFin);

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
