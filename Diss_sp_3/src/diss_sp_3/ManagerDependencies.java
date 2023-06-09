/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diss_sp_3;

import OSPABA.ISimDelegate;

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

    public void findDependencyOnEmp1(ISimDelegate refresher, int countOfEmp, int countOfEmpC2, boolean validation, int countOfReplication) {
        stop = false;
        MySimulation simulation = new MySimulation();
        for (int i = 0; i < 15; i++) {

            init(simulation, RunType.DEPENDENCY_1, i + 1, countOfEmp, countOfEmpC2, validation, refresher);

            simulation.simulate(countOfReplication, 480);
            refresher.refresh(simulation);
            if (stop) {
                System.out.println("vzpol som");
                break;
            }
        }
    }

    public void findDependencyOnEmp2(ISimDelegate refresher, int countOfEmp, int countOfReplication, boolean validation) {
        stop = false;
        MySimulation simulation = new MySimulation();
        for (int i = 10; i < 26; i++) {

            init(simulation, RunType.DEPENDENCY_2, countOfEmp, 0, i + 1, validation, refresher);
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

    private void init(MySimulation simulation, RunType type, int countOfEmp1, int countOfEmp2, int countOfEmpC2, boolean validation, ISimDelegate refresher) {
        ((Diss_sp_3) refresher).setSimulation(simulation);
        simulation.setType(type);
        simulation.registerDelegate(refresher);
        simulation.setCountOfEmployeeType1(countOfEmp1);
        simulation.setValidationRun(validation);
        simulation.setCountOfEmployeeType2WithCertificate1(countOfEmp2);
        simulation.setCountOfEmployeeType2WithCertificate2(countOfEmpC2);
    }

}
