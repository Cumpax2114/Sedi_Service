package dev.franklinbg.sediservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class MovCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private int id;
    @ManyToOne
    private Apertura apertura;
    @NotNull
    @ManyToOne(optional = false)
    private Caja caja;
    @NotNull
    @ManyToOne(optional = false)
    private Usuario usuario;
    @Column(length = 1, nullable = false)
    private char tipoMov;
    @NotNull
    @ManyToOne(optional = false)
    private ConceptoMovCaja conceptoMovCaja;
    @NotNull
    @ManyToOne(optional = false)
    private MetodoPago metodoPago;
    @ManyToOne
    private Usuario trabajador;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    private Proveedor proveedor;
    @Column(columnDefinition = "DECIMAL(11,2)", nullable = false)
    private double total;
    @NotBlank
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

    public Apertura getApertura() {
        return apertura;
    }

    public void setApertura(Apertura apertura) {
        this.apertura = apertura;
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

    public Usuario getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Usuario trabajador) {
        this.trabajador = trabajador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
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

    public String getNombrePersona() {
        if (cliente != null) {
            return cliente.getNombre();
        } else if (proveedor != null) {
            return proveedor.getNombre();
        } else if (trabajador != null) {
            return trabajador.getNombre();
        } else {
            return "no name";
        }
    }

    public String getNombreMetodoPago() {
        if (metodoPago != null) {
            return metodoPago.getNombre();
        } else {
            return "null";
        }
    }
}
