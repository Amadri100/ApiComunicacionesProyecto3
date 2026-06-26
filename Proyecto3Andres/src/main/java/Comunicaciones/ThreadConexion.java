package Comunicaciones;

import Mensajes.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//ThreadConexion maneja una conexion con el servidor
public class ThreadConexion extends Thread{
    private Servidor servidor;
    private Socket socket;
    private String identificador = "NA";
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
 
    private boolean isRunning = true;

    public ThreadConexion(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
        try {
            this.salida = new ObjectOutputStream(socket.getOutputStream());
            this.salida.flush();
        } catch (IOException ex) {
           servidor.getInterfaz().escribirTexto("ERROR: " + ex.getMessage());
        }
        try {
            this.entrada = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            servidor.getInterfaz().escribirTexto("ERROR: " + ex.getMessage());
        }
    }
    
    public void limpiar() {
        try {
            salida.flush();
            salida.close();
            entrada.close();
            socket.close();
        } catch (IOException ex) {
            this.servidor.getInterfaz().escribirTexto(ex.getMessage());
        }
        isRunning = false;
    }
    
    @Override
    public void run() {
        while (isRunning) {
            try {
                Mensaje mensaje = (Mensaje) entrada.readObject();

                servidor.recibirMensaje(mensaje, this);
            }
            catch (Exception e) {
                 servidor.getInterfaz().escribirTexto("ERROR: " + e.getMessage());
                 servidor.getInterfaz().escribirTexto("Se cierra la coneccion");
                 servidor.eliminarConexion(this);
            }
            
            try {
                sleep(10);
            } catch (InterruptedException ex) {
               
            }
            
        }
    }
    
    
    //GETTERS SETTERS
    public void mandarMensaje(Mensaje mensaje) {
        try { 
            salida.writeObject(mensaje);
            salida.flush();
        } catch (IOException ex) {
            servidor.getInterfaz().escribirTexto("ERROR: " + ex.getMessage());
        }
        
    }
    
        public void cambiarID(String id) {
        this.identificador = id;
    }

    public boolean mismoIdentificador(String iden) {
        if (this.identificador.equals(iden)){
            return true;
        }
        return false;
    }
    
    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    
     
}
