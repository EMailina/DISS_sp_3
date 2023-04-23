package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="1"
public class AgentModel extends Agent {

    public AgentModel(int id, Simulation mySim, Agent parent) {
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
        new ManagerModel(Id.managerModel, mySim(), this);
        addOwnMessage(Mc.init);
        addOwnMessage(Mc.customerService);
        addOwnMessage(Mc.noticeCustomerArrival);
    }
    //meta! tag="end"

    public void startSimulation() {
        MyMessage message = new MyMessage(super.mySim());
        message.setCode(Mc.init);
        message.setAddressee(this);
        manager().notice(message);
    }
}
