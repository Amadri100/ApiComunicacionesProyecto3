package Comunicaciones;

import Mensajes.Mensaje;
import Mensajes.MensajeConexion;
import Mensajes.MensajeNotificacion;
import Mensajes.TiposMensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public abstract class BaseLadoCliente extends Thread {
    public enum TipoCliente {
        Usuario,
        Admin;
    }
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private TipoCliente tipo;
    private String Ip = "localhost";
    private int Port = 33332;
    private String identificacion;
    private boolean estaConectado = false;
    private boolean isRunning = true;

    public BaseLadoCliente(TipoCliente tipo) throws IOException {
        this.socket = abrirConexion();
        this.salida = new ObjectOutputStream(socket.getOutputStream());
        this.entrada = new ObjectInputStream(socket.getInputStream());
        this.tipo = tipo;
        this.start();
        this.solicitarConexion();
        
        
    }
        
    public Socket abrirConexion() throws IOException {
        System.out.println("Intenta");
        Socket nueva = new Socket(this.Ip, this.Port);
        System.out.println("Abre conexion");
        return nueva;
    }
    
    public void limpiar() {
        try {
            salida.flush();
            salida.close();     
            entrada.close();
            socket.close();
        } catch (IOException ex) {
            //Nada
        }
        isRunning = false;
        
        
    }
    
    public abstract void avisarError(Exception e);
    public abstract void accionConMensaje(Mensaje msg);
    
    public void accionPrevioConexion(Mensaje msg) {
        if (msg.getTipo() == TiposMensaje.NotificacionUsurio) {
            MensajeNotificacion msgNoti = (MensajeNotificacion)msg;
            this.identificacion = msgNoti.getIDObjetivo();
            this.estaConectado = true;
        }
    }
        
    
    @Override
    public void run() {
        while (isRunning) {
            try {
                Mensaje mensaje = (Mensaje) entrada.readObject();
                System.out.println("Recibe mensaje: " + mensaje.getTipo());
                if (this.estaConectado)
                    accionConMensaje(mensaje);
                else 
                    accionPrevioConexion(mensaje);
                sleep(10);
            }
            catch (Exception e) {
                avisarError(e);    
                continue;
            }
            
        }
    }
    
    public void solicitarConexion() {
        boolean caso = this.tipo == TipoCliente.Admin;  
        MensajeConexion msg = new MensajeConexion(caso);
        mandarMensaje(msg);
    }
    
    public void mandarMensaje(Mensaje mensaje) {
        try { 
            salida.writeObject(mensaje);
            salida.flush();
        } catch (IOException ex) {
            avisarError(ex);
        }
        
    }    

    //Getter Setters
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectInputStream getEntrada() {
        return entrada;
    }

    public void setEntrada(ObjectInputStream entrada) {
        this.entrada = entrada;
    }

    public ObjectOutputStream getSalida() {
        return salida;
    }

    public void setSalida(ObjectOutputStream salida) {
        this.salida = salida;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    /*public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }*/

    public String getIp() {
        return Ip;
    }

    public void setIp(String Ip) {
        this.Ip = Ip;
    }

    public int getPort() {
        return Port;
    }

    public void setPort(int Port) {
        this.Port = Port;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public boolean isEstaConectado() {
        return estaConectado;
    }

    /*public void setEstaConectado(boolean estaConectado) {
        this.estaConectado = estaConectado;
    }*/
    
    
    

}
