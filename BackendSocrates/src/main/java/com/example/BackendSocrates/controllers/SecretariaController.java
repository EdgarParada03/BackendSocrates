package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.model.Secretaria;
import com.example.BackendSocrates.model.Secretaria;
import com.example.BackendSocrates.model.Secretaria;
import com.example.BackendSocrates.model.Secretaria;
import com.example.BackendSocrates.repositories.SecretariaRepository;
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
public class SecretariaController {

    @Autowired
    private SecretariaRepository secretariaRepository;

    //Get all secretarias
    @GetMapping("/secretarias")
    public List<Secretaria> getAllSecretaria(){
        return secretariaRepository.findAll();
    }

    //Create secretaria rest api
    @PostMapping("/secretarias")
    public Secretaria createCSecretaria(@RequestBody Secretaria secretaria){
        return secretariaRepository.save(secretaria);
    }

    //Get secretaria by id rest api
    @GetMapping("/secretarias/{id}")
    public ResponseEntity<Secretaria> getSecretariaById(@PathVariable Long id){
        Secretaria secretaria = secretariaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Secretaria not exist with id :" + id));
        return ResponseEntity.ok(secretaria);
    }

    //Update secretaria rest api
    @PutMapping("/secretarias/{id}")
    public ResponseEntity<Secretaria> updateSecretaria(@PathVariable Long id, @RequestBody Secretaria secretariaDetails){
        Secretaria secretaria = secretariaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Secretaria not exist with id :" + id));

        //AQUI PONER LOS OTROS ATRIBUTOS DE PERSONA

        secretaria.setFechaContratacion(secretariaDetails.getFechaContratacion());

        Secretaria updateSecretaria = secretariaRepository.save(secretaria);
        return ResponseEntity.ok(updateSecretaria);
    }

    //Delete secretaria rest api
    @DeleteMapping("/secretarias/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteSecretaria(@PathVariable Long id){
        Secretaria secretaria = secretariaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Secretaria not exist with id :" + id));
        secretariaRepository.delete(secretaria);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
