package com.example.BackendSocrates.model;

import jakarta.persistence.*;

@Entity(name = "administradores")
public class Administrador { //FALTA PONER EL EXTENDS DE PERSONA

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "nivel_jerarquico")
    private String nivelJerarquico;

    // Constructor vacío
    public Administrador() {
    }

    // Constructor con todos los parámetros
    public Administrador(long id, String departamento, String nivelJerarquico) {
        this.id = id;
        this.departamento = departamento;
        this.nivelJerarquico = nivelJerarquico;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNivelJerarquico() {
        return nivelJerarquico;
    }

    public void setNivelJerarquico(String nivelJerarquico) {
        this.nivelJerarquico = nivelJerarquico;
    }
}
