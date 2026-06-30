package Subastas.Subastador;


import Mensajes.*;
import Observers.Peticion;
import Subastas.DatosSubasta;
import Subastas.PeticionSubasta;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
    private threadReloj reloj = null;
    
    
    public DatosSubastador(InterfazSubastador interfaz) {
        this.interfaz = interfaz;
    }
    
    public boolean conectar() {
        if (servidor == null){
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
    
    public String textoFinal() {
        if (!this.datos.getPostor().equals("NA")) {
            return "El ganador es el usuario: " + this.datos.getPostor() +" con: $" + this.datos.getLimiteActual();
        }
        return "No hay ganador";
    }
    
    public void cargarDatos() {
        PanelSubasta panelSub =  (PanelSubasta) this.interfaz.getPanelEspecifico(InterfazSubastador.NombrePanelesST.Subasta);
        panelSub.getLblNombre().setText(this.datos.getNombre());
        panelSub.getLblDescripcion().setText(this.datos.getProducto().getDescripcion());
        String estado = "";
        switch (this.datos.getEstatus()) {
            case CANCELADA:
                estado += "Cancelado";
                break;
            case CERRADA:
                estado += "Cerrado";
                break;
            case VENDIDA:
                estado += "Vendido";
                break;
        }
        panelSub.getLblEstatus().setText(estado);
        String fecha = this.datos.getFinalCronometro().
                atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        panelSub.getLblFecha().setText(fecha);
        panelSub.getTaBitacora().setText(this.datos.getHistorial());
        panelSub.getLblPrecioActual().setText("$"+this.datos.getLimiteActual());
        panelSub.mostrarImagen(this.getDatos().getProducto().getImagen());
    }
    
    public void actualizarDatos() {
        PanelSubasta panelSub =  (PanelSubasta) this.interfaz.getPanelEspecifico(InterfazSubastador.NombrePanelesST.Subasta);
        String estado = "";
        switch (this.datos.getEstatus()) {
            case CANCELADA:
                estado += "Cancelado";
                break;
            case CERRADA:
                estado += "Cerrado";
                break;
            case VENDIDA:
                estado += "Vendido";
                break;
        }
        panelSub.getLblEstatus().setText(estado);
        panelSub.getTaBitacora().setText(this.datos.getHistorial());
        panelSub.getLblPrecioActual().setText("$"+this.datos.getLimiteActual());
    }
    
    public void iniciarSubasta(DatosSubasta datos) {
        this.datos = datos;
        
        MensajeObservables msg = new MensajeObservables(this.identificadorUsuario, this.baseCodigo, this.datos.getNombre(), true, this.datos);

        this.servidor.mandarMensaje(msg);        

        this.reloj = new threadReloj(this);
        this.reloj.start();

        cargarDatos();
    }
    
    public void terminarSubasta() {
        this.datos.setEstatus(DatosSubasta.estadoSubasta.VENDIDA);
        MensajeObservables msg = new MensajeObservables(this.identificadorUsuario, this.datos.getIdentificador(), this.datos.getNombre(), false);
        this.servidor.mandarMensaje(msg);
        ((PanelSubasta)this.interfaz.getPanelEspecifico(InterfazSubastador.NombrePanelesST.Subasta)).darGanador();
    }
    
    public void mandarDatos() {
        this.datos.setPeticion(null);
        MensajeDatos datos  = new MensajeDatos(this.identificadorUsuario, this.datos.getIdentificador(), this.datos);
        servidor.mandarMensaje(datos);
        actualizarDatos();
    }
    
    public void accionMensaje(Mensaje msg) {
        switch(msg.getTipo()) {
            case ConectarseServidor: /*{NO RECIBE}*/ break;
            case CrearObservable: /*{NO RECIBE}*/break;
            case MandarDatos: 
                System.out.println("Recibe mensaje datos");
                MensajeDatos msg1 = (MensajeDatos)msg;
                System.out.println(msg1.getIDMandado() + " " + ((PeticionSubasta)msg1.getDatosDelMensaje().getPeticion())); 
                if(msg1.permitidoPorUsuario()) {
                     System.out.println("Detecta peticion");
                     PeticionSubasta peticion = (PeticionSubasta)msg1.getDatosDelMensaje().getPeticion();
                     accionPeticion(peticion);
                }
                else {
                    if (this.datos.getIdentificador().equals("NA") ) {
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
    
    public void cancelar() {
        this.datos.setEstatus(DatosSubasta.estadoSubasta.CANCELADA);
        MensajeDatos msg = new MensajeDatos(this.identificadorUsuario, this.datos.getIdentificador(), this.datos);
        this.servidor.mandarMensaje(msg);
        MensajeObservables msg1 =
                new MensajeObservables(this.identificadorUsuario, this.datos.getIdentificador(), this.datos.getNombre(), false);
        this.servidor.mandarMensaje(msg1);
        this.interfaz.limpiar();   
        this.limpiar(); 
    }
    
    public void limpiar() {
        this.identificadorUsuario = "NA";
        if (reloj != null) {
            this.reloj.setRunning(false);
        }
        this.reloj = null;
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
