package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;

//meta! id="77"
public class SchedulerLunchPause extends Scheduler {

    private int constPauseTime = 120;

    public SchedulerLunchPause(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! sender="AgentEnviroment", id="78", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeLunchPause);
        ((MyMessage) message).setCustomer(null);
        hold(constPauseTime, message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;

            case Mc.noticeLunchPause:
                processNoticeLunchPause(message);
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

    private void processNoticeLunchPause(MessageForm message) {
        assistantFinished(message);
    }

}
