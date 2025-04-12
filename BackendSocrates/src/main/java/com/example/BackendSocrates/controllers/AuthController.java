package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(origins = "http://38.191.42.12:4200")
@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String numeroDocumento, @RequestParam String password,   String cargo) {
        return authService.authenticate(numeroDocumento, password,cargo);
    }
}