package com.example.BackendSocrates.repositories;

import com.example.BackendSocrates.model.Tecnico;
import com.example.BackendSocrates.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonaRepository extends JpaRepository<Persona, Long> {
}


