package dev.franklinbg.sediservice.entity;

import javax.persistence.*;

@Entity
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 20, unique = true)
    private String documento;
    @Column(nullable = false, length = 250)
    private String nombre;
    @Column(nullable = false, length = 150)
    private String direccion;
    @Column(length = 150,nullable = false)
    private String ubigeo;
    @Column(length = 20,nullable = false)
    private String telefono;
    @Column(length = 100)
    private String correo;
    @Column(length = 1,nullable = false)
    private char estado;
    @Column(columnDefinition = "DECIMAL(11,2)",nullable = false)
    private double monto_compra;
}
