package com.example.BackendSocrates.controllers;

import com.example.BackendSocrates.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {""})
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String numeroDocumento, @RequestParam String password) {
        return authService.authenticate(numeroDocumento, password);
    }
}