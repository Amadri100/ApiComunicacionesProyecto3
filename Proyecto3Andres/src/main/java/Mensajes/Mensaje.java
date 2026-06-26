package Mensajes;

 
import java.io.Serializable;

public class Mensaje implements Serializable {
    private String IDMandado; //"NA" -> no la conoce
    private String IDObjetivo; //"NA"  -> no se realaciona a un evento (MSG servidor)
    private TiposMensaje tipo;

    public Mensaje(String IDMandado, String IDObjetivo, TiposMensaje tipo) {
        this.IDMandado = IDMandado;
        this.IDObjetivo = IDObjetivo;
        this.tipo = tipo;
    }

    public String getIDMandado() {
        return IDMandado;
    }

    public void setIDMandado(String IDMandado) {
        this.IDMandado = IDMandado;
    }

    public String getIDObjetivo() {
        return IDObjetivo;
    }

    public void setIDObjetivo(String IDObjetivo) {
        this.IDObjetivo = IDObjetivo;
    }

    public TiposMensaje getTipo() {
        return tipo;
    }

    public void setTipo(TiposMensaje tipo) {
        this.tipo = tipo;
    }
    
    
    
}
