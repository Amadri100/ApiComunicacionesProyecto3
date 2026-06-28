package RecursosCompartidos;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelImagen extends JPanel {

    private Image imagen;

    public PanelImagen(BufferedImage imagen) {
       this.imagen = imagen;
    }

    public void setwithei(int w, int h) {
        setPreferredSize(new Dimension(w, h));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}