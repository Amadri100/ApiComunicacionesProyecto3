package Observers;

import Mensajes.MensajeDatos;

// clase abstracta que representa un observador
public abstract class IObserver {
    private String Id;
    private Observable observable;

    public IObserver(String Id, Observable observable) {
        this.Id = Id;
        this.observable = observable;
    }
    
    //Metodo usado para notificar si termina inesperadamente
    public void notificarSiempre() {
        MensajeDatos msg = new MensajeDatos(this.getId(), this.getObservable().getIdEvento(), this.getObservable().getDatos());
        this.getObservable().getServidor().mandarMensaje(this.getId(), msg);
    }
    
    public abstract void notificar();

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public Observable getObservable() {
        return observable;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }
    
    
}
