package managers;

import OSPABA.*;
import simulation.*;
import agents.*;


//meta! id="2"
public class ManagerEnviroment extends Manager {

    public ManagerEnviroment(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentModel", id="47", type="Notice"
    public void processInit() {
        MyMessage message = new MyMessage(super.mySim());
        message.setAddressee(myAgent().findAssistant(Id.schedulerCustomerArrival));
        startContinualAssistant(message);
        if (!((MySimulation) mySim()).isValidationRun()) {
            message = (MyMessage) message.createCopy();
            message.setAddressee(myAgent().findAssistant(Id.schedulerLunchPause));
            startContinualAssistant(message);
        }
    }

    //meta! sender="AgentModel", id="32", type="Notice"
    public void processNoticeCustomerLeave(MessageForm message) {

        myAgent().getAverageTimeInSystem().addSample(mySim().currentTime() - ((MyMessage) message).getCustomer().getStartOfWaitingForTakeOver());

        myAgent().getCustomers().remove(((MyMessage) message).getCustomer());
    }

    //meta! sender="SchedulerCustomerArrival", id="10", type="Finish"
    public void processFinishCustomerArrival(MessageForm message) {
        myAgent().getCustomers().add(((MyMessage) message).getCustomer());
        message.setCode(Mc.noticeCustomerArrival);
        message.setAddressee(mySim().findAgent(Id.agentModel));
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
                processInit();
                break;

            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.schedulerLunchPause:
                        processFinishSchedulerLunchPause(message);
                        break;

                    case Id.schedulerCustomerArrival:
                        processFinishCustomerArrival(message);
                        break;
                }
                break;

            case Mc.noticeCustomerLeave:
                processNoticeCustomerLeave(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentEnviroment myAgent() {
        return (AgentEnviroment) super.myAgent();
    }

    private void processFinishSchedulerLunchPause(MessageForm message) {
        message.setCode(Mc.noticeLunchPause);
        message.setAddressee(mySim().findAgent(Id.agentModel));
        notice(message);
    }

}
