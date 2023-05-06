/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import agents.AgentMechanics;
import agents.AgentVehicleInspection;
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
public class ProcessMoveFromInspection extends OSPABA.Process {

    private double moveLength = 20;

    public ProcessMoveFromInspection(int id, Simulation mySim, CommonAgent myAgent) {
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
        message.setCode(Mc.noticeEndMoveFromInspection);

        int emp = ((MyMessage) message).getAnimEmployer();
        addAnimToWork(emp, moveLength);
        ((MyMessage) message).setAnimEmployer(emp);
        hold(moveLength, message);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;

            case Mc.noticeEndMoveFromInspection: {
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
    public AgentVehicleInspection myAgent() {
        return (AgentVehicleInspection) super.myAgent();
    }

    private void processNoticeEndPause(MessageForm message) throws Exception {
        assistantFinished(message);
    }

    private void addAnimToWork(int count, double endTime) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);
            a.setStartTime(mySim().currentTime());
            a.setEndTime(endTime + mySim().currentTime());
            a.setType(ActivityType.ADD_MOVE_FROM_INSPECTION);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

  

}
