package Observers;
import java.io.Serializable;

//Datos del evento
//
//
//
public class Datos implements Serializable{
    private String Identificador;
    private Peticion peticion;
    private boolean enCurso = true; //Indica si se ejecuta: false -> ya no existe

    public Datos(String Identificador, Peticion peticion) {
        this.Identificador = Identificador;
        this.peticion = peticion;
    }
    public Datos(String Identificador) {
        this.Identificador = Identificador;
        this.peticion = null;
    }

    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String Identificador) {
        this.Identificador = Identificador;
    }

    public Peticion getPeticion() {
        return peticion;
    }

    public void setPeticion(Peticion peticion) {
        this.peticion = peticion;
    }

    public boolean isEnCurso() {
        return enCurso;
    }

    public void setEnCurso(boolean enCurso) {
        this.enCurso = enCurso;
    }

}
