package Mensajes;

//Notifica al usuario su nueva ID
public class MensajeNotificacion extends Mensaje {
    //Objetivo = Id actual
    public MensajeNotificacion(String IDObjetivo) {
        super("SERVER", IDObjetivo, TiposMensaje.NotificacionUsurio);
    }
    
}
