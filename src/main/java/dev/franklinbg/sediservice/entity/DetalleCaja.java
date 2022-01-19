package dev.franklinbg.sediservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class DetalleCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "America/Lima")
    private Date fechaCreacion;
    @ManyToOne(optional = false)
    private Caja caja;
    @ManyToOne(optional = false)
    private MetodoPago metodoPago;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double monto;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double montoCierre;
    @Column(nullable = false)
    private boolean cerrado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(double montoCierre) {
        this.montoCierre = montoCierre;
    }

    public boolean isCerrado() {
        return cerrado;
    }

    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }

    public String getNombreMetodoPago() {
        return metodoPago.getNombre();
    }
}
