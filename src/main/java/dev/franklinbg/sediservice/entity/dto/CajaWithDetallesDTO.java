package dev.franklinbg.sediservice.entity.dto;
import dev.franklinbg.sediservice.entity.DetalleCaja;

import java.util.ArrayList;

public class CajaWithDetallesDTO {
    private int idCaja;
    private ArrayList<DetalleCaja> detalles;

    public int getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }

    public ArrayList<DetalleCaja> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetalleCaja> detalles) {
        this.detalles = detalles;
    }
}
