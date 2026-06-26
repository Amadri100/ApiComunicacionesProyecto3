package Observers;

import Mensajes.MensajeDatos;

//Observer que puede crear y manejar el observable
public class ObserverAdmin extends IObserver {
      public ObserverAdmin(String Id, Observable observable) {
        super(Id, observable);
    }

    @Override
    public void notificar() {
        if(super.getObservable().getDatos().getPeticion() != null) { //Solo le notifica si hay una peticion
            MensajeDatos msg = new MensajeDatos(this.getId(), this.getObservable().getIdEvento(), this.getObservable().getDatos());
            this.getObservable().getServidor().mandarMensaje(this.getId(), msg);
        }
    }
}
