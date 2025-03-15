package com.example.BackendSocrates.model;

import jakarta.persistence.*;

@Entity(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;


    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")  // Corregida la tilde
    private String direccion;


    @Column(name = "correo")
    private String correo;

    @Column(name = "estado")
    private Boolean estado; // Cambio de `boolean` a `Boolean`

    @Column(name = "cargo") // Corregido `@JoinColumn`
    private String cargo;

    public Persona() {}

    public Persona(long id, String nombre, String numeroDocumento, String telefono, String direccion, String correo, Boolean estado, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.numeroDocumento = numeroDocumento;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.estado = estado;
        this.cargo = cargo;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
