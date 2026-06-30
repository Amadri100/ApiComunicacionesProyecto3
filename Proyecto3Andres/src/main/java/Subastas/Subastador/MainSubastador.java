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
