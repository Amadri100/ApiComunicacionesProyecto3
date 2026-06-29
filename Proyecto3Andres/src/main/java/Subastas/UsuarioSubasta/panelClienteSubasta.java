/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Subastas.UsuarioSubasta;

import ImagenSerializable.ImagenSerializable;
import RecursosCompartidos.FiltroNumeros;
import RecursosCompartidos.PanelImagen;
import Subastas.DatosSubasta;
import static Subastas.DatosSubasta.estadoSubasta.CANCELADA;
import static Subastas.DatosSubasta.estadoSubasta.CERRADA;
import static Subastas.DatosSubasta.estadoSubasta.VENDIDA;
import Subastas.Subastador.*;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author andre
 */
public class panelClienteSubasta extends javax.swing.JPanel {
    private final int ancho = 780;
    private final int alto = 380;
    private usuarioSubastaPrincipal interfaz;
    private String identificacion;
    private DatosSubasta.estadoSubasta estado = DatosSubasta.estadoSubasta.CERRADA;
    private boolean abierto = false;
    /**
     * Creates new form PanelSubasta
     */
    public panelClienteSubasta(usuarioSubastaPrincipal interfaz, DatosSubasta datos) {
        initComponents();
        ((AbstractDocument)this.input.getDocument()).setDocumentFilter(new FiltroNumeros());
        this.interfaz = interfaz;
        obtenerDatos(datos);
    }
    
     public void cerrar() {
        this.abierto = false;
    }
     
    public void abrir() {
        this.abierto = true;
        ejecutarAccion();
    }
    
    public void ejecutarAccion() {
        if (abierto) {
            String mensaje = this.interfaz.getDatos().mensaje(this.identificacion);
            if (!mensaje.equals("")) {
                JOptionPane.showMessageDialog(this, mensaje);
                this.interfaz.eliminar(this.identificacion);
            }
        }

    }
 

    public void actualizarDatos(DatosSubasta datos) {
        String estado = "";
        switch (datos.getEstatus()) {
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
        this.lblEstatus.setText(estado);
        this.estado = datos.getEstatus();
        this.lblPrecioActual.setText("%" + datos.getLimiteActual());
        this.Historial.setText(datos.getHistorial());
        
    };
    
    public final void obtenerDatos(DatosSubasta datos) {
        this.identificacion = datos.getIdentificador();  
        this.lblNombre.setText(datos.getNombre());
        this.lblDescripcion.setText(datos.getProducto().getDescripcion());
        String fecha = datos.getFinalCronometro().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.lblFecha.setText(fecha);
        String estado = "";
        switch (datos.getEstatus()) {
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
        this.lblEstatus.setText(estado);
        this.estado = datos.getEstatus();
        this.lblPrecioActual.setText("%" + datos.getLimiteActual());
        this.Historial.setText(datos.getHistorial());
        mostrarImagen(datos.getProducto().getImagen());
        
    }
    public final void mostrarImagen(ImagenSerializable imagen) {
        ImagenSerializable img = imagen;
        if (img == null) {
            try {
                URL url = getClass().getResource("/Imagen_no_disponible.png");
                Path ruta = Paths.get(url.toURI());
                try {
                    img = new ImagenSerializable(ruta);
                } catch (IOException ex) {
                    System.out.println("MI: " + ex.getMessage());
                    img = null;
                }
            } catch (URISyntaxException ex) {
                 
            }
        }
        BufferedImage imagenTemp = null;
        if (img == null) {}
        else{
            try {
                imagenTemp = img.conseguirImagen();
            } catch (IOException ex) {
                imagenTemp = null;
            }
        }
        this.panelImagen.removeAll();
        PanelImagen panel = new PanelImagen(imagenTemp);
        panel.setwithei(this.ancho, this.alto);
        this.panelImagen.add(panel, BorderLayout.CENTER);
    
    }
    

    public void agregarImagen(ImagenSerializable imagen) {
        this.panelImagen.removeAll();   
        try {
            PanelImagen panel = new PanelImagen(imagen.conseguirImagen());
            panel.setwithei(this.ancho, this.alto);
            this.panelImagen.add(panel);
        } catch (IOException ex) {
            System.getLogger(panelClienteSubasta.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }

    public usuarioSubastaPrincipal getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(usuarioSubastaPrincipal interfaz) {
        this.interfaz = interfaz;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public DatosSubasta.estadoSubasta getEstado() {
        return estado;
    }

    public void setEstado(DatosSubasta.estadoSubasta estado) {
        this.estado = estado;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public JTextArea getHistorial() {
        return Historial;
    }

    public void setHistorial(JTextArea Historial) {
        this.Historial = Historial;
    }

    public JTextField getInput() {
        return input;
    }

    public void setInput(JTextField input) {
        this.input = input;
    }

 

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JTextArea getjTextArea1() {
        return Historial;
    }

    public void setjTextArea1(JTextArea jTextArea1) {
        this.Historial = jTextArea1;
    }

    public JLabel getLblDescripcion() {
        return lblDescripcion;
    }

    public void setLblDescripcion(JLabel lblDescripcion) {
        this.lblDescripcion = lblDescripcion;
    }

    public JLabel getLblEstatus() {
        return lblEstatus;
    }

    public void setLblEstatus(JLabel lblEstatus) {
        this.lblEstatus = lblEstatus;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public void setLblFecha(JLabel lblFecha) {
        this.lblFecha = lblFecha;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JLabel getLblPrecioActual() {
        return lblPrecioActual;
    }

    public void setLblPrecioActual(JLabel lblPrecioActual) {
        this.lblPrecioActual = lblPrecioActual;
    }

    public JPanel getPanelImagen() {
        return panelImagen;
    }

    public void setPanelImagen(JPanel panelImagen) {
        this.panelImagen = panelImagen;
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblEstatus = new javax.swing.JLabel();
        panelImagen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Historial = new javax.swing.JTextArea();
        lblDescripcion = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        lblPrecioActual = new javax.swing.JLabel();
        input = new javax.swing.JTextField();

        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombre.setText("Nombre");
        lblNombre.setToolTipText("");
        lblNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblNombre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFecha.setText("Fecha");
        lblFecha.setToolTipText("");
        lblFecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblFecha.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblEstatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstatus.setText("Estatus");
        lblEstatus.setToolTipText("");
        lblEstatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblEstatus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        panelImagen.setBackground(new java.awt.Color(51, 102, 255));
        panelImagen.setMinimumSize(new java.awt.Dimension(780, 360));

        javax.swing.GroupLayout panelImagenLayout = new javax.swing.GroupLayout(panelImagen);
        panelImagen.setLayout(panelImagenLayout);
        panelImagenLayout.setHorizontalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        panelImagenLayout.setVerticalGroup(
            panelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );

        Historial.setColumns(20);
        Historial.setRows(5);
        jScrollPane1.setViewportView(Historial);

        lblDescripcion.setText("Descripcion");
        lblDescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnCancelar.setBackground(new java.awt.Color(255, 0, 0));
        btnCancelar.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelar.setText("Salir Subasta");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        lblPrecioActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrecioActual.setText("PrecioActual");
        lblPrecioActual.setToolTipText("");
        lblPrecioActual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblPrecioActual.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        input.addActionListener(this::inputActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(input)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPrecioActual, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 543, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addComponent(lblEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrecioActual, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(lblDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Historial;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JTextField input;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblEstatus;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecioActual;
    private javax.swing.JPanel panelImagen;
    // End of variables declaration//GEN-END:variables
}
