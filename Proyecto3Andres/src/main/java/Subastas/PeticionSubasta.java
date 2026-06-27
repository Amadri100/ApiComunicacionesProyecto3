/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import Observers.Peticion;

/**
 *
 * @author andre
 */
public class PeticionSubasta extends Peticion{
    private int bid;
    private String identidad;
    public PeticionSubasta(int bid, String id) {
        super();
        this.bid = bid;
        this.identidad = id;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getIdentidad() {
        return identidad;
    }

    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }
    
    
}
