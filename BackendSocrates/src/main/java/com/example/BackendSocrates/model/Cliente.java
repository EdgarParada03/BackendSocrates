package com.example.BackendSocrates.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "clientes")
public class Cliente extends Persona{   //FALTA PONER EL EXTENDS DE PERSONA

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo_servicio")
    private String tipoServicio;

    @Column(name = "fecha_registro")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date fechaRegistro;

    public Cliente() {
    }


    public Cliente(long id, String nombre, String tipoDocumento, String numeroDocumento, String telefono, String direccion, String correo, Boolean estado, String sexo, String cargo, long id1, String tipoServicio, Date fechaRegistro) {
        super(id, nombre, tipoDocumento, numeroDocumento, telefono, direccion, correo, estado, sexo, cargo);
        this.id = id1;
        this.tipoServicio = tipoServicio;
        this.fechaRegistro = fechaRegistro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
