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
        while (running) {
            if (this.datos == null){
                return;
            }
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

    public DatosSubastador getDatos() {
        return datos;
    }

    public void setDatos(DatosSubastador datos) {
        this.datos = datos;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    
    

}
