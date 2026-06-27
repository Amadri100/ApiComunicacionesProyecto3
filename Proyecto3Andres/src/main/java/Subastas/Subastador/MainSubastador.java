/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas.Subastador;

/**
 *
 * @author andre
 */
public class MainSubastador {
    public static void main(String[] args) {
         InterfazSubastador interfaz = new InterfazSubastador();
         DatosSubastador datos = new DatosSubastador(interfaz);
         interfaz.setSubastador(datos);
         interfaz.setVisible(true);
    }
}
