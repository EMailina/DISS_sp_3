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
import animation.EmployeeAnimActivity;
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
public class ProcessMoveToPayment extends OSPABA.Process{
    
    private double moveLength = 20;

    public ProcessMoveToPayment(int id, Simulation mySim, CommonAgent myAgent) {
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
        message.setCode(Mc.noticeEndMoveToPayment);
        hold(moveLength, message);

        addAnimToWork(getEmployee(((MyMessage) message).getCustomer()), moveLength);

    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;

            case Mc.noticeEndMoveToPayment: {
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
        myAgent().removeFromEmployer(((MyMessage) message).getCustomer());
        assistantFinished(message);
    }

    private void addAnimToWork(int count, double endTime) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);
            a.setStartTime(mySim().currentTime());
            a.setEndTime(endTime + mySim().currentTime());
            a.setType(ActivityType.ADD_MOVE_TO_PAYMENT);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private int getEmployee(CustomerObject customer) {
        try {

            return myAgent().addToEmployer(customer, false, 0);
        } catch (Exception ex) {
            Logger.getLogger(ProcessInsepction.class.getName()).log(Level.SEVERE, null, ex);
        }
        //error
        return -1;
    }

}
