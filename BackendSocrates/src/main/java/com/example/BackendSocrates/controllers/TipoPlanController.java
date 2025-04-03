package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.model.*;
import com.example.BackendSocrates.model.TipoPlan;
import com.example.BackendSocrates.model.TipoPlan;
import com.example.BackendSocrates.model.TipoPlan;
import com.example.BackendSocrates.repositories.TipoPlanRepository;
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
public class TipoPlanController {

    @Autowired
    private TipoPlanRepository tipoPlanRepository;

    //Get all tipo_planes
    @GetMapping("/tipos_planes")
    public List<TipoPlan> getAllTipoPlan(){
        return tipoPlanRepository.findAll();
    }

    //Create tipoPlan rest api
    @PostMapping("/tipos_planes")
    public TipoPlan createTipoPlan(@RequestBody TipoPlan tipoPlan){
        return tipoPlanRepository.save(tipoPlan);
    }

    //Get tipoPlan by id rest api
    @GetMapping("/tipos_planes/{id}")
    public ResponseEntity<TipoPlan> getTipoPlanById(@PathVariable Long id){
        TipoPlan tipoPlan = tipoPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoPlan not exist with id :" + id));
        return ResponseEntity.ok(tipoPlan);
    }

    //Update tipoPlan rest api
    @PutMapping("/tipos_planes/{id}")
    public ResponseEntity<TipoPlan> updateTipoPlan(@PathVariable Long id, @RequestBody TipoPlan tipoPlanDetails){
        TipoPlan tipoPlan = tipoPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoPlan not exist with id :" + id));


        tipoPlan.setNombre(tipoPlanDetails.getNombre());
        tipoPlan.setDescripcion(tipoPlanDetails.getDescripcion());
        tipoPlan.setPrecio(tipoPlanDetails.getPrecio());
        tipoPlan.setEstado(tipoPlanDetails.isEstado());

        TipoPlan updateTipoPlan = tipoPlanRepository.save(tipoPlan);
        return ResponseEntity.ok(updateTipoPlan);
    }

    //Delete tipoPlan rest api
    @DeleteMapping("/tipos_planes/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTipoPlan(@PathVariable Long id){
        TipoPlan tipoPlan = tipoPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoPlan not exist with id :" + id));
        tipoPlanRepository.delete(tipoPlan);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
