/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas.UsuarioSubasta;

import Comunicaciones.BaseLadoCliente;
import Mensajes.Mensaje;
import Subastas.Subastador.DatosSubastador;
import Subastas.Subastador.InterfazSubastador;
import Subastas.Subastador.PanelConexionSubastador;
import java.io.IOException;

/**
 *
 * @author andre
 */
public class ThreadUsuarioSubasta extends BaseLadoCliente{
    datosUsuarioSubasta datos;
    public ThreadUsuarioSubasta(datosUsuarioSubasta datos) throws IOException {
        super(BaseLadoCliente.TipoCliente.Usuario);
        this.datos = datos;
    }

    @Override
    public void avisarError(Exception e) {
        PanelConectarUsuario panel = (PanelConectarUsuario) this.datos.getInterfaz().getPanelConectar();
        panel.mostrarTexto("ERROR: " + e.getMessage());
        this.limpiar();
        datos.limpiar();
    }

    @Override
    public void accionConMensaje(Mensaje msg) {
        this.datos.accionMensaje(msg);
    }
    
}