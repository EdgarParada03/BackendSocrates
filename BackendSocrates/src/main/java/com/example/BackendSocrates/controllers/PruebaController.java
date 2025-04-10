package com.example.BackendSocrates.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class PruebaController {

    @GetMapping("/prueba")
    public String prueba() {
        return "Hola desde el backend en Render";
    }
}
