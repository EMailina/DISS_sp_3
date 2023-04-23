package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import OSPStat.WStat;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="3"
public class AgentReception extends Agent {

    private int totalCountOfEmployees = 1;
    private int totalCountOfParkingPlaces = 5;
    private SimQueue<MessageForm> queueTakeOver;
    private SimQueue<MessageForm> queueTakeOverTest;
    private SimQueue<MessageForm> queuePaying;
    private Stat waitingTimeStat;
    private WStat freeEmployersStat;
    private int countOfWorking = 0;
    private int countOfReservedParkingPlaces = 0;
    private SimQueue<MessageForm> employee;

    public AgentReception(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        queueTakeOver = new SimQueue<>(new WStat(_mySim));
        queueTakeOverTest = new SimQueue<>(new WStat(_mySim));
        queuePaying = new SimQueue<>(new WStat(_mySim));
        waitingTimeStat = new Stat();
        freeEmployersStat = new WStat(_mySim);
        countOfWorking = 0;
        countOfReservedParkingPlaces = 0;
        employee = new SimQueue<>(new WStat(_mySim));
        employee.add(null);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerReception(Id.managerReception, mySim(), this);
        new ProcessTakeOverVehicle(Id.processTakeOverVehicle, mySim(), this);
        new ProcessPaying(Id.processPaying, mySim(), this);

        addOwnMessage(Mc.checkParkingPlace);
        addOwnMessage(Mc.paymentExecute);
        addOwnMessage(Mc.receptionExecute);
        addOwnMessage(Mc.noticeEndTakeOver);
        addOwnMessage(Mc.noticeEndPaying);
        addOwnMessage(Mc.noticeFreeParking);
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
        return countOfWorking;
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

    public SimQueue<MessageForm> getQueueTakeOverTest() {
        return queueTakeOverTest;
    }

    public void setQueueTakeOverTest(SimQueue<MessageForm> queueTakeOverTest) {
        this.queueTakeOverTest = queueTakeOverTest;
    }

    
    
}
