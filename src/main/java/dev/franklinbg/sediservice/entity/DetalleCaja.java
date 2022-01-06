package dev.franklinbg.sediservice.entity;

import javax.persistence.*;

@Entity
public class DetalleCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
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
}
