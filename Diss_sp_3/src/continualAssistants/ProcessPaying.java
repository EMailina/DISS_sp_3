package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;

//meta! id="21"
public class ProcessPaying extends Process {

    private UniformContinuousRNG paymentTimeDistribution;

    public ProcessPaying(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        paymentTimeDistribution = new UniformContinuousRNG((double) 65 / 60, (double) 177 / 60, ((MySimulation) this.mySim()).getGeneratorOfGenerators());
    }

    //meta! sender="AgentReception", id="22", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeEndPaying);
        hold(paymentTimeDistribution.sample(), message);
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
                processEndPaying(message);
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

    private void processEndPaying(MessageForm message) {
        assistantFinished(message);
    }

}
