package continualAssistants;

import OSPABA.*;
import OSPRNG.ExponentialRNG;
import OSPRNG.UniformContinuousRNG;
import simulation.*;
import agents.*;
import objects.CustomerObject;

//meta! id="9"
public class SchedulerCustomerArrival extends Scheduler {

    private ExponentialRNG arrivalExponentialDistribution;
    private UniformContinuousRNG vehicleDistribution;
    private int count = 1;

    public SchedulerCustomerArrival(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        arrivalExponentialDistribution = new ExponentialRNG((double) 60 / 23, ((MySimulation) this.mySim()).getGeneratorOfGenerators());
        vehicleDistribution = new UniformContinuousRNG((double) 0, (double) 1, ((MySimulation) this.mySim()).getGeneratorOfGenerators());
        count = 1;
    }

    //meta! sender="AgentEnviroment", id="10", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeCustomerArrival);
        hold(arrivalExponentialDistribution.sample(), message);
        // processCustomerArrival(message);

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

            case Mc.noticeCustomerArrival:
                processCustomerArrival(message);
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

    private void processCustomerArrival(MessageForm message) {
        double time = arrivalExponentialDistribution.sample();
        MessageForm copy = message.createCopy();

        if (mySim().currentTime() + time <= 405) {

            hold(time, copy);
            CustomerObject customer = new CustomerObject();
          
            customer.setCount(count);
              count++;
            customer.setProbabilityVehicle(vehicleDistribution.sample());
            ((MyMessage) message).setCustomer(customer);
            assistantFinished(message);
        }
    }

}
