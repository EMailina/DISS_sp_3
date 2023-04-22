package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="1"
public class ManagerModel extends Manager {

    public ManagerModel(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
    }

    //meta! sender="AgentVehicleInspection", id="33", type="Response"
    public void processCustomerService(MessageForm message) {
        message.setCode(Mc.noticeCustomerLeave);
        message.setAddressee(mySim().findAgent(Id.agentEnviroment));
        notice(message);
    }

    //meta! sender="AgentEnviroment", id="31", type="Notice"
    public void processNoticeCustomerArrival(MessageForm message) {
        message.setCode(Mc.customerService);
        message.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
        notice(message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.init:
                processInit(message);
                break;

            case Mc.customerService:
                processCustomerService(message);
                break;

            case Mc.noticeCustomerArrival:
                processNoticeCustomerArrival(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    public void processInit(MessageForm message) {
        message.setAddressee(mySim().findAgent(Id.agentEnviroment));
        notice(message);
    }

    @Override
    public AgentModel myAgent() {
        return (AgentModel) super.myAgent();
    }

}
