package Subastas.Subastador;

import Comunicaciones.BaseLadoCliente;
import Mensajes.Mensaje;
import java.io.IOException;

/**
 *
 * @author andre
 */
public class ThreadSubastador extends BaseLadoCliente{
    DatosSubastador datos;
    public ThreadSubastador(DatosSubastador datos) throws IOException {
        super(BaseLadoCliente.TipoCliente.Admin);
        this.datos = datos;
    }

    @Override
    public void avisarError(Exception e) {
        PanelConexionSubastador panel = (PanelConexionSubastador) this.datos.getInterfaz().getPanelEspecifico(InterfazSubastador.NombrePanelesST.Conexion);
        panel.escribirMensaje("ERROR: " + e.getMessage());
        this.limpiar();
        datos.limpiar();
        datos.getInterfaz().mostrarPanel(InterfazSubastador.NombrePanelesST.Conexion);
    }

    @Override
    public void accionConMensaje(Mensaje msg) {
        this.datos.accionMensaje(msg);
    }
    
}
