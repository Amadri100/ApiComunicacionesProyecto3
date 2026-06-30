package Subastas.UsuarioSubasta;

import Subastas.DatosSubasta;
import Subastas.Subastador.InterfazSubastador;
import java.awt.CardLayout;

import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;


public class usuarioSubastaPrincipal extends javax.swing.JFrame {
    public enum codigosDefinidos {
        CONECTAR ("Conectar"),
        SELECCIONAR("Seleccionar");
        
        private String nombre;

        private codigosDefinidos(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
    }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(usuarioSubastaPrincipal.class.getName());
    private PanelConectarUsuario panelConectar;
    private HashMap<String, JPanel> paneles; //subasta = nombre(codigo)
    private datosUsuarioSubasta datos = null;
    private boolean empezo = false; //false = no ha comenzado; true = ya comenzo
    /**
     * Creates new form usuarioSubastaPrincipal
     */
    public usuarioSubastaPrincipal() {
        initComponents();
        this.setResizable(false);
        this.Pantalla.setLayout(new CardLayout());
        this.panelConectar = new PanelConectarUsuario(this);
        this.paneles = new HashMap<>();
        abrirConectar();
    }
    
    
    public void eliminar(String identidad) {
        DatosSubasta datos = this.datos.getDatos().get(identidad);
        this.datos.getDatos().remove(identidad);
        String identificacionPanel = stringPaneles(datos);
        this.paneles.remove(identificacionPanel);
    }
    
    public boolean textoEsSeleccionar(String str)
    {
        return str.equals(codigosDefinidos.SELECCIONAR.getNombre());
    }    
    public void cerrarTodo() {
        if (this.paneles == null || this.paneles.isEmpty()){
            return;
        }
        for (String str : this.paneles.keySet()) {
             if (!textoEsSeleccionar(str)) {
                 panelClienteSubasta panel = (panelClienteSubasta)this.paneles.get(str);
                 panel.cerrar();
             }
        }
    }
        
    
    public void actualizarSelector() {
        this.SelectorDePantallas.setModel(new DefaultComboBoxModel<>(this.paneles.keySet().toArray(new String[0])));
    }
    
    public void actualizarPanelesSeleccion(HashMap<String, DatosSubasta> mapa) {
        ((SelectorSubastas)paneles.get(codigosDefinidos.SELECCIONAR.getNombre())).actualizarPanelesSeleccion(mapa);
    }   
    
    public String stringPaneles(DatosSubasta datos) {
        return datos.getNombre()+"(" + datos.getIdentificador() + ")";
    }
    
    public void agregarDatosPanel(DatosSubasta datos) {
        System.out.println("Se recibieron datos");
        String identificador = stringPaneles(datos);
           
        if(paneles.get(identificador) != null) {     
            System.out.println("Existe");
            ((panelClienteSubasta)paneles.get(identificador)).actualizarDatos(datos);
        }
        else {    
            System.out.println("Se crea uno nuevo");
            panelClienteSubasta panel = new panelClienteSubasta(this, datos);
            paneles.put(identificador, panel);
            Pantalla.add(panel, identificador);
            Pantalla.revalidate();
            Pantalla.repaint();         
            
        }
        actualizarSelector();
    }

    public void mostrarPanel(String texto) {
        CardLayout layout = (CardLayout) this.Pantalla.getLayout();
        layout.show(this.Pantalla, texto);

    }
    
    public void abrirPanel(String texto) {
        if (!textoEsSeleccionar(texto)) {
            panelClienteSubasta panel = (panelClienteSubasta)this.paneles.get(texto);
            panel.abrir();
        }
    }
    
    public void abrirConectar() {
        this.Pantalla.add(panelConectar, codigosDefinidos.CONECTAR.getNombre());
        Pantalla.revalidate();
        Pantalla.repaint();
 
        this.mostrarPanel(codigosDefinidos.CONECTAR.getNombre());
    }
    
    public void resetAConectar() {
        limpiar();
        abrirConectar();
    }
    
    public void comenzarSeleccion() {
        limpiar();
        SelectorSubastas selector = new SelectorSubastas(this);
        this.paneles.put(codigosDefinidos.SELECCIONAR.getNombre(), selector);
        this.Pantalla.add(selector, codigosDefinidos.SELECCIONAR.getNombre());
        mostrarPanel(codigosDefinidos.SELECCIONAR.getNombre());
        actualizarSelector();
    }
    
    public void limpiar() {
        this.Pantalla.removeAll();
        this.paneles.clear();
        actualizarSelector();
        Pantalla.revalidate();
        Pantalla.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Pantalla = new javax.swing.JPanel();
        SelectorDePantallas = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Pantalla.setPreferredSize(new java.awt.Dimension(1000, 600));

        javax.swing.GroupLayout PantallaLayout = new javax.swing.GroupLayout(Pantalla);
        Pantalla.setLayout(PantallaLayout);
        PantallaLayout.setHorizontalGroup(
            PantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        PantallaLayout.setVerticalGroup(
            PantallaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        SelectorDePantallas.addActionListener(this::SelectorDePantallasActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(SelectorDePantallas, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Pantalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SelectorDePantallas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Pantalla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SelectorDePantallasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectorDePantallasActionPerformed
        cerrarTodo();
        String llave = (String) this.SelectorDePantallas.getSelectedItem();
        mostrarPanel(llave);
        abrirPanel(llave);
    }//GEN-LAST:event_SelectorDePantallasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new usuarioSubastaPrincipal().setVisible(true));
    }

    public PanelConectarUsuario getPanelConectar() {
        return panelConectar;
    }

    public void setPanelConectar(PanelConectarUsuario panelConectar) {
        this.panelConectar = panelConectar;
    }

    public HashMap<String, JPanel> getPaneles() {
        return paneles;
    }

    public void setPaneles(HashMap<String, JPanel> paneles) {
        this.paneles = paneles;
    }

    public boolean isEmpezo() {
        return empezo;
    }

    public void setEmpezo(boolean empezo) {
        this.empezo = empezo;
    }

    public JPanel getPantalla() {
        return Pantalla;
    }

    public void setPantalla(JPanel Pantalla) {
        this.Pantalla = Pantalla;
    }

    public JComboBox<String> getSelectorDePantallas() {
        return SelectorDePantallas;
    }

    public void setSelectorDePantallas(JComboBox<String> SelectorDePantallas) {
        this.SelectorDePantallas = SelectorDePantallas;
    }

    public datosUsuarioSubasta getDatos() {
        return datos;
    }

    public void setDatos(datosUsuarioSubasta datos) {
        this.datos = datos;
    }

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Pantalla;
    private javax.swing.JComboBox<String> SelectorDePantallas;
    // End of variables declaration//GEN-END:variables
}

