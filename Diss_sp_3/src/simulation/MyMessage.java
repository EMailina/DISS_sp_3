package simulation;

import OSPABA.*;
import objects.CustomerObject;

public class MyMessage extends MessageForm {

    private CustomerObject customer = new CustomerObject();
    private int countOfParkingPlaces;
    private boolean availableEmployee;
    private double processStartTime;
    private double processEndTime;
    private int certificate2;
    private boolean executeWithCertficated;
    private int animEmployer;

    public MyMessage(Simulation sim) {
        super(sim);
    }

    public MyMessage(MyMessage original) {
        super(original);
        // copy() is called in superclass
        customer = original.customer;
        animEmployer = original.animEmployer;
        processStartTime = original.processStartTime;
        countOfParkingPlaces = original.getCountOfParkingPlaces();
        availableEmployee = original.isAvailableEmployee();
        certificate2 = original.certificate2;
        executeWithCertficated = original.executeWithCertficated;
        processEndTime = original.processEndTime;
    }

    @Override
    public MessageForm createCopy() {
        return new MyMessage(this);
    }

    public CustomerObject getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerObject customer) {
        this.customer = customer;
    }

    public int getCountOfParkingPlaces() {
        return countOfParkingPlaces;
    }

    public void setCountOfParkingPlaces(int countOfParkingPlaces) {
        this.countOfParkingPlaces = countOfParkingPlaces;
    }

    public boolean isAvailableEmployee() {
        return availableEmployee;
    }

    public void setAvailableEmployee(boolean availableEmployee) {
        this.availableEmployee = availableEmployee;
    }

    public double getProcessStartTime() {
        return processStartTime;
    }

    public void setProcessStartTime(double processStartTime) {
        this.processStartTime = processStartTime;
    }

    public int getCertificate2() {
        return certificate2;
    }

    public void setCertificate2(int certificate2) {
        this.certificate2 = certificate2;
    }

    public boolean isExecuteWithCertficated() {
        return executeWithCertficated;
    }

    public void setExecuteWithCertficated(boolean executeWithCertficated) {
        this.executeWithCertficated = executeWithCertficated;
    }

    public double getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(double processEndTime) {
        this.processEndTime = processEndTime;
    }

    public int getAnimEmployer() {
        return animEmployer;
    }

    public void setAnimEmployer(int animEmployer) {
        this.animEmployer = animEmployer;
    }

    
}
