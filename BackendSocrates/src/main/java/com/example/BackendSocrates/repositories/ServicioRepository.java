package com.example.BackendSocrates.repositories;

import com.example.BackendSocrates.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    @Query("SELECT s FROM servicios s WHERE s.tecnico.id = :tecnicoId AND s.fechaServicio = :fecha AND " +
            "(:hora BETWEEN s.horaServicio AND s.horaServicio.plusMinutes(29))")
    List<Servicio> verificarDisponibilidad(@Param("tecnicoId") Long tecnicoId,
                                           @Param("fecha") LocalDate fecha,
                                           @Param("hora") LocalTime hora);
}
