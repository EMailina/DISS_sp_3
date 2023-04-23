package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import OSPStat.WStat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.ArrayList;
import objects.CustomerObject;

//meta! id="4"
public class AgentMechanics extends Agent {

    private int totalCountOfEmployees = 2;
    private int countOfWorking;
    private SimQueue<MessageForm> employee;

    private WStat freeEmployersStat;
    private ArrayList<CustomerObject> guiEmployers;

    public AgentMechanics(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        countOfWorking = 0;
        freeEmployersStat = new WStat(_mySim);
        employee = new SimQueue<>(new WStat(_mySim));
        employee.clear();
        for (int i = 0; i < totalCountOfEmployees; i++) {
            employee.add(null);
        }

        guiEmployers = new ArrayList<>(totalCountOfEmployees);
        for (int i = 0; i < totalCountOfEmployees; i++) {
            guiEmployers.add(null);
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerMechanics(Id.managerMechanics, mySim(), this);
        new ProcessInsepction(Id.processInsepction, mySim(), this);
        addOwnMessage(Mc.mechanicExecute);
        addOwnMessage(Mc.mechanicsAvailability);
        addOwnMessage(Mc.noticeEndInspection);
    }
    //meta! tag="end"

    public int getTotalCountOfEmployees() {
        return totalCountOfEmployees;
    }

    public int getCountOfWorking() {
        return countOfWorking;
    }

    public void addWorkingEmployee() {
        if (countOfWorking < totalCountOfEmployees) {
            freeEmployersStat.addSample(totalCountOfEmployees - countOfWorking);
            countOfWorking++;
            employee.remove(0);

        } else {
            System.out.println("Chyba------------------------------------------------------------------");
        }
    }

    public void removeWorkingEmployee() {
        if (countOfWorking > 0) {
            freeEmployersStat.addSample(totalCountOfEmployees - countOfWorking);
            countOfWorking--;
            employee.add(null);

        } else {
            System.out.println("Chyba-------------------------------------------------------------------");
        }
    }

    public long getCOuntOfVehicles() {
        return countOfWorking;
    }

    public WStat getFreeEmployersStat() {
        return freeEmployersStat;
    }

    public void setFreeEmployersStat(WStat freeEmployersStat) {
        this.freeEmployersStat = freeEmployersStat;
    }

    public SimQueue<MessageForm> getEmployee() {
        return employee;
    }

    public void setEmployee(SimQueue<MessageForm> employee) {
        this.employee = employee;
    }

    public ArrayList<CustomerObject> getGuiEmployers() {
        return guiEmployers;
    }

    public void setGuiEmployers(ArrayList<CustomerObject> guiEmployers) {
        this.guiEmployers = guiEmployers;
    }

}
