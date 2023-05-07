/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import agents.AgentEnviroment;
import simulation.Mc;


/**
 *
 * @author Erik
 */
public class SchedulerAnimation extends Scheduler {

    private double tau = 1.0/5.0;

    public SchedulerAnimation(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
    }

    public void processStart(MessageForm message) {
        message.setCode(Mc.noticeDrawAnimation);
        hold(tau, message);
    }

    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                processStart(message);
                break;

            case Mc.noticeDrawAnimation:
                processDraw(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }

    @Override
    public AgentEnviroment myAgent() {
        return (AgentEnviroment) super.myAgent();
    }
    
    private void processDraw(MessageForm message) {
        MessageForm copy = message.createCopy();
        hold(tau, copy);
        assistantFinished(message);
    }

}
