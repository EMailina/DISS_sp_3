package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import java.util.ArrayList;
import objects.CustomerObject;

//meta! id="4"
public class AgentMechanics extends Agent {

    //FINAL COUNT
    private int totalCountOfEmployeesWithCertificate1 = 2;
    private int totalCountOfEmployeesWithCertificate2 = 2;

    //COUNT OF WORKING
    private int countOfWorkingWithCertificate1;
    private int countOfWorkingWithCertificate2;

    private SimQueue<MessageForm> employeeWithCertificate1;
    private SimQueue<MessageForm> employeeWithCertificate2;

    private ArrayList<CustomerObject> guiEmployers;

    private int countOfPausedCertificate1 = 0;
    private int countOfPausedCertificate2 = 0;

    private boolean pauseTimeStarted = false;
    private double pauseTimeStartedTime = Double.MAX_VALUE;
    private int pauseCounter = 0;

    private double messageTime = -1;
    private boolean duplicatedTime = false;

    public AgentMechanics(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        countOfWorkingWithCertificate1 = 0;
        countOfWorkingWithCertificate2 = 0;
        employeeWithCertificate1 = new SimQueue<>(new WStat(_mySim));

        for (int i = 0; i < getTotalCountOfEmployeesWithCertificate1(); i++) {
            employeeWithCertificate1.add(null);
        }

        employeeWithCertificate2 = new SimQueue<>(new WStat(_mySim));

        for (int i = 0; i < getTotalCountOfEmployeesWithCertificate2(); i++) {
            employeeWithCertificate2.add(null);
        }

        guiEmployers = new ArrayList<>(getTotalCountOfEmployees());
        for (int i = 0; i < getTotalCountOfEmployees(); i++) {
            guiEmployers.add(null);
        }
        countOfPausedCertificate1 = 0;
        countOfPausedCertificate2 = 0;
        pauseTimeStarted = false;
        pauseTimeStartedTime = Double.MAX_VALUE;
        pauseCounter = 0;
        messageTime = -1;
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
        addOwnMessage(Mc.noticeTruckInspection);
    }
    //meta! tag="end"

    public int getTotalCountOfEmployees() {
        return totalCountOfEmployeesWithCertificate1 + totalCountOfEmployeesWithCertificate2;
    }

    public int getCountOfWorkingC1() {
        return countOfPausedCertificate1 + countOfWorkingWithCertificate1;
    }

    public int getCountOfAllWorking() {
        return countOfPausedCertificate1 + countOfWorkingWithCertificate1 + countOfPausedCertificate2 + countOfWorkingWithCertificate2;
    }

    public int getCountOfWorkingC2() {
        return countOfPausedCertificate2 + countOfWorkingWithCertificate2;
    }

    public void addWorkingEmployeeC1() throws Exception {
        if (getCountOfWorkingC1() < totalCountOfEmployeesWithCertificate1) {
            countOfWorkingWithCertificate1++;
            employeeWithCertificate1.remove(0);
        } else {
            throw new Exception("Any free workers(type 2) WITH CERTIFICATE 1!");
        }
    }

    public void addWorkingEmployeeC2() throws Exception {
        if (getCountOfWorkingC2() < totalCountOfEmployeesWithCertificate2) {
            countOfWorkingWithCertificate2++;
            employeeWithCertificate2.remove(0);
        } else {
            throw new Exception("Any free workers(type 2) WITH CERTIFICATE 2! TIME: " + mySim().currentTime());
        }
    }

    public void removeWorkingEmployeeC2() throws Exception {
        if (getCountOfWorkingC2() > 0) {
            countOfWorkingWithCertificate2--;
            employeeWithCertificate2.add(null);
        } else {
            throw new Exception("Any free workers(type 2) WITH CERTIFICATE 2!");
        }
    }

    public void removeWorkingEmployeeC1() throws Exception {
        if (getCountOfWorkingC1() > 0) {
            countOfWorkingWithCertificate1--;
            employeeWithCertificate1.add(null);
        } else {
            throw new Exception("Any free workers(type 2) WITH CERTIFICATE 1!");
        }
    }

