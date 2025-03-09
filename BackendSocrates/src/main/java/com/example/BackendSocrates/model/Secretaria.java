package com.example.BackendSocrates.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "secretarias")
public class Secretaria { //FALTA PONER EL EXTENDS DE PERSONA

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fecha_contratacion")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    //SE PUSO ESA ANOTACION PARA QUE EL FORMATO DE LA FECHA COINCIDA CON LA FECHA DE LAS PRUEBAS UNITARIAS
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
