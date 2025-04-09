package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping("/servicios")
    public List<Servicio> getAllServicios() {
        return servicioService.listarServicios();
    }

    @PostMapping("/servicios")
    public ResponseEntity<Servicio> createServicio(@RequestBody Servicio servicio) {
        Servicio creado = servicioService.registrarServicio(servicio);
        return ResponseEntity.ok(creado);
    }

    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Long id) {
        return ResponseEntity.ok(servicioService.obtenerPorId(id));
    }

    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteServicio(@PathVariable Long id) {
        servicioService.eliminarServicio(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", true);
        return ResponseEntity.ok(response);
    }
}
