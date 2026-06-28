package Comunicaciones;

import java.io.IOException;
import java.net.Socket;

//Maneja el aceptar conexiones y el manejo de errores
public class ThreadConexiones extends Thread  {
        
    private Servidor servidor;

    private int contadorConexiones = 0;
    private boolean isRunning = true;

    public ThreadConexiones(Servidor servidor) {
        this.servidor = servidor;
    }
    
    @Override
    public void run() {
        while(isRunning) {
            Socket socket = null;
            try {
                socket = servidor.getSocketServidor().accept();
                servidor.agregarEspera(socket, this.contadorConexiones++);
            } catch (IOException ex) {
                servidor.getInterfaz().escribirTexto("ERROR: " + ex.getMessage() );
            }
            
        }
    }
    
    
    public void liberarConexiones() {
        this.contadorConexiones--;
    }
    
    
    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public int getContadorConexiones() {
        return contadorConexiones;
    }

    public void setContadorConexiones(int contadorConexiones) {
        this.contadorConexiones = contadorConexiones;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    
    
}
