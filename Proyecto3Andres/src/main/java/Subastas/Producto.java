/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import ImagenSerializable.ImagenSerializable;
import java.io.Serializable;

/**
 *
 * @author andre
 */
public class Producto implements Serializable {
    private int precioFinal;
    private int precioInicial;
    private String descripcion;
    private ImagenSerializable imagen;

    public Producto(int precioFinal, int precioInicial, ImagenSerializable imagen) {
        this.precioFinal = precioFinal;
        this.precioInicial = precioInicial;
        this.imagen = imagen;
    }
    
    public int getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(int precioFinal) {
        this.precioFinal = precioFinal;
    }

    public int getPrecioInicial() {
        return precioInicial;
    }

    public void setPrecioInicial(int precioInicial) {
        this.precioInicial = precioInicial;
    }

    public ImagenSerializable getImagen() {
        return imagen;
    }

    public void setImagen(ImagenSerializable imagen) {
        this.imagen = imagen;
    }
    
    
    
}
