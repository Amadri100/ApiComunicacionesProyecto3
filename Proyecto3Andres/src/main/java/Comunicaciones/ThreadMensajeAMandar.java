package Comunicaciones;

import java.io.IOException;
import java.net.Socket;

//Mandar mensajes constantemente (Lista de datos base para el display)
public class ThreadMensajeAMandar extends Thread {
    private Servidor servidor;
    private boolean  corre = true;

    public ThreadMensajeAMandar(Servidor servidor) {
        this.servidor = servidor;
    }

     @Override
    public void run() {
        while(corre) {
            try {
                servidor.mandarDatos();
                sleep(100);
            } catch (Exception ex) {
                servidor.getInterfaz().escribirTexto("ERROR: " + ex.getMessage());
            }
        }
    }
    
    public void parar() {
        this.corre = false;
    }
    
    public boolean isCorre() {
        return corre;
    }

    public void setCorre(boolean corre) {
        this.corre = corre;
    }
    

    
    
    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }
    
    
}
