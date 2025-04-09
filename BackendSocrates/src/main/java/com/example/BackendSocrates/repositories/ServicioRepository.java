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
            ":horaInicio < FUNCTION('ADDTIME', s.horaServicio, '00:30:00') AND " +
            "FUNCTION('ADDTIME', :horaInicio, '00:30:00') > s.horaServicio")
    List<Servicio> verificarDisponibilidad(
            @Param("tecnicoId") Long tecnicoId,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio
    );
}
