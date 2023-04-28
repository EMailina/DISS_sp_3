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

    private int countOfPaused = 0;
    private boolean pauseTimeStarted = false;
    private double pauseTimeStartedTime = Double.MAX_VALUE;
    private int pauseCounter = 0;

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
        countOfPaused = 0;
        pauseTimeStarted = false;
        pauseTimeStartedTime = Double.MAX_VALUE;
        pauseCounter = 0;
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerMechanics(Id.managerMechanics, mySim(), this);
        new ProcessInsepction(Id.processInsepction, mySim(), this);
        new ProcessLunchPauseInspection(Id.processLunchPauseInspection, mySim(), this);

        addOwnMessage(Mc.mechanicExecute);
        addOwnMessage(Mc.mechanicsAvailability);
        addOwnMessage(Mc.noticeEndInspection);
        addOwnMessage(Mc.noticeLunchPause);
        addOwnMessage(Mc.noticeEndPause);
    }
    //meta! tag="end"

    public int getTotalCountOfEmployees() {
        return totalCountOfEmployees;
    }

    public int getCountOfWorking() {
        return countOfWorking + countOfPaused;
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

    public void setTotalCountOfEmployees(int totalCountOfEmployees) {
        this.totalCountOfEmployees = totalCountOfEmployees;
    }

    public int getCountOfPaused() {
        return countOfPaused;
    }

    public void setCountOfPaused(int countOfPaused) {
        this.countOfPaused = countOfPaused;
    }

    public boolean isPauseTimeStarted() {
        return pauseTimeStarted;
    }

    public void setPauseTimeStarted(boolean pauseTimeStarted) {
        this.pauseTimeStarted = pauseTimeStarted;
    }

    public double getPauseTimeStartedTime() {
        return pauseTimeStartedTime;
    }

    public void setPauseTimeStartedTime(double pauseTimeStartedTime) {
        this.pauseTimeStartedTime = pauseTimeStartedTime;
    }

    public int getPauseCounter() {
        return pauseCounter;
    }

    public void setPauseCounter(int pauseCounter) {
        this.pauseCounter = pauseCounter;
    }

    public void addPausedEmployees() {
        countOfPaused = totalCountOfEmployees - countOfWorking;
        for (int i = 0; i < countOfPaused; i++) {
            employee.remove(0);
        }
    }

    public void removePausedEmployees() {
        countOfPaused--;

        employee.add(null);

    }

    public void addPauseCounter() {
        this.pauseCounter++;
    }

    public void addEmployeeToPause() {
        countOfPaused++;
        employee.remove(0);
    }

}
