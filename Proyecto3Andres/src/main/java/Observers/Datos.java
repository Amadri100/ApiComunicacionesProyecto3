package Observers;
import java.io.Serializable;

//Datos del evento
//
//
//
public class Datos implements Serializable{
    private String Identificador;
    private Peticion peticion;
    private String nombre;
    private int contadorObservadores = 0;
    private boolean enCurso = true; //Indica si se ejecuta: false -> ya no existe

    public Datos(String Identificador, Peticion peticion, String nombre) {
        this.Identificador = Identificador;
        this.peticion = peticion;
        this.nombre = nombre;
    }
    public Datos(String Identificador, String nombre) {
        this.Identificador = Identificador;
        this.peticion = null;
        this.nombre = nombre;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getContadorObservadores() {
        return contadorObservadores;
    }

    public void setContadorObservadores(int contadorObservadores) {
        this.contadorObservadores = contadorObservadores;
    }

    
    
}
