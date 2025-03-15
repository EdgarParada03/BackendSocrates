package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.model.Cliente;
import com.example.BackendSocrates.repositories.ClienteRepository;
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
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    //Get all clientes
    @GetMapping("/clientes")
    public List<Cliente> getAllCliente(){
        return clienteRepository.findAll();
    }

    //Create cliente rest api
    @PostMapping("/clientes")
    public Cliente createCliente(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    //Get cliente by id rest api
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente not exist with id :" + id));
        return ResponseEntity.ok(cliente);
    }

    //Update cliente rest api
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente not exist with id :" + id));

        ////
        cliente.setNombre(clienteDetails.getNombre());
        cliente.setNumeroDocumento(clienteDetails.getNumeroDocumento());
        cliente.setTelefono(clienteDetails.getTelefono());
        cliente.setDireccion(clienteDetails.getDireccion());
        cliente.setCorreo(clienteDetails.getCorreo());
        cliente.setEstado(clienteDetails.getEstado());
        cliente.setCargo(clienteDetails.getCargo());
        cliente.setSexo(clienteDetails.getSexo());
        cliente.setTipoDocumento(clienteDetails.getTipoDocumento());
        //////

        cliente.setTipoServicio(clienteDetails.getTipoServicio());
        cliente.setFechaRegistro(clienteDetails.getFechaRegistro());

        Cliente updateCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(updateCliente);
    }

    //Delete cliente rest api
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCliente(@PathVariable Long id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente not exist with id :" + id));
        clienteRepository.delete(cliente);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
