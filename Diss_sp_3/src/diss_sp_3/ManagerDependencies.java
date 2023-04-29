/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_sp_3;

import OSPABA.ISimDelegate;

import diss_sp_3.Diss_sp_3;
import diss_sp_3.RunType;
import simulation.MySimulation;

/**
 *
 * @author Erik
 */
public class ManagerDependencies {

    public boolean stop = false;

    public ManagerDependencies() {
        stop = false;
    }

    public void findDependencyOnEmp1(ISimDelegate refresher, int countOfEmp, int countOfReplication) {
        stop = false;
        for (int i = 0; i < 15; i++) {
            MySimulation simulation = new MySimulation();
         //   init(simulation, RunType.DEPENDENCY_1, i + 1, countOfEmp, refresher);

            simulation.simulate(countOfReplication, 480);
            refresher.refresh(simulation);
            if (stop) {
                System.out.println("vzpol som");
                break;
            }
        }
    }

    public void findDependencyOnEmp2(ISimDelegate refresher, int countOfEmp, int countOfReplication) {
        stop = false;
        for (int i = 10; i < 26; i++) {
            MySimulation simulation = new MySimulation();
          //  init(simulation, RunType.DEPENDENCY_2, countOfEmp, i + 1, refresher);

            simulation.simulate(countOfReplication, 480);
            refresher.refresh(simulation);

            if (stop) {
                System.out.println("vzpol som");
                break;
            }

        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    
//    private void init(MySimulation simulation, RunType type, int countOfEmp1, int countOfEmp2, ISimDelegate refresher) {
//        ((Diss_sp_3) refresher).setSimulation(simulation);
//        simulation.setType(type);
//        simulation.registerDelegate(refresher);
//        simulation.setCountOfEmployeeType1(countOfEmp1);
//        simulation.setCountOfEmployeeType2(countOfEmp2);
//    }

}
