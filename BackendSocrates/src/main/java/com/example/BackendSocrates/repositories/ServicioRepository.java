package com.example.BackendSocrates.repositories;

import com.example.BackendSocrates.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByTecnicoIdAndFechaServicio(Long tecnicoId, LocalDate fechaServicio);

}
