package Observers;

import Comunicaciones.Servidor;
import java.io.Serializable;
import java.util.ArrayList;


//El identificador es IDevento, el nombre es estetico
public class Observable implements Serializable {
    private String IdEvento;
    private String nombre; //Meramente visual
    private Servidor servidor;
    private ArrayList<IObserver> observables;
    private Datos datos = null;

    public Observable(Servidor servidor, Datos datos, String IdEvento, String nombre) {
        this.servidor = servidor;
        this.observables = new ArrayList<>();
        this.datos = datos;
        this.IdEvento = IdEvento;
        this.datos.setIdentificador(IdEvento);
        this.nombre = nombre;
    }
    public Observable(Servidor servidor, Datos datos, String IdEvento) {
        this.servidor = servidor;
        this.observables = new ArrayList<>();
        this.datos = datos;
        this.IdEvento = IdEvento;
        this.datos.setIdentificador(IdEvento);
        this.nombre = "NAN";
    }
    
    public void terminar() {
        this.datos.setEnCurso(false);
        notificarPrioridad();
    }
    
    public void recibirDatos(Datos datos) {
        this.datos = datos;
    }
    
    public void agregarObservable(IObserver observador) {
        this.observables.add(observador);
    }
    
    public void eliminarObserver(IObserver observador) {
        int i = this.observables.indexOf(observador);
        if (i != -1)
            this.observables.remove(i);
    }
    public boolean eliminarObserver(String identificacion) {
        boolean elimino = false;
        for (int i = 0; i < this.observables.size(); i++) {
            if (this.observables.get(i).getId().equals(identificacion)) {
                this.observables.remove(i);
                elimino = true;
            }
        }
        return elimino;
    }
    public void notificarTodos() {
        for (int i = 0; i < this.observables.size(); i++) {
            observables.get(i).notificar();
        }
    }
    
    public void notificarPrioridad() {
       for (int i = 0; i < this.observables.size(); i++) {
            observables.get(i).notificarSiempre();
        } 
    }
        

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public ArrayList<IObserver> getObservables() {
        return observables;
    }

    public void setObservables(ArrayList<IObserver> observables) {
        this.observables = observables;
    }

    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }

    public String getIdEvento() {
        return IdEvento;
    }

    public void setIdEvento(String IdEvento) {
        this.IdEvento = IdEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
