/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import Observers.Datos;
import Observers.Peticion;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author andre
 */
public class DatosSubasta extends Datos {
    public enum estadoSubasta {
        CERRADA,
        VENDIDA,
        CANCELADA;
    }
    private final int duracion = 5*60; //Segundo 5mins
    private Instant inicioCronometro = null;
    private Instant finalCronometro = null;
    private Producto producto;
    private String historial;
    private estadoSubasta estatus;
    private int limiteActual;
    private String postor = "NA";

    public DatosSubasta(Producto producto, String nombre) {
        super("NA", nombre);
        this.producto = producto;
        this.historial = "";
        this.estatus = estadoSubasta.CERRADA;
        this.limiteActual = this.producto.getPrecioInicial();
        this.obtenerTiempos();
    }
    
    private void obtenerTiempos() {
        this.inicioCronometro = Instant.now();
        this.finalCronometro = inicioCronometro.plusSeconds(duracion);
    }
    
    public LocalDateTime inicioEnLocal() {
        return LocalDateTime.ofInstant(inicioCronometro, ZoneId.systemDefault());
    }
    
    public LocalDateTime finalEnLocal() {
        return LocalDateTime.ofInstant(finalCronometro, ZoneId.systemDefault());
    }

    public Instant getInicioCronometro() {
        return inicioCronometro;
    }

    public void setInicioCronometro(Instant inicioCronometro) {
        this.inicioCronometro = inicioCronometro;
    }

    public Instant getFinalCronometro() {
        return finalCronometro;
    }

    public void setFinalCronometro(Instant finalCronometro) {
        this.finalCronometro = finalCronometro;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public estadoSubasta getEstatus() {
        return estatus;
    }

    public void setEstatus(estadoSubasta estatus) {
        this.estatus = estatus;
    }

    public int getLimiteActual() {
        return limiteActual;
    }

    public void setLimiteActual(int limiteActual) {
        this.limiteActual = limiteActual;
    }

    public String getPostor() {
        return postor;
    }

    public void setPostor(String postor) {
        this.postor = postor;
    }
    
}
