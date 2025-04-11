package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://38.191.42.12:4200")
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String numeroDocumento, @RequestParam String password) {
        return authService.authenticate(numeroDocumento, password);
    }

    @GetMapping("/prueba")
    public String prueba() {
        return "Hola desde el backend en Render";
    }
}