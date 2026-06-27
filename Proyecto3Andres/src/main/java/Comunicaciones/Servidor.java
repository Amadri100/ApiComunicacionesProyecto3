package Comunicaciones;


import Observers.Observable;
import Mensajes.*;
import static Mensajes.TiposMensaje.NotificacionUsurio;
import Observers.Datos;
import Observers.ObserverAdmin;
import Observers.ObserverUsuario;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

//===================================================================
//
//Nombre observable = AD
//Nombre observador = US
//Nombre enEspera = ES
//
//
//
//
//===================================================================
public class Servidor {
    private final int port = 33332;
    
    private Interfaz interfaz;
    private ServerSocket socketServidor;
    
    private HashMap<String, Integer> numeroSegunBase; //Segun la base de la identidad
    /*
    Subasta = SU
    SU0 - SU1 - SU2
    Luego se tiene además Red social = RS
    RS0 - RS1 -RS2; cadavez que aparezca uno nuevo tiene su propio contador
    */
    private HashMap<String, String> esDueñoDe; 
    //Llava: Identificacion de una conexion Observable AD Valor: Identificacion de su Observable correspondiente
    
    private HashMap<String, ThreadConexion> conexionesObservables;
    private HashMap<String, ThreadConexion> conexionesObservadores;
    private ArrayList<ThreadConexion> conexionesEnEspera; 
    
    private HashMap<String, Observable> listaObservables; //Lista con todos los objetos observables
    
    private ThreadConexiones buscarConexiones;
    private ThreadMensajeAMandar threadMandadoMensajes;
    
    //Contadores
    private int contadorObservables = 0;
    private int contadorUsuarios = 0;
    //private int contadorConexiones = 0;
    
    //INCIALIZAR SERVIDOR
    
    public Servidor(Interfaz interfaz) {
        this.interfaz = interfaz;
        connect();
        this.conexionesEnEspera = new ArrayList<>();
        this.conexionesObservables = new HashMap<>();
        this.conexionesObservables = new HashMap<>();
        this.numeroSegunBase = new HashMap<>();
        this.listaObservables = new HashMap<>();
        this.buscarConexiones = new ThreadConexiones(this);
        this.threadMandadoMensajes = new ThreadMensajeAMandar(this);
        this.threadMandadoMensajes.start();
    }

    private void connect() {
        try {
            this.socketServidor = new ServerSocket(port);
            interfaz.escribirTexto("Servidor iniciado en el puerto: " + String.valueOf(port));
        } catch (IOException ex) {
            interfaz.escribirTexto("ERROR: " + ex.getMessage());
        }
    }
    
    public void eliminarConexion(ThreadConexion conexion) {
        String id = conexion.getIdentificador();
        if (id.matches("ES\\d+")) {
            for (int i = 0; i < this.conexionesEnEspera.size(); i++ ) {
                if (this.conexionesEnEspera.get(i).getIdentificador().equals(id)){
                    this.conexionesEnEspera.remove(i);
                    break;
                }
            }
            //No se saca de ningun observable
        }
        else if (id.matches("AD\\d+")) {
           this.conexionesObservables.remove(id);
           quitarObservable(id);
        }
        else if (id.matches("US\\d+")) {
            this.conexionesObservadores.remove(id);
            desuscribirseTodos(id);
        }
    }
    
    public void quitarObservable(String idConexion) {
        Observable observableEnCuestion = this.listaObservables.get(this.esDueñoDe.get(idConexion));
        this.listaObservables.remove(observableEnCuestion.getIdEvento());
        observableEnCuestion.terminar();
    }
    
    public void desuscribirseTodos(String identificacion) {
        for (Observable observable : this.listaObservables.values()) {
            observable.eliminarObserver(identificacion);
        }
    }
    
