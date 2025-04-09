package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.repositories.ServicioRepository;
import com.example.BackendSocrates.services.ServicioService;
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
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ServicioService servicioService;

    // Get all servicios
    @GetMapping("/servicios")
    public List<Servicio> getAllServicio() {
        return servicioRepository.findAll();
    }

    // Create servicio con validación de disponibilidad
    @PostMapping("/servicios")
    public ResponseEntity<?> createServicio(@RequestBody Servicio servicio) {
        try {
            Servicio nuevoServicio = servicioService.registrarServicio(servicio);
            return ResponseEntity.ok(nuevoServicio);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Get servicio by id
    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio not exist with id :" + id));
        return ResponseEntity.ok(servicio);
    }

    // Update servicio
    @PutMapping("/servicios/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Long id, @RequestBody Servicio servicioDetails) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio not exist with id :" + id));

        servicio.setFechaServicio(servicioDetails.getFechaServicio());
        servicio.setDescripcion(servicioDetails.getDescripcion());
        servicio.setHoraServicio(servicioDetails.getHoraServicio());
        servicio.setEstado(servicioDetails.getEstado());
        servicio.setTipoPlan(servicioDetails.getTipoPlan());
        servicio.setTecnico(servicioDetails.getTecnico());
        servicio.setCliente(servicioDetails.getCliente());

        Servicio updateServicio = servicioRepository.save(servicio);
        return ResponseEntity.ok(updateServicio);
    }

    // Delete servicio
    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteServicio(@PathVariable Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio not exist with id :" + id));
        servicioRepository.delete(servicio);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
