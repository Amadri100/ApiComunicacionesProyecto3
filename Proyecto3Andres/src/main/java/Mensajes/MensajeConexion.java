package Mensajes;

//Manejar que tipo de conexion quiere obtener
public class MensajeConexion extends Mensaje {
    private boolean conexion; //TRUE =  Obsevable
                              //FALSE = Observador 
    public MensajeConexion(boolean conexion) {
        super("NA", "NA", TiposMensaje.ConectarseServidor);
        this.conexion = conexion;
    }

    public boolean isConexion() {
        return conexion;
    }

    public void setConexion(boolean conexion) {
        this.conexion = conexion;
    }
    
}
