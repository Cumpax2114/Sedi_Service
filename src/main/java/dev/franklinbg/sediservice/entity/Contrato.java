package dev.franklinbg.sediservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
    @NotNull
    @ManyToOne(optional = false)
    private TipoContrato tipoContrato;
    @NotNull
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Lima")
    @NotNull(message = "la fecha de inicio no puede estar vacía")
    @Column(nullable = false)
    private Date fechaInicio;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Lima")
    @NotNull(message = "la fecha de término no puede estar vacía")
    @Column(nullable = false)
    private Date fechaTermino;
    @Min(value = 1, message = "el total de contrato no puede ser 0")
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double totalContrato;
    @Min(value = 1, message = "debe de haber por lo menos una cuota")
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double cuotaMensual;
    @Column(nullable = false, length = 11)
    @Min(value = 1, message = "el total por cuotas no puede ser 0")
    @Max(message = "solo 24 cuotas como máximo", value = 24)
    private int totalCuotas;
    @Column(nullable = false)
    private int cuotasPagadas;
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

    public int getCuotasPagadas() {
        return cuotasPagadas;
    }

    public void setCuotasPagadas(int cuotasPagadas) {
        this.cuotasPagadas = cuotasPagadas;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
}
