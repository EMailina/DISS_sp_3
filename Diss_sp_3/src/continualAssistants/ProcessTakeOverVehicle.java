package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import OSPRNG.TriangularRNG;
import animation.ActivityType;
import animation.AnimTimeCounter;
import animation.Animator;
import animation.EmployeeAnimActivity;
import diss_sp_3.RunType;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.CustomerObject;

//meta! id="19"
public class ProcessTakeOverVehicle extends Process {

    private TriangularRNG takeOverVehicleDistribution;
    private AnimTimeCounter timeCounter;

    public ProcessTakeOverVehicle(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);

        takeOverVehicleDistribution = new TriangularRNG((double) 180 / 60, (double) 431 / 60, (double) 695 / 60, ((MySimulation) this.mySim()).getGeneratorOfGenerators());

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        if (((MySimulation) mySim()).getAnimator() != null) {
            timeCounter = new AnimTimeCounter((Animator) ((MySimulation) mySim()).getAnimator());
        }
    }
    //meta! sender="AgentReception", id="20", type="Start"

    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeEndTakeOver);
        double duration = takeOverVehicleDistribution.sample();
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
        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            removeAnimToWork(getEmployee(((MyMessage) message).getCustomer()));
        }
    }

    private void addAnimToWork(int count, double endTime) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);
            double moveLength = timeCounter.getTimePerRouteFromEmp1(count);
            a.setStartMovingTime(endTime - moveLength);
            a.setStartTime(mySim().currentTime());
            a.setStaticObject(true);
            a.setEndTime(endTime);
            a.setType(ActivityType.ADD_WORK_TO_EMPLOYER_TYPE_1);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private void removeAnimToWork(int count) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);

            a.setType(ActivityType.REMOVE_WORK_FROM_EMPLOYER_TYPE_1);
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
