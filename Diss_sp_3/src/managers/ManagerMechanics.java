package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import diss_sp_3.RunType;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.CustomerObject;

//meta! id="4"
public class ManagerMechanics extends Manager {

    public ManagerMechanics(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
    }

    //meta! sender="AgentVehicleInspection", id="44", type="Request"
    public void processMechanicExecute(MessageForm message) {
        if (myAgent().getCountOfWorking() < myAgent().getTotalCountOfEmployees()) {
            addToEmployer(((MyMessage) message).getCustomer());
            myAgent().addWorkingEmployee();
            ((MyMessage) message).setProcessStartTime(mySim().currentTime());
            message.setAddressee(myAgent().findAssistant(Id.processInsepction));
            startContinualAssistant(message);
            //System.out.println("Start service: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        }
    }

    //meta! sender="ProcessInsepction", id="28", type="Finish"
    public void processFinish(MessageForm message) {
        removeFromEmployer(((MyMessage) message).getCustomer());
        myAgent().removeWorkingEmployee();
        // System.out.println("END service: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        boolean pause = false;
        //pause
        if (myAgent().isPauseTimeStarted()) {
            if (((MyMessage) message).getProcessStartTime() < myAgent().getPauseTimeStartedTime()) {
                addPauseToEmployerGui();
                MyMessage newMessage = (MyMessage)  message.createCopy();
                myAgent().addEmployeeToPause();
                newMessage.setAddressee(myAgent().findAssistant(Id.processLunchPauseInspection));
                startContinualAssistant(newMessage);
                pause = true;
            }

        }
        if (!pause) {
            ((MyMessage) message).setAvailableEmployee(true);
            message.setCode(Mc.mechanicExecute);
            response(message);
        }else{
            ((MyMessage) message).setAvailableEmployee(false);
            message.setCode(Mc.mechanicExecute);
            response(message);
        }
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {

        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.mechanicExecute:
                processMechanicExecute(message);
                break;

            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.processInsepction: {
                        processFinish(message);
                    }
                    break;

                    case Id.processLunchPauseInspection:
                        processFinishProcessLunchPause(message);
                        break;
                }
                break;

            case Mc.mechanicsAvailability:
                processMechanicsAvailability(message);
                break;

            case Mc.noticeLunchPause:
                processNoticeLunchPause(message);
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

    private void processMechanicsAvailability(MessageForm message) {
        if (myAgent().getCountOfWorking() < myAgent().getTotalCountOfEmployees()) {
            ((MyMessage) message).setAvailableEmployee(true);
        } else {
            ((MyMessage) message).setAvailableEmployee(false);
        }
        response(message);
    }

    public void addToEmployer(CustomerObject customer) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            for (int i = 0; i < myAgent().getTotalCountOfEmployees(); i++) {
                if (myAgent().getGuiEmployers().get(i) == null) {

                    myAgent().getGuiEmployers().set(i, customer);
                    customer.setInspectionRewrite(true);
//                    if (takeOver) {
//                        customer.setTakeOverRewrite(true);
//                    } else {
//                        customer.setPaymentRewrite(true);
//                    }
                    break;
                }
            }
        }
    }

    private void removeFromEmployer(CustomerObject customer) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            for (int i = 0; i < myAgent().getTotalCountOfEmployees(); i++) {
                if (customer.equals(myAgent().getGuiEmployers().get(i))) {
                    myAgent().getGuiEmployers().set(i, null);
                    break;
                }
            }
        }
    }

    private void processNoticeLunchPause(MessageForm message) {
       // System.out.println("pauza 2");
        myAgent().setPauseTimeStarted(true);
        myAgent().setPauseTimeStartedTime(mySim().currentTime());
        myAgent().addPausedEmployees();
        ((MyMessage) message).setProcessStartTime(mySim().currentTime());
        for (int i = 0; i < myAgent().getCountOfPaused(); i++) {
            message = message.createCopy();
            message.setCode(Mc.start);
            message.setAddressee(myAgent().findAssistant(Id.processLunchPauseInspection));
            startContinualAssistant(message);
            addPauseToEmployerGui();
        }
    }

    public void addPauseToEmployerGui() {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            for (int i = 0; i < myAgent().getTotalCountOfEmployees(); i++) {
                if (myAgent().getGuiEmployers().get(i) == null) {
                    CustomerObject customer = new CustomerObject();
                    customer.setCount(myAgent().getPauseCounter());
                    myAgent().addPauseCounter();
                    myAgent().getGuiEmployers().set(i, customer);
                    customer.setPause(true);
                    break;
                }
            }
        }
    }

    private void removePauseFromEmployer() {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int i = 0; i < myAgent().getTotalCountOfEmployees(); i++) {
                if (myAgent().getGuiEmployers().get(i) != null) {
                    if (myAgent().getGuiEmployers().get(i).isPause()) {
                        if (min > myAgent().getGuiEmployers().get(i).getCount()) {
                            min = (int) myAgent().getGuiEmployers().get(i).getCount();
                            index = i;
                        }
                    }

                }
            }
            myAgent().getGuiEmployers().set(index, null);
        }
    }

    private void processFinishProcessLunchPause(MessageForm message) {
        //System.out.println("stop pauza 2");
        myAgent().removePausedEmployees();
        removePauseFromEmployer();
        message.setAddressee(Id.agentVehicleInspection);
        message.setCode(Mc.noticeFreeMechanic);
        notice(message);
    }
}
