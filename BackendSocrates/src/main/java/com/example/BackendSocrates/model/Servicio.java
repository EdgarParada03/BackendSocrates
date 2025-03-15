package com.example.BackendSocrates.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fecha_servicio")
    private Date fechaServicio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "hora_servicio")
    private Time horaServicio;

    @Column(name = "estado")
    private String estado;

    @OneToOne //REVISAR ESTA RELACION
    @Column(name = "tipo_plan")
    private TipoPlan tipoPlan;

    @OneToOne //REVISAR ESTA RELACION
    @Column(name = "tecnico")
    private Tecnico tecnico;

    @OneToOne //REVISAR ESTA RELACION
    @Column(name = "cliente")
    private Cliente cliente;

    public Servicio() {
    }

    public Servicio(long id, Date fechaServicio, String descripcion, Time horaServicio, String estado, TipoPlan tipoPlan, Tecnico tecnico, Cliente cliente) {
        this.id = id;
        this.fechaServicio = fechaServicio;
        this.descripcion = descripcion;
        this.horaServicio = horaServicio;
        this.estado = estado;
        this.tipoPlan = tipoPlan;
        this.tecnico = tecnico;
        this.cliente = cliente;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Time getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(Time horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TipoPlan getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(TipoPlan tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
}
