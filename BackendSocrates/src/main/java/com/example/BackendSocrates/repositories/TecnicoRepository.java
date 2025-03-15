package com.example.BackendSocrates.repositories;

import com.example.BackendSocrates.model.Cliente;
import com.example.BackendSocrates.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
}
