/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RecursosCompartidos;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author andre
 */
public class FiltroNumeros extends DocumentFilter {
    private final String regexVerificacion = "[\\d]*";
    //0, 1 o más digitos
    public FiltroNumeros() {
    }
    
    @Override
    public void insertString(DocumentFilter.FilterBypass fb,
                             int offset,
                             String string,
                             AttributeSet attr)
            throws BadLocationException {

        System.out.println("Texto: - " + string);
        
        if (string == null) {
            string = "";
        }

        String textoPrevio = fb.getDocument().getText(0, fb.getDocument().getLength());
        String definitivo = textoPrevio.substring(0, offset) + string + textoPrevio.substring(offset);
        
        System.out.println("Resulstado -" + definitivo);
        if (definitivo.matches(regexVerificacion)) {  
                super.insertString(fb, offset, string, attr);
        }

      
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb,
                         int offset,
                         int length,
                         String string,
                         AttributeSet attrs)
            throws BadLocationException {

        System.out.println("Texto: - " + string);
        
        if (string == null) {
            string = "";
        }
        
        String textoPrevio = fb.getDocument().getText(0, fb.getDocument().getLength());
        String definitivo = textoPrevio.substring(0, offset) + string + textoPrevio.substring(offset + length);
        
        System.out.println("Resulstado -" + definitivo);
        
        if (definitivo.matches(regexVerificacion)) {
            super.replace(fb, offset, length, string, attrs);
        }
    }
    
    public void remove(DocumentFilter.FilterBypass fb,
                             int offset,
                             int length)
            throws BadLocationException {
        
        
        
        String textoPrevio = fb.getDocument().getText(0, fb.getDocument().getLength());
        String definitivo = textoPrevio.substring(0, offset) + textoPrevio.substring(offset + length);
        if (definitivo.matches(regexVerificacion)) { //Por ejemplo si se borran el . al centro no sirve
            super.remove(fb, offset, length);
        }
    }
    
    
}
