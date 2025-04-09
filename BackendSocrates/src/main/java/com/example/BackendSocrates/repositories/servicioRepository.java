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
public interface servicioRepository extends JpaRepository<Servicio, Long> {

    @Query("SELECT s FROM servicios s " +
            "WHERE s.tecnico.id = :tecnicoId " +
            "AND s.fechaServicio = :fecha " +
            "AND ((s.horaServicio <= :horaInicio AND s.horaServicio.plusMinutes(30) > :horaInicio) " +
            "OR (s.horaServicio < :horaFin AND s.horaServicio.plusMinutes(30) >= :horaFin) " +
            "OR (s.horaServicio >= :horaInicio AND s.horaServicio.plusMinutes(30) <= :horaFin))")
    List<Servicio> findOverlappingServices(
            @Param("tecnicoId") Long tecnicoId,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );

}