package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.model.Servicio;
import com.example.BackendSocrates.repositories.ServicioRepository;
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

    //Get all servicios
    @GetMapping("/servicios")
    public List<Servicio> getAllServicio() {
        return servicioRepository.findAll();
    }

    //Create servicio rest api
    @PostMapping("/servicios")
    public Servicio createServicio(@RequestBody Servicio servicio){
        return servicioRepository.save(servicio);
    }

    //Get servicio by id rest api
    @GetMapping("/servicios/{id}")
    public ResponseEntity<Servicio> getServicioById(@PathVariable Long id){
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio not exist with id :" + id));
        return ResponseEntity.ok(servicio);
    }

    //Update servicio rest api
    @PutMapping("/servicios/{id}")
    public ResponseEntity<Servicio> updateServicio(@PathVariable Long id, @RequestBody Servicio servicioDetails){
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio not exist with id :" + id));

        //AQUI PONER LOS OTROS ATRIBUTOS DE PERSONA

        servicio.setFechaServicio(servicioDetails.getFechaServicio());
        servicio.setDescripcion(servicioDetails.getDescripcion());
        servicio.setHoraServicio(servicioDetails.getHoraServicio());
        servicio.setEstado(servicioDetails.getEstado());
        servicio.setTipoPlan(servicioDetails.getTipoPlan());
        //ESPACIO PAL TECNICO
        servicio.setTecnico(servicioDetails.getTecnico());
        //
        servicio.setCliente(servicioDetails.getCliente());

        Servicio updateServicio = servicioRepository.save(servicio);
        return ResponseEntity.ok(updateServicio);
    }

    //Delete servicio rest api
    @DeleteMapping("/servicios/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteServicio(@PathVariable Long id){
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio not exist with id :" + id));
        servicioRepository.delete(servicio);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
