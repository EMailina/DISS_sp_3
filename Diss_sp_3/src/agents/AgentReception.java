package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import OSPStat.WStat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import objects.CustomerObject;

//meta! id="3"
public class AgentReception extends Agent {

    private int totalCountOfEmployees = 2;
    private int totalCountOfParkingPlaces = 5;
    private SimQueue<MessageForm> queueTakeOver;
    private ConcurrentLinkedQueue<MessageForm> queueTakeOverGui;
    private ConcurrentLinkedQueue<MessageForm> queuePayingGui;
    private SimQueue<MessageForm> queuePaying;
    private Stat waitingTimeStat;
    private WStat freeEmployersStat;
    private int countOfWorking = 0;
    private int countOfPaused = 0;

    private int countOfReservedParkingPlaces = 0;
    private SimQueue<MessageForm> employee;
    private ArrayList<CustomerObject> guiEmployers;
    private boolean pauseTimeStarted = false;
    private double pauseTimeStartedTime = Double.MAX_VALUE;
    private int pauseCounter = 0;

    public AgentReception(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();

        queueTakeOver = new SimQueue<>(new WStat(_mySim));
        queueTakeOverGui = new ConcurrentLinkedQueue<>();
        queuePayingGui = new ConcurrentLinkedQueue<>();
        queuePaying = new SimQueue<>(new WStat(_mySim));
        waitingTimeStat = new Stat();
        freeEmployersStat = new WStat(_mySim);
        countOfWorking = 0;
        countOfPaused = 0;
        countOfReservedParkingPlaces = 0;
        employee = new SimQueue<>(new WStat(_mySim));
        employee.clear();
        for (int i = 0; i < totalCountOfEmployees; i++) {
            employee.add(null);
        }
        pauseTimeStartedTime = Double.MAX_VALUE;

        guiEmployers = new ArrayList<>(totalCountOfEmployees);
        for (int i = 0; i < totalCountOfEmployees; i++) {
            guiEmployers.add(null);
        }
        pauseTimeStarted = false;
        pauseCounter = 0;
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerReception(Id.managerReception, mySim(), this);
        new ProcessTakeOverVehicle(Id.processTakeOverVehicle, mySim(), this);
        new ProcessPaying(Id.processPaying, mySim(), this);
        new ProcessLunchPauseReception(Id.processLunchPauseReception, mySim(), this);

        addOwnMessage(Mc.checkParkingPlace);
        addOwnMessage(Mc.paymentExecute);
        addOwnMessage(Mc.receptionExecute);
        addOwnMessage(Mc.noticeEndTakeOver);
        addOwnMessage(Mc.noticeEndPaying);
        addOwnMessage(Mc.noticeFreeParking);
        addOwnMessage(Mc.noticeLunchPause);
        addOwnMessage(Mc.noticeEndPause);
    }
    //meta! tag="end"

    public Stat getStatWaitingTime() {
        return waitingTimeStat;
    }

    public WStat getStatQueueLengthBeforeTakeOver() {
        return queueTakeOver.lengthStatistic();
    }

    public WStat getStatQueueLengthBeforePaying() {
        return queueTakeOver.lengthStatistic();
    }

    public int getCountOfWorkingEmployees() {
        return countOfWorking + countOfPaused;
    }

    public int getCountOfReservedParkingPlaces() {
        return countOfReservedParkingPlaces;
    }

    public int getTotalCountOfEmployees() {
        return totalCountOfEmployees;
    }

    public void addWorkingEmployee() throws java.lang.Exception {
        if (countOfWorking < totalCountOfEmployees) {
            freeEmployersStat.addSample(totalCountOfEmployees - countOfWorking);
            countOfWorking++;
            employee.remove(0);

        } else {
            throw new Exception("Any free workers(type 1)!");
        }
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

    public void addReservedParkingPlace() throws java.lang.Exception {
        if (countOfReservedParkingPlaces < 5) {
            countOfReservedParkingPlaces++;

        } else {
            throw new Exception("Parking places out of space!");
        }
    }

    public void removeWorkingEmployee() throws java.lang.Exception {
        if (countOfWorking > 0) {
            freeEmployersStat.addSample(totalCountOfEmployees - countOfWorking);
            countOfWorking--;
            employee.add(null);

        } else {
            throw new Exception("free workers less then 0(type 1)!");
        }
    }

    public void removeReservedParkingPlace() throws java.lang.Exception {
        if (countOfReservedParkingPlaces > 0) {
            countOfReservedParkingPlaces--;

        } else {
            throw new Exception("Parking places less then 0!");
        }
    }

    public SimQueue<MessageForm> getQueueTakeOver() {
        return queueTakeOver;
    }

    public SimQueue<MessageForm> getQueuePaying() {
        return queuePaying;
    }

    public int getTotalCountOfParkingPlaces() {
        return totalCountOfParkingPlaces;
    }

    public WStat getQueueTakeOverAvgLength() {
        return queueTakeOver.lengthStatistic();
    }

    public long getCOuntOfVehicles() {
        return queuePaying.size() + queueTakeOver.size() + countOfWorking;
    }

    public Stat getWaitingTimeStat() {
        return waitingTimeStat;
    }

    public Stat getFreeEmployersStat() {
        return freeEmployersStat;
    }

    public int getCountOfWorking() {
        return countOfWorking;
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

    public ConcurrentLinkedQueue<MessageForm> getQueueTakeOverGui() {
        return queueTakeOverGui;
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

    public void addEmployeeToPause() {
        countOfPaused++;
        employee.remove(0);
    }

    public int getPauseCounter() {
        return pauseCounter;
    }

    public void addPauseCounter() {
        this.pauseCounter++;
    }

    public double getPauseTimeStartedTime() {
        return pauseTimeStartedTime;
    }

    public void setPauseTimeStartedTime(double pauseTimeStartedTime) {
        this.pauseTimeStartedTime = pauseTimeStartedTime;
    }

    public ConcurrentLinkedQueue<MessageForm> getQueuePayingGui() {
        return queuePayingGui;
    }

    public void setQueuePayingGui(ConcurrentLinkedQueue<MessageForm> queuePayingGui) {
        this.queuePayingGui = queuePayingGui;
    }

    public int findEmpForCustomer(CustomerObject customer) throws Exception {
        for (int i = 0; i < guiEmployers.size(); i++) {
            if (guiEmployers.get(i) != null) {
                if (customer.getCount() == guiEmployers.get(i).getCount()) {
                    return i;
                }
            }
        }
        throw new Exception("Employer for animation error!");
    }

    public int findEmpForPause(CustomerObject customer) throws Exception {
        for (int i = 0; i < guiEmployers.size(); i++) {
            if (guiEmployers.get(i) != null) {
                if (customer.getCount() == guiEmployers.get(i).getCount() && guiEmployers.get(i).isPause()) {
                    return i;
                }
            }
        }
        throw new Exception("Employer for animation error!");
    }

}
