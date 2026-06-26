/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author andre
 */
public class DatosSubasta {
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
    
}
