package dev.franklinbg.sediservice.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
    @ManyToOne(optional = false)
    private TipoContrato tipoContrato;
    @ManyToOne(optional = false)
    private Cliente cliente;
    @Column(nullable = false)
    private Date fechaInicio;
    @Column(nullable = false)
    private Date fechaTermino;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double totalContrato;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double cuotaMensual;
    @Column(nullable = false, length = 11)
    private int totalCuotas;
    @Column(length = 1, nullable = false)
    private char estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public double getTotalContrato() {
        return totalContrato;
    }

    public void setTotalContrato(double totalContrato) {
        this.totalContrato = totalContrato;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public int getTotalCuotas() {
        return totalCuotas;
    }

    public void setTotalCuotas(int totalCuotas) {
        this.totalCuotas = totalCuotas;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
}
