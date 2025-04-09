package com.example.BackendSocrates.services;


import com.example.BackendSocrates.model.Persona;
import com.example.BackendSocrates.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private PersonaRepository personaRepository;

    public Map<String, String> authenticate(String numeroDocumento, String password, String cargo) {
        Map<String, String> response = new HashMap<>();
        Optional<Persona> persona = personaRepository.findByNumeroDocumento(numeroDocumento);


        if (persona.isPresent() && persona.get().getNumeroDocumento().equals(password)) {
            response.put("message", "Login successful");

            Persona usuario = persona.get();

            if (cargo != null && usuario.getCargo().equalsIgnoreCase("administrador")) {
                response.put("userType", "administrador");
            } else if (cargo != null && usuario.getCargo().equalsIgnoreCase("secretaria")) {
                response.put("userType", "secretaria");
            }
        } else {
            response.put("message", "Invalid credentials");
        }
        return response;
    }
}
