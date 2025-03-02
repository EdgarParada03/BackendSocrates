package com.example.BackendSocrates.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "secretarias")
public class Secretaria { //FALTA PONER EL EXTENDS DE PERSONA

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fecha_contratacion")
    private Date fechaContratacion;

    public Secretaria() {
    }

    public Secretaria(long id, Date fechaContratacion) {
        this.id = id;
        this.fechaContratacion = fechaContratacion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
}
