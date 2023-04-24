package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import OSPRNG.ExponentialRNG;
import OSPRNG.TriangularRNG;

//meta! id="19"
public class ProcessTakeOverVehicle extends Process {

    private TriangularRNG takeOverVehicleDistribution;

    public ProcessTakeOverVehicle(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        if (mySim().currentReplication() == 0) {
            takeOverVehicleDistribution = new TriangularRNG((double) 180 / 60, (double) 431 / 60, (double) 695 / 60, ((MySimulation) this.mySim()).getGeneratorOfGenerators());
        }
    }

    //meta! sender="AgentReception", id="20", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeEndTakeOver);
        hold(takeOverVehicleDistribution.sample(), message);
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
            case Mc.noticeEndTakeOver:
                processEndTakeOver(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentReception myAgent() {
        return (AgentReception) super.myAgent();
    }

    private void processEndTakeOver(MessageForm message) {
        assistantFinished(message);
    }

}
