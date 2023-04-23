/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;


import diss_sp_3.Diss_sp_3;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erik
 */
public class ExtraThread extends Thread{
    
    Diss_sp_3 frame;

    public ExtraThread(Diss_sp_3 frame) {
        this.frame = frame;
    }

    @Override
    public void run() {
        try {
            frame.startSimulation();
        } catch (IOException ex) {
            Logger.getLogger(ExtraThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExtraThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}