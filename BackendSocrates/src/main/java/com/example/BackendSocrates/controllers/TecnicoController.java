package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.model.Tecnico;
import com.example.BackendSocrates.repositories.TecnicoRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1")
public class TecnicoController {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    // Get all tecnicos
    @GetMapping("/tecnicos")
    public List<Tecnico> getAllTecnicos() {
        return tecnicoRepository.findAll();
    }

    // Create tecnico rest api
    @PostMapping("/tecnicos")
    public Tecnico createTecnico(@RequestBody Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }

    // Get tecnico by id rest api
    @GetMapping("/tecnicos/{id}")
    public ResponseEntity<Tecnico> getTecnicoById(@PathVariable Long id) {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tecnico not exist with id: " + id));
        return ResponseEntity.ok(tecnico);
    }

    // Update tecnico rest api
    @PutMapping("/tecnicos/{id}")
    public ResponseEntity<Tecnico> updateTecnico(@PathVariable Long id, @RequestBody Tecnico tecnicoDetails) {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tecnico not exist with id: " + id));

        tecnico.setNombre(tecnicoDetails.getNombre());
        tecnico.setTipoDocumento(tecnicoDetails.getTipoDocumento());
        tecnico.setNumeroDocumento(tecnicoDetails.getNumeroDocumento());
        tecnico.setTelefono(tecnicoDetails.getTelefono());
        tecnico.setDireccion(tecnicoDetails.getDireccion());
        tecnico.setCorreo(tecnicoDetails.getCorreo());
        tecnico.setEstado(tecnicoDetails.getEstado());
        tecnico.setSexo(tecnicoDetails.getSexo());
        tecnico.setCargo(tecnicoDetails.getCargo());
        tecnico.setEspecialidad(tecnicoDetails.getEspecialidad());

        Tecnico updatedTecnico = tecnicoRepository.save(tecnico);
        return ResponseEntity.ok(updatedTecnico);
    }

    // Delete tecnico rest api
    @DeleteMapping("/tecnicos/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTecnico(@PathVariable Long id) {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tecnico not exist with id: " + id));
        tecnicoRepository.delete(tecnico);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
