package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import OSPRNG.EmpiricPair;
import OSPRNG.EmpiricRNG;
import OSPRNG.UniformDiscreteRNG;
import objects.VehicleConstants;
import java.util.ArrayList;

//meta! id="27"
public class ProcessInsepction extends Process {

    UniformDiscreteRNG inspectionTimeCarDistribution;
    EmpiricRNG inspectionTimeVanDistribution;
    EmpiricRNG inspectionTimeTruckDistribution;

    public ProcessInsepction(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
        
         inspectionTimeCarDistribution = new UniformDiscreteRNG(31, 45, ((MySimulation) this.mySim()).getGeneratorOfGenerators());

            ArrayList<EmpiricPair> list = new ArrayList<>();
            list.add(new EmpiricPair(new UniformDiscreteRNG(35, 37, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.2));
            list.add(new EmpiricPair(new UniformDiscreteRNG(38, 40, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.35));
            list.add(new EmpiricPair(new UniformDiscreteRNG(41, 47, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.3));
            list.add(new EmpiricPair(new UniformDiscreteRNG(48, 52, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.15));

            EmpiricPair[] obj = new EmpiricPair[list.size()];
            obj = list.toArray(obj);
            inspectionTimeVanDistribution = new EmpiricRNG(((MySimulation) this.mySim()).getGeneratorOfGenerators(), obj);

            obj = null;
            list = new ArrayList<>();
            list.add(new EmpiricPair(new UniformDiscreteRNG(37, 42, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.05));
            list.add(new EmpiricPair(new UniformDiscreteRNG(43, 45, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.1));
            list.add(new EmpiricPair(new UniformDiscreteRNG(46, 47, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.15));
            list.add(new EmpiricPair(new UniformDiscreteRNG(48, 51, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.4));
            list.add(new EmpiricPair(new UniformDiscreteRNG(52, 55, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.25));
            list.add(new EmpiricPair(new UniformDiscreteRNG(56, 65, ((MySimulation) this.mySim()).getGeneratorOfGenerators()), 0.05));
            obj = new EmpiricPair[list.size()];
            obj = list.toArray(obj);
            inspectionTimeTruckDistribution = new EmpiricRNG(((MySimulation) this.mySim()).getGeneratorOfGenerators(), obj);
     
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();

       
    }

    //meta! sender="AgentMechanics", id="28", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeEndInspection);
        if (((MyMessage) message).getCustomer().getProbabilityVehicle() < VehicleConstants.CAR_LIMIT) {
            hold((double) inspectionTimeCarDistribution.sample().doubleValue(), message);
        } else if (((MyMessage) message).getCustomer().getProbabilityVehicle() < VehicleConstants.VAN_LIMIT + VehicleConstants.CAR_LIMIT) {
            hold((double)(inspectionTimeVanDistribution.sample().doubleValue()), message);
        } else {
            hold((double) inspectionTimeTruckDistribution.sample().doubleValue(), message);
        }
        ((MyMessage)message).setProcessStartTime(mySim().currentTime());
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
            case Mc.noticeEndInspection:
                processEndInspection(message);
                break;
            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentMechanics myAgent() {
        return (AgentMechanics) super.myAgent();
    }

    private void processEndInspection(MessageForm message) {
        assistantFinished(message);
    }

}
