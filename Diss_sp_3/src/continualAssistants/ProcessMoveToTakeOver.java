/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import agents.AgentReception;
import animation.ActivityType;
import animation.AnimTimeCounter;
import animation.Animator;
import animation.EmployeeAnimActivity;
import diss_sp_3.RunType;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.CustomerObject;
import simulation.Mc;
import simulation.MyMessage;
import simulation.MySimulation;

/**
 *
 * @author Erik
 */
public class ProcessMoveToTakeOver extends OSPABA.Process {

    private double moveLength = 20;
    private AnimTimeCounter timeCounter;

    public ProcessMoveToTakeOver(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);

    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        if (((MySimulation) mySim()).getAnimator() != null) {
            timeCounter = new AnimTimeCounter((Animator) ((MySimulation) mySim()).getAnimator());
        }
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! sender="AgentMechanics", id="89", type="Start"
    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeEndMoveToTakeOver);
        int emp = getEmployee(((MyMessage) message).getCustomer());
        moveLength = timeCounter.getTimePerRouteToEmp1(emp);
        hold(moveLength, message);

        addAnimToWork(emp, moveLength);

    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;

            case Mc.noticeEndMoveToTakeOver: {
                try {
                    processNoticeEndPause(message);
                } catch (Exception ex) {
                    Logger.getLogger(ProcessMoveToTakeOver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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

    private void processNoticeEndPause(MessageForm message) throws Exception {
        //myAgent().removeFromEmployer(((MyMessage) message).getCustomer());
        assistantFinished(message);
    }

    private void addAnimToWork(int count, double endTime) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);
            a.setStartTime(mySim().currentTime());
            a.setEndTime(endTime + mySim().currentTime());
            a.setType(ActivityType.ADD_MOVE_TO_TAKE_OVER);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private int getEmployee(CustomerObject customer) {
        try {
            return myAgent().addToEmployer(customer, true, 0);
        } catch (Exception ex) {
            Logger.getLogger(ProcessInsepction.class.getName()).log(Level.SEVERE, null, ex);
        }
        //error
        return -1;
    }

}
