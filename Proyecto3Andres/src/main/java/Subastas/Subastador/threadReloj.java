/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas.Subastador;

import java.time.Instant;

/**
 *
 * @author andre
 */
public class threadReloj extends Thread{
    private DatosSubastador datos;
    private boolean running = true;

    public threadReloj(DatosSubastador datos) {
        this.datos = datos;
    }
    
    @Override
    public void run() {
        if (running) {
            int res = Instant.now().compareTo(this.datos.getDatos().getFinalCronometro());
            if (res >= 0) { //Despues o en el mimsmo mometno que la fecha en 
                this.running = false;
                this.datos.terminarSubasta(); 
                return;
            }    
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                 
            }
        }
         
    }

}
