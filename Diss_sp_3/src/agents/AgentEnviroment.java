package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="2"
public class AgentEnviroment extends Agent {

    public AgentEnviroment(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
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
}
