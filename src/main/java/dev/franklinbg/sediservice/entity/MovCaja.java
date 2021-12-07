package dev.franklinbg.sediservice.entity;

import javax.persistence.*;

@Entity
public class MovCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
    @ManyToOne(optional = false)
    private Caja caja;
    @ManyToOne(optional = false)
    private Usuario usuario;
    @Column(length = 1, nullable = false)
    private char tipoMov;
    @ManyToOne(optional = false)
    private ConceptoMovCaja conceptoMovCaja;
    @ManyToOne(optional = false)
    private MetodoPago metodoPago;
    @Column(length = 11, nullable = false)
    private int idTrabajador;
    @Column(length = 11)
    private String nombre;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double total;
    @Column(length = 250, nullable = false)
    private String descripcion;
    @Column(length = 1, nullable = false)
    private char estado;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public char getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(char tipoMov) {
        this.tipoMov = tipoMov;
    }

    public ConceptoMovCaja getConceptoMovCaja() {
        return conceptoMovCaja;
    }

    public void setConceptoMovCaja(ConceptoMovCaja conceptoMovCaja) {
        this.conceptoMovCaja = conceptoMovCaja;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }
}
