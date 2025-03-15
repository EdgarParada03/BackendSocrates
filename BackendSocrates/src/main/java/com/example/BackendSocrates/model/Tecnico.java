package com.example.BackendSocrates.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "tecnicos")
@PrimaryKeyJoinColumn(name = "id")
public class Tecnico extends Persona {

    @Column(name = "especialidad")
    private String especialidad;

    public Tecnico() {}

    public Tecnico(long id, String nombre, String tipoDocumento, String numeroDocumento, String telefono, String direccion, String correo, Boolean estado, String sexo, String cargo, String especialidad) {
        super(id, nombre, tipoDocumento, numeroDocumento, telefono, direccion, correo, estado, sexo, cargo);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
