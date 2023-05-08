package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import OSPRNG.UniformContinuousRNG;
import animation.ActivityType;
import animation.EmployeeAnimActivity;
import diss_sp_3.RunType;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.CustomerObject;

//meta! id="21"
public class ProcessPaying extends Process {

    private UniformContinuousRNG paymentTimeDistribution;

    public ProcessPaying(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
        paymentTimeDistribution = new UniformContinuousRNG((double) 65 / 60, (double) 177 / 60, ((MySimulation) this.mySim()).getGeneratorOfGenerators());

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
    }

    //meta! sender="AgentReception", id="22", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeEndPaying);
        double duration = paymentTimeDistribution.sample();
        hold(duration, message);
        ((MyMessage) message).setProcessStartTime(mySim().currentTime());
        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            addAnimToWork(getEmployee(((MyMessage) message).getCustomer()), duration + mySim().currentTime());
        }
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
            case Mc.noticeEndPaying:
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
        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            removeAnimToWork(getEmployee(((MyMessage) message).getCustomer()));
        }
    }

    private void addAnimToWork(int count, double endTime) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);
            a.setStartTime(mySim().currentTime());
            a.setEndTime(endTime);
            a.setType(ActivityType.ADD_WORK_TO_EMPLOYER_TYPE_1_PAYMENT);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private void removeAnimToWork(int count) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);
            a.setType(ActivityType.REMOVE_WORK_FROM_EMPLOYER_TYPE_1_PAYMENT);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private int getEmployee(CustomerObject customer) {
        try {
            return myAgent().findExistEmpForCustomer(customer);
        } catch (Exception ex) {
            Logger.getLogger(ProcessInsepction.class.getName()).log(Level.SEVERE, null, ex);
        }
        //error
        return -1;
    }

}
