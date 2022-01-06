package dev.franklinbg.sediservice.entity;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
    @NotBlank
    @Column(nullable = false, length = 20, unique = true)
    private String documento;
    @NotBlank
    @Column(nullable = false, length = 250)
    private String nombre;
    @NotBlank
    @Column(nullable = false, length = 150)
    private String direccion;
    @NotBlank
    @Column(length = 150, nullable = false)
    private String ubigeo;
    @NotBlank
    @Column(length = 20, nullable = false)
    private String telefono;
    @Column(length = 100)
    private String correo;
    @Column(length = 1, nullable = false)
    private char estado;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double monto_compra;

    public Cliente() {
        this.estado = 'A';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public double getMonto_compra() {
        return monto_compra;
    }

    public void setMonto_compra(double monto_compra) {
        this.monto_compra = monto_compra;
    }
}