    public int indiceDeConexionSegunId(String id) {
        for (int i = 0; i < conexionesEnEspera.size(); i++) {
            if (conexionesEnEspera.get(i).getIdentificador().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    
    public void mandarDatos() {
        ArrayList<Datos> todosLosDatos = new ArrayList<>();
        for (Observable obs : this.listaObservables.values()){
            todosLosDatos.add(obs.getDatos());
        }
        MensajeTodosLosDatos msg = new MensajeTodosLosDatos(todosLosDatos);
        for (ThreadConexion conexion : this.conexionesObservadores.values()) {
            conexion.mandarMensaje(msg);
        }
    }
    
    //MANEJO DE SOCKETS
    
    public void mandarMensaje(String identificacion, Mensaje msg) {
        if (identificacion.matches("US\\d+")) {
            ThreadConexion conexion = this.conexionesObservadores.get(identificacion);
            if (conexion != null) {
                conexion.mandarMensaje(msg);
            }
        }
        else if (identificacion.matches("AD\\d+")){
            ThreadConexion conexion = this.conexionesObservables.get(identificacion);
            if (conexion != null) {
                conexion.mandarMensaje(msg);
            }
        }
    }
    
    
    public void recibirMensaje(Mensaje msg, ThreadConexion conexion) {
        boolean encontrado = false;
        int indice = -1;
        String identidad = "";
        switch (msg.getTipo()) {
            case ConectarseServidor:
                MensajeConexion msg0 = (MensajeConexion)msg;
                if (msg0.isConexion()) {
                    indice = indiceDeConexionSegunId(conexion.getIdentificador());
                    if (indice != -1) {
                        encontrado = true;
                        ThreadConexion conexionGuardada = this.conexionesEnEspera.get(indice);
                        this.conexionesEnEspera.remove(indice);
                        identidad += "AD" + contadorObservables++;
                        conexionGuardada.cambiarID(identidad);
                        this.conexionesObservables.put(conexion.getIdentificador(), conexion);
                    }
                }
                else {
                    indice = indiceDeConexionSegunId(conexion.getIdentificador());
                    if (indice != -1) {
                        encontrado = true;
                        ThreadConexion conexionGuardada = this.conexionesEnEspera.get(indice);
                        this.conexionesEnEspera.remove(indice);
                        identidad += "US" + contadorObservables++;
                        conexionGuardada.cambiarID(identidad);
                        this.conexionesObservables.put(conexion.getIdentificador(), conexion);
                    }
                }
                if (encontrado) {
                    MensajeNotificacion msgNoti = new MensajeNotificacion(identidad);
                    conexion.mandarMensaje(msgNoti);
                }
                break;
            case CrearObservable: 
                if (conexion.getIdentificador().matches("AD\\d+")) {
                    boolean esDueñoDeAlgo = this.esDueñoDe.get(conexion.getIdentificador()) == null;
                    MensajeObservables msg1 = (MensajeObservables)msg;
                    String codigo = "";
                    indice = 0;
                    if (msg1.isCaso() && !esDueñoDeAlgo) { //Crear
                        identidad = msg1.getIDObjetivo();
                        if (this.numeroSegunBase.get(msg1.getIDObjetivo()) != null) {
                            indice = this.numeroSegunBase.get(msg1.getIDObjetivo());
                            this.numeroSegunBase.put(codigo, indice+1);
                        }
                        else {
                            this.numeroSegunBase.put(codigo, indice+1);
                        }
                        identidad += String.valueOf(indice);
                        Observable observable = new Observable(this, msg1.getDatos(), identidad);
                        ObserverAdmin observer1 = new ObserverAdmin(conexion.getIdentificador(), observable);
                        this.esDueñoDe.put(conexion.getIdentificador(), identidad);
                        observable.agregarObservable(observer1);
                        observable.notificarPrioridad(); 
                    }
                    else if (esDueñoDeAlgo){ //destruir
                        codigo = msg1.getIDObjetivo(); //Esta vez es el codigo completo
                        Observable obeservable0 = this.listaObservables.get(this.esDueñoDe.get(conexion.getIdentificador()));
                        if (obeservable0 != null) {
                            obeservable0.terminar();
                            this.listaObservables.remove(codigo);
                        }
                        this.esDueñoDe.remove(conexion.getIdentificador());
                    }
                } 
                break;
            case MandarDatos:
                MensajeDatos msg2 = (MensajeDatos)msg;
                String codigo1 = msg2.getIDObjetivo();
                boolean esUsuario = conexion.getIdentificador().matches("US\\d+");
                if (msg2.permitidoPorUsuario() && esUsuario) {
                    this.listaObservables.get(codigo1).getDatos().setPeticion(msg2.getDatosDelMensaje().getPeticion());
                }
                else if (!esUsuario){
                    this.listaObservables.get(codigo1).setDatos(msg2.getDatosDelMensaje());
                }
                break;
            case Subscripciones: 
                if (conexion.getIdentificador().matches("US\\d+")){
                    MensajeSuscripcion msg3 = (MensajeSuscripcion)msg;
                    Observable observable1 = this.listaObservables.get(msg3.getIDObjetivo());
                    if (observable1 != null) {
                        if (observable1 != null) {
                            ObserverUsuario observer2 = new ObserverUsuario(conexion.getIdentificador(), observable1);
                            observable1.agregarObservable(observer2);
                        }
                        if (observable1 != null) {
                            observable1.eliminarObserver(conexion.getIdentificador());
                        }
                    }
                }
                break;
            case NotificacionUsurio: /*El servidor no recibe esta clase de mensajes*/ break;
            case TodosLosDatos: /*El servidor no recibe esta clase de mensajes*/ break;

        }
        
 
     
    }

    public void agregarEspera(Socket socket, int numConexion) {
        ThreadConexion conexion = new ThreadConexion(socket, this);
        conexion.cambiarID("ES" + numConexion);
        this.conexionesEnEspera.add(conexion);
    }
    
    //GETTER SETTERS
    
    public Interfaz getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

    public ServerSocket getSocketServidor() {
        return socketServidor;
    }

    public void setSocketServidor(ServerSocket socketServidor) {
        this.socketServidor = socketServidor;
    }

    public HashMap<String, Integer> getNumeroSegunBase() {
        return numeroSegunBase;
    }

    public void setNumeroSegunBase(HashMap<String, Integer> numeroSegunBase) {
        this.numeroSegunBase = numeroSegunBase;
    }

    public HashMap<String, ThreadConexion> getConexionesObservables() {
        return conexionesObservables;
    }

    public void setConexionesObservables(HashMap<String, ThreadConexion> conexionesObservables) {
        this.conexionesObservables = conexionesObservables;
    }

    public HashMap<String, ThreadConexion> getConexionesObservadores() {
        return conexionesObservadores;
    }

    public void setConexionesObservadores(HashMap<String, ThreadConexion> conexionesObservadores) {
        this.conexionesObservadores = conexionesObservadores;
    }

    public ArrayList<ThreadConexion> getConexionesEnEspera() {
        return conexionesEnEspera;
    }

    public void setConexionesEnEspera(ArrayList<ThreadConexion> conexionesEnEspera) {
        this.conexionesEnEspera = conexionesEnEspera;
    }

    public HashMap<String, Observable> getListaObservables() {
        return listaObservables;
    }

    public void setListaObservables(HashMap<String, Observable> listaObservables) {
        this.listaObservables = listaObservables;
    }

    public HashMap<String, String> getEsDueñoDe() {
        return esDueñoDe;
    }

    public void setEsDueñoDe(HashMap<String, String> esDueñoDe) {
        this.esDueñoDe = esDueñoDe;
    }

    public ThreadConexiones getBuscarConexiones() {
        return buscarConexiones;
    }

    public void setBuscarConexiones(ThreadConexiones buscarConexiones) {
        this.buscarConexiones = buscarConexiones;
    }

    public ThreadMensajeAMandar getThreadMandadoMensajes() {
        return threadMandadoMensajes;
    }

    public void setThreadMandadoMensajes(ThreadMensajeAMandar threadMandadoMensajes) {
        this.threadMandadoMensajes = threadMandadoMensajes;
    }

    public int getContadorObservables() {
        return contadorObservables;
    }

    public void setContadorObservables(int contadorObservables) {
        this.contadorObservables = contadorObservables;
    }

    public int getContadorUsuarios() {
        return contadorUsuarios;
    }

    public void setContadorUsuarios(int contadorUsuarios) {
        this.contadorUsuarios = contadorUsuarios;
    }
    
}
