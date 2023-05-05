package continualAssistants;

import OSPABA.*;
import simulation.*;
import agents.*;
import OSPABA.Process;
import animation.ActivityType;
import animation.EmployeeAnimActivity;
import diss_sp_3.RunType;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.CustomerObject;

//meta! id="85"
public class ProcessLunchPauseReception extends Process {

    private double pauseLength = 30;

    public ProcessLunchPauseReception(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! sender="AgentMechanics", id="89", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeEndPause);
        hold(pauseLength, message);
        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            addAnimToWork(getEmployee(((MyMessage) message).getCustomer()), pauseLength);
        }

    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;

            case Mc.noticeEndPause:
                processNoticeEndPause(message);
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

    private void processNoticeEndPause(MessageForm message) {
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
            a.setEndTime(endTime + mySim().currentTime());
            a.setType(ActivityType.ADD_PAUSE_TO_EMPLOYER_TYPE_1);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private void removeAnimToWork(int count) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);

            a.setType(ActivityType.REMOVE_PAUSE_FROM_EMPLOYER_TYPE_1);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private int getEmployee(CustomerObject customer) {
        try {
            return myAgent().findEmpForPause(customer);
        } catch (Exception ex) {
            Logger.getLogger(ProcessInsepction.class.getName()).log(Level.SEVERE, null, ex);
        }
        //error
        return -1;
    }

}
