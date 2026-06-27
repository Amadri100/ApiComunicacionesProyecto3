package Subastas.Subastador;


import Mensajes.*;
import Observers.Peticion;
import Subastas.DatosSubasta;
import Subastas.PeticionSubasta;

/**
 *
 * @author andre
 */
public class DatosSubastador {
    private final String baseCodigo = "SU";
    private InterfazSubastador interfaz;
    private String identificadorUsuario = "NA";
    private DatosSubasta datos = null;
    private ThreadSubastador servidor = null;
    
    
    public DatosSubastador(InterfazSubastador interfaz) {
        this.interfaz = interfaz;
    }
    
    public boolean conectar() {
        if (servidor != null){
            try {
                this.servidor = new ThreadSubastador(this);
                return true;
            }
            catch (Exception e) {
                PanelConexionSubastador panel = (PanelConexionSubastador) this.interfaz.getPanelEspecifico(InterfazSubastador.NombrePanelesST.Conexion);
                panel.escribirMensaje("ERROR: " + e.getMessage());
                this.limpiar();
            }
        }
        return false;
    }
    
    public void mandarDatos() {
        this.datos.setPeticion(null);
        MensajeDatos datos  = new MensajeDatos(this.identificadorUsuario, this.datos.getIdentificador(), this.datos);
        servidor.mandarMensaje(datos);
    }
    
    public void accionMensaje(Mensaje msg) {
        switch(msg.getTipo()) {
            case ConectarseServidor: /*{NO RECIBE}*/ break;
            case CrearObservable: /*{NO RECIBE}*/break;
            case MandarDatos: 
                MensajeDatos msg1 = (MensajeDatos)msg;
                if(msg1.permitidoPorUsuario()) {
                     PeticionSubasta peticion = (PeticionSubasta)msg1.getDatosDelMensaje().getPeticion();
                }
                else {
                    if (this.datos.getIdentificador() == "NA") {
                        this.datos.setIdentificador(msg1.getDatosDelMensaje().getIdentificador());
                    }
                }
                break;
            case NotificacionUsurio:
                if (identificadorUsuario.equals("NA")) { //Si no tiene ID la guarda
                    MensajeNotificacion msg2 = (MensajeNotificacion)msg;
                    this.identificadorUsuario = msg2.getIDObjetivo();  
                }
                break;
            case Subscripciones: /*{NO RECIBE}*/break;
            case TodosLosDatos: /*{NO RECIBE}*/break;
        }
    }
    
    public void accionPeticion(PeticionSubasta peticion) {
        if (this.datos.getLimiteActual() < peticion.getBid()) {
            this.datos.setLimiteActual(peticion.getBid());
            this.datos.setPostor(peticion.getIdentidad());
            this.datos.setHistorial("Se acepto una Peticion por: $" + peticion.getBid() + '\n' + this.datos.getHistorial()); 
        }
        else {
            this.datos.setHistorial("Se rechazo una Peticion por: $" + peticion.getBid() + '\n' + this.datos.getHistorial());
        }
        mandarDatos();
    }
    public void limpiar() {
        this.identificadorUsuario = "NA";
        this.datos = null;
        this.servidor = null;
    }

    public InterfazSubastador getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(InterfazSubastador interfaz) {
        this.interfaz = interfaz;
    }

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public void setIdentificadorUsuario(String identificadorUsuario) {
        this.identificadorUsuario = identificadorUsuario;
    }

    public DatosSubasta getDatos() {
        return datos;
    }

    public void setDatos(DatosSubasta datos) {
        this.datos = datos;
    }

    public ThreadSubastador getServidor() {
        return servidor;
    }

    public void setServidor(ThreadSubastador servidor) {
        this.servidor = servidor;
    }
    
    
}
