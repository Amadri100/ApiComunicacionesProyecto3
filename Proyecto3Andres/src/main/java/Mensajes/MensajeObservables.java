package Mensajes;

import Observers.Datos;

//Genera un Observable
public class MensajeObservables extends Mensaje {
    //ID Objetivo =  Crear: Codigo base Destruir Codigo especifico
    private boolean caso;
    private String nombre; 
    private Datos datos;
    //Maneja Observables   ( booleano TRUE(Crear) ; FALSE(Destruir)                 }
    public MensajeObservables(String IDMandado, String base, String nombre, boolean caso, Datos datos) {
        super(IDMandado, base, TiposMensaje.CrearObservable);
        this.caso = caso;
        this.nombre = nombre;
        this.datos = datos;
    }
    public MensajeObservables(String IDMandado, String IDObjetivo, String nombre, boolean caso) {
        super(IDMandado, IDObjetivo, TiposMensaje.CrearObservable);
        this.caso = caso;
        this.nombre = nombre;
        this.datos = null;
    }

    public boolean isCaso() {
        return caso;
    }

    public void setCaso(boolean caso) {
        this.caso = caso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }
    
    
    
}
