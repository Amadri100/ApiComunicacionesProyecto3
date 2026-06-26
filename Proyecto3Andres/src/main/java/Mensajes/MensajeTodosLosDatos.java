package Mensajes;

import Observers.Datos;
import java.util.ArrayList;

//Notifica a los clientes sobre las opciones de datos(subastas o celebridades etc) que tienen
public class MensajeTodosLosDatos extends Mensaje{
    private ArrayList<Datos> datos;

    public MensajeTodosLosDatos(ArrayList<Datos> datos) {
        super("Server", "NA", TiposMensaje.TodosLosDatos);
        this.datos = datos;
    }

    public ArrayList<Datos> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<Datos> datos) {
        this.datos = datos;
    }
    
    
    
}
