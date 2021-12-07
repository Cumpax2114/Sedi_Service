package dev.franklinbg.sediservice.entity;

import javax.persistence.*;

@Entity
public class MetodoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
    @Column(length = 11, nullable = false)
    private int TipoPago;
    @Column(length = 90, nullable = false)
    private String nombre;
    @Column(length = 100, nullable = false)
    private String numeroCuenta;
    @Column(length = 100, nullable = false)
    private String cci;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double monto;
    @Column(length = 1, nullable = false)
    private char estado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoPago() {
        return TipoPago;
    }

    public void setTipoPago(int tipoPago) {
        TipoPago = tipoPago;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getCci() {
        return cci;
    }

    public void setCci(String cci) {
        this.cci = cci;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
}
