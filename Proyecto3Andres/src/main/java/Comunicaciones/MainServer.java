package Comunicaciones;

import java.net.InterfaceAddress;

/**
 *
 * @author andre
 */
public class MainServer {
    public static void main(String[] args) {
        Interfaz interfaz = new Interfaz();
        Servidor server = new Servidor(interfaz);
        interfaz.setVisible(true);
    }
}
