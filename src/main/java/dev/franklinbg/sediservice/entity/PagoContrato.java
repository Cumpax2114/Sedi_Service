package dev.franklinbg.sediservice.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PagoContrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
    @ManyToOne(optional = false)
    private Contrato contrato;
    @ManyToOne(optional = false)
    private Caja caja;
    @Column(length = 11,nullable = false)
    private int idTipoPago;
    @ManyToOne(optional = false)
    private MetodoPago metodoPago;
    @Column(length = 2,nullable = false)
    private int numeroCuota;
    @Column(nullable = false)
    private Date fechaPago;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double montoPagado;
}
