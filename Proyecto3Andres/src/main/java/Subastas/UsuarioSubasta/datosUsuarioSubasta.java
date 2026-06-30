package Subastas.UsuarioSubasta;

import Mensajes.Mensaje;
import Mensajes.MensajeDatos;
import Mensajes.MensajeNotificacion;
import Mensajes.MensajeObservables;
import Mensajes.MensajeSuscripcion;
import Mensajes.MensajeTodosLosDatos;
import static Mensajes.TiposMensaje.ConectarseServidor;
import static Mensajes.TiposMensaje.CrearObservable;
import static Mensajes.TiposMensaje.MandarDatos;
import static Mensajes.TiposMensaje.NotificacionUsurio;
import static Mensajes.TiposMensaje.Subscripciones;
import static Mensajes.TiposMensaje.TodosLosDatos;
import Observers.Datos;
import Subastas.DatosSubasta;
import static Subastas.DatosSubasta.estadoSubasta.CANCELADA;
import static Subastas.DatosSubasta.estadoSubasta.CERRADA;
import static Subastas.DatosSubasta.estadoSubasta.VENDIDA;
 
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author andre
 */
public class datosUsuarioSubasta {
    private final String baseCodigo = "SU";
    private usuarioSubastaPrincipal interfaz;
    private String identificadorUsuario = "NA";
    private HashMap<String, DatosSubasta> datos;
    private ThreadUsuarioSubasta servidor = null;
    
    
    public datosUsuarioSubasta(usuarioSubastaPrincipal interfaz) {
        this.interfaz = interfaz;
        this.datos = new HashMap<>();
    }
    
    public String mensaje(String IdEvento) {
        DatosSubasta data = datos.get(IdEvento);
        String mensaje = "";
        switch(data.getEstatus()){
            case CANCELADA:
                mensaje = "Se cancelo la subasta";
                break;
            case VENDIDA:
                if (data.getPostor().equals(this.identificadorUsuario)) {
                    mensaje = "Felicidades ganaste la subasta; se vendio a: $" + data.getLimiteActual();
                } 
                else {
                    mensaje = "No ganaste la subasta; se vendio a: $" + data.getLimiteActual();
                }
                
                break;
        }
        return mensaje;
    }
    
    public boolean conectar() {
        if (servidor == null){
            try {
                this.servidor = new ThreadUsuarioSubasta(this);
                return true;
            }
            catch (Exception e) {
                PanelConectarUsuario panel = (PanelConectarUsuario) this.getInterfaz().getPanelConectar();
                panel.mostrarTexto("ERROR: " + e.toString());
                this.limpiar();
            }
        }
        return false;
    }
    

     
    public void accionMensaje(Mensaje msg) {
        switch(msg.getTipo()) {
            case ConectarseServidor: /*{NO RECIBE}*/ break;
            case CrearObservable: /*{NO RECIBE}*/break;
            case MandarDatos:
                MensajeDatos msg1 = (MensajeDatos)msg;
                DatosSubasta datosMsg = (DatosSubasta)msg1.getDatosDelMensaje();
                System.out.println("[CLIENTE] MandarDatos recibido, id: " + datosMsg.getIdentificador());                
                this.datos.put(datosMsg.getIdentificador(), datosMsg);

                this.interfaz.agregarDatosPanel(datosMsg);
                break;
            case NotificacionUsurio:
                if (identificadorUsuario.equals("NA")) { //Si no tiene ID la guarda
                    MensajeNotificacion msg2 = (MensajeNotificacion)msg;
                    this.identificadorUsuario = msg2.getIDObjetivo();  
                }
                break;
            case Subscripciones: /*{NO RECIBE}*/break;
            case TodosLosDatos: 
                
                MensajeTodosLosDatos msg3 = (MensajeTodosLosDatos)msg;
                this.interfaz.actualizarPanelesSeleccion(obtenerListadoValidos(msg3.getDatos()));
                
                break;
        }
    }
     
    
    
    public HashMap<String, DatosSubasta> obtenerListadoValidos(ArrayList<Datos> lista) {
        HashMap<String, DatosSubasta> mapa = new HashMap<>();
        for (Datos dato : lista) {
            if(!dato.getIdentificador().matches(baseCodigo+"\\d+")) { //Revisa si es una subasta
                continue;
            }
            if (this.datos.get(dato.getIdentificador()) != null) {
                continue;
            }
            DatosSubasta convetido = (DatosSubasta)dato;
            mapa.put(dato.getIdentificador(), convetido);
        }
        return mapa;
    }
    
    public void suscribirse(String identificacion) {
        MensajeSuscripcion msg = new MensajeSuscripcion(this.identificadorUsuario, identificacion, true);
        servidor.mandarMensaje(msg);
    }
    public void desuscribirse(String identificacion) {
        MensajeSuscripcion msg = new MensajeSuscripcion(this.identificadorUsuario, identificacion, false);
        servidor.mandarMensaje(msg);
        this.datos.remove(identificacion);
    }
    
    
    public void limpiar() {
        this.identificadorUsuario = "NA";
        this.datos = null;
        this.servidor = null;
        this.interfaz.resetAConectar();
    }

    public usuarioSubastaPrincipal getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(usuarioSubastaPrincipal interfaz) {
        this.interfaz = interfaz;
    }

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public void setIdentificadorUsuario(String identificadorUsuario) {
        this.identificadorUsuario = identificadorUsuario;
    }

    public HashMap<String, DatosSubasta> getDatos() {
        return datos;
    }

    public void setDatos(HashMap<String, DatosSubasta> datos) {
        this.datos = datos;
    }

   
    public ThreadUsuarioSubasta getServidor() {
        return servidor;
    }

    public void setServidor(ThreadUsuarioSubasta servidor) {
        this.servidor = servidor;
    }
}
