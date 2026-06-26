package Mensajes;

//Pide subscripcion o desupscripcion
//( booleano TRUE(Sub) ; FALSE(deSub) 
public class MensajeSuscripcion extends Mensaje {
    private boolean caso;

    public MensajeSuscripcion(String IDMandado, String IDObjetivo, boolean caso) {
        super(IDMandado, IDObjetivo, TiposMensaje.Subscripciones);
        this.caso = caso;
    }

    public boolean isCaso() {
        return caso;
    }

    public void setCaso(boolean caso) {
        this.caso = caso;
    }
    
}
