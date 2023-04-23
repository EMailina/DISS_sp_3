package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.Stat;
import OSPStat.WStat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import objects.CustomerObject;

//meta! id="2"
public class AgentEnviroment extends Agent {

    private SimQueue<CustomerObject> customers;
    private Stat averageTimeInSystem;

    public AgentEnviroment(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        averageTimeInSystem = new Stat();
       customers = new SimQueue<>(new WStat(_mySim));
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerEnviroment(Id.managerEnviroment, mySim(), this);
        new SchedulerCustomerArrival(Id.schedulerCustomerArrival, mySim(), this);
        addOwnMessage(Mc.init);
        addOwnMessage(Mc.noticeCustomerLeave);
        addOwnMessage(Mc.noticeCustomerArrival);
    }
    //meta! tag="end"

    public ContinualAssistant getContinualAssistant() {
        return this.getContinualAssistant();
    }

    public Stat getAverageTimeInSystem() {
        return averageTimeInSystem;
    }

    public void setAverageTimeInSystem(Stat averageTimeInSystem) {
        this.averageTimeInSystem = averageTimeInSystem;
    }

    public SimQueue<CustomerObject> getCustomers() {
        return customers;
    }

    public void setCustomers(SimQueue<CustomerObject> customers) {
        this.customers = customers;
    }

    
}
