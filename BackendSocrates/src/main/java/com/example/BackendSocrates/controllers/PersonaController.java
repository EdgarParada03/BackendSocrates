package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.model.Persona;
import com.example.BackendSocrates.repositories.PersonaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1")
public class PersonaController {

    private final PersonaRepository personaRepository;

    public PersonaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    // Obtener todas las personas
    @GetMapping("/personas")
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }

    // Crear una nueva persona
    @PostMapping("/personas")
    public Persona createPersona(@RequestBody Persona persona) {
        return personaRepository.save(persona);
    }

    // Obtener una persona por ID
    @GetMapping("/personas/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no existe con id: " + id));
        return ResponseEntity.ok(persona);
    }



    // Actualizar persona
    @PutMapping("/personas/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona personaDetails) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no existe con id: " + id));

        // Actualizando los atributos de Persona
        persona.setNombre(personaDetails.getNombre());
        persona.setNumeroDocumento(personaDetails.getNumeroDocumento());
        persona.setTelefono(personaDetails.getTelefono());
        persona.setDireccion(personaDetails.getDireccion());
        persona.setCorreo(personaDetails.getCorreo());
        persona.setEstado(personaDetails.getEstado());
        persona.setCargo(personaDetails.getCargo());

        Persona updatedPersona = personaRepository.save(persona);
        return ResponseEntity.ok(updatedPersona);
    }

    // Eliminar persona
    @DeleteMapping("/personas/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePersona(@PathVariable Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no existe con id: " + id));
        personaRepository.delete(persona);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
