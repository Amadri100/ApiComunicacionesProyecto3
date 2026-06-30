package Subastas.UsuarioSubasta;

/**
 *
 * @author andre
 */
public class mainClienteSubasta {
    public static void main(String[] args) {
        usuarioSubastaPrincipal interfaz = new usuarioSubastaPrincipal();
        datosUsuarioSubasta datos = new datosUsuarioSubasta(interfaz);
        interfaz.setDatos(datos);
        interfaz.setVisible(true);
    }
 
}