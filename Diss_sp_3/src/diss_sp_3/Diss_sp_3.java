/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_sp_3;

import simulation.MySimulation;

/**
 *
 * @author Erik
 */
public class Diss_sp_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MySimulation sim = new MySimulation();
        sim.onSimulationWillStart(s -> {
            System.out.println("simm...");
        });
        sim.simulate(1, 10000000);
        
    }

}
