package com.example.BackendSocrates.repositories;

import com.example.BackendSocrates.model.Tecnico;
import com.example.BackendSocrates.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByNumeroDocumento(String numeroDocumento);
}


