package Observers;

import Mensajes.MensajeDatos;


//Observable regular
public class ObserverUsuario extends IObserver{

    public ObserverUsuario(String Id, Observable observable) {
        super(Id, observable);
    }

    @Override
    public void notificar() {

        if(super.getObservable().getDatos().getPeticion() == null) { //Peticion == NULL -> definitivo
            MensajeDatos msg = new MensajeDatos(this.getId(), this.getObservable().getIdEvento(), this.getObservable().getDatos());
            this.getObservable().getServidor().mandarMensaje(this.getId(), msg);
            System.out.println("[SERVIDOR] ObserverUsuario notificando a: " + this.getId());

        }
        else {
            System.out.println("[SERVIDOR] ObserverUsuario NO notifica, hay peticion pendiente");
        }
            }
}