    public long getCountOfVehicles() {
        return countOfWorkingWithCertificate1 + countOfWorkingWithCertificate2;
    }

    public ArrayList<CustomerObject> getGuiEmployers() {
        return guiEmployers;
    }

    public void setGuiEmployers(ArrayList<CustomerObject> guiEmployers) {
        this.guiEmployers = guiEmployers;
    }

    public int getCountOfWorkingWithCertificate1() {
        return countOfWorkingWithCertificate1;
    }

    public void setCountOfWorkingWithCertificate1(int countOfWorkingWithCertificate1) {
        this.countOfWorkingWithCertificate1 = countOfWorkingWithCertificate1;
    }

    public int getCountOfPausedCertificate1() {
        return countOfPausedCertificate1;
    }

    public void setCountOfPausedCertificate1(int countOfPausedCertificate1) {
        this.countOfPausedCertificate1 = countOfPausedCertificate1;
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
        countOfPausedCertificate1 = totalCountOfEmployeesWithCertificate1 - countOfWorkingWithCertificate1;
        for (int i = 0; i < countOfPausedCertificate1; i++) {
            employeeWithCertificate1.remove(0);
        }

        countOfPausedCertificate2 = totalCountOfEmployeesWithCertificate2 - countOfWorkingWithCertificate2;
        for (int i = 0; i < countOfPausedCertificate2; i++) {
            employeeWithCertificate2.remove(0);
        }
    }

    public void removePausedEmployeesC1() {
        countOfPausedCertificate1--;
        employeeWithCertificate1.add(null);
    }

    public void removePausedEmployeesC2() {
        countOfPausedCertificate2--;
        employeeWithCertificate2.add(null);
    }

    public void addPauseCounter() {
        this.pauseCounter++;
    }

    public void addEmployeeToPauseC1() {
        countOfPausedCertificate1++;
        employeeWithCertificate1.remove(0);
    }

    public void addEmployeeToPauseC2() {
        countOfPausedCertificate2++;
        employeeWithCertificate2.remove(0);
    }

    public int getTotalCountOfEmployeesWithCertificate2() {
        return totalCountOfEmployeesWithCertificate2;
    }

    public void setTotalCountOfEmployeesWithCertificate2(int totalCountOfEmployeesWithCertificate2) {
        this.totalCountOfEmployeesWithCertificate2 = totalCountOfEmployeesWithCertificate2;
    }

    public int getCountOfWorkingWithCertificate2() {
        return countOfWorkingWithCertificate2 + countOfPausedCertificate2;
    }

    public void setCountOfWorkingWithCertificate2(int countOfWorkingWithCertificate2) {
        this.countOfWorkingWithCertificate2 = countOfWorkingWithCertificate2;
    }

    public int getFreeCertificate2Employees() {
        return totalCountOfEmployeesWithCertificate2 - countOfWorkingWithCertificate2;
    }

    public int getCountOfPausedCertificate2() {
        return countOfPausedCertificate2;
    }

    public int getTotalCountOfEmployeesWithCertificate1() {
        return totalCountOfEmployeesWithCertificate1;
    }

    public void setCountOfPausedCertificate2(int countOfPausedCertificate2) {
        this.countOfPausedCertificate2 = countOfPausedCertificate2;
    }

    public void setTotalCountOfEmployeesWithCertificate1(int totalCountOfEmployeesWithCertificate1) {
        this.totalCountOfEmployeesWithCertificate1 = totalCountOfEmployeesWithCertificate1;
    }

    public SimQueue<MessageForm> getEmployeeWithCertificate1() {
        return employeeWithCertificate1;
    }

    public SimQueue<MessageForm> getEmployeeWithCertificate2() {
        return employeeWithCertificate2;
    }

    public double getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(double messageTime) {
        this.messageTime = messageTime;
    }

    public boolean isDuplicatedTime() {
        return duplicatedTime;
    }

    public void setDuplicatedTime(boolean duplicatedTime) {
        this.duplicatedTime = duplicatedTime;
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
