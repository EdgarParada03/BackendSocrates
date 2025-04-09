package com.example.BackendSocrates.repositories;

import com.example.BackendSocrates.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByTecnicoIdAndFechaServicio(Long tecnicoId, LocalDate fechaServicio);

    @Query("SELECT s FROM servicios s WHERE s.tecnico.id = :tecnicoId " +
            "AND s.fechaServicio = :fecha " +
            "AND ((s.horaServicio <= :horaFin AND :horaInicio <= s.horaServicio.plusMinutes(30)))")
    List<Servicio> findOverlappingServices(
            @Param("tecnicoId") Long tecnicoId,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );
}