package Mensajes;

import Observers.Datos;

//Mensaje que manda los datos de un Observable
public class MensajeDatos extends Mensaje{
    private Datos datosDelMensaje;
    
    public MensajeDatos(String IDMandado, String IDEvento, Datos datos) {
        super(IDMandado, IDEvento, TiposMensaje.MandarDatos);
        this.datosDelMensaje = datos;
    }

    public boolean permitidoPorUsuario() {
        return this.datosDelMensaje.getPeticion() != null;
    }
    
    public Datos getDatosDelMensaje() {
        return datosDelMensaje;
    }

    public void setDatosDelMensaje(Datos datosDelMensaje) {
        this.datosDelMensaje = datosDelMensaje;
    }

 
    
    
}
