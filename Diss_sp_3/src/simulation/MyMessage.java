package simulation;

import OSPABA.*;
import objects.CustomerObject;

public class MyMessage extends MessageForm {

    private CustomerObject customer = new CustomerObject();
    private int countOfParkingPlaces;
    private boolean availableEmployee;
    private double processStartTime;
 
    public MyMessage(Simulation sim) {
        super(sim);
    }

    public MyMessage(MyMessage original) {
        super(original);
        // copy() is called in superclass
        customer = original.customer;
        processStartTime = original.processStartTime;
        countOfParkingPlaces = original.getCountOfParkingPlaces();
        availableEmployee = original.isAvailableEmployee();
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

}
