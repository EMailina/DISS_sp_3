package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import animation.ActivityType;
import animation.EmployeeAnimActivity;
import continualAssistants.ProcessInsepction;
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
    public void processMechanicExecute(MessageForm message) throws Exception {
        if (myAgent().getCountOfAllWorking() < myAgent().getTotalCountOfEmployees()) {
            if (((MySimulation) mySim()).getAnimator() == null) {
                if (((MyMessage) message).getCustomer().isTruck()) {
                    myAgent().addWorkingEmployeeC2();

                    ((MyMessage) message).setExecuteWithCertficated(true);

                } else {
                    if (myAgent().getCountOfWorkingC1() < myAgent().getTotalCountOfEmployeesWithCertificate1()) {
                        myAgent().addWorkingEmployeeC1();
                        ((MyMessage) message).setExecuteWithCertficated(false);
                    } else {

                        myAgent().addWorkingEmployeeC2();
                        ((MyMessage) message).setExecuteWithCertficated(true);
                    }
                }

                if (mySim().currentTime() != myAgent().getMessageTime()) {
                    myAgent().setMessageTime(mySim().currentTime());
                    MessageForm newMessage = message.createCopy();
                    newMessage.setAddressee(Id.agentMechanics);
                    newMessage.setCode(Mc.noticeTruckInspection);
                    notice(newMessage);
                }

                ((MyMessage) message).setProcessStartTime(mySim().currentTime());
                message.setAddressee(myAgent().findAssistant(Id.processInsepction));
                startContinualAssistant(message);

                addToEmployer(((MyMessage) message).getCustomer(), ((MyMessage) message).isExecuteWithCertficated(), 0);
                //((MyMessage) message).setAnimEmployer(emp);
            } else {
                startMoveToInspection(message);
            }

        }
    }

    //meta! sender="ProcessInsepction", id="28", type="Finish"
    public void processFinish(MessageForm message) throws Exception {
        //if (((MySimulation) mySim()).getAnimator() == null) {
        removeFromEmployer(((MyMessage) message).getCustomer());
        //}
        if (((MyMessage) message).isExecuteWithCertficated()) {
            myAgent().removeWorkingEmployeeC2();
        } else {
            myAgent().removeWorkingEmployeeC1();
        }

        boolean pause = false;
        //pause
        if (myAgent().isPauseTimeStarted()) {
            if (((MyMessage) message).getProcessStartTime() < myAgent().getPauseTimeStartedTime()) {
                if (((MyMessage) message).isExecuteWithCertficated()) {
                    myAgent().addEmployeeToPauseC2();
                } else {
                    myAgent().addEmployeeToPauseC1();
                }
                MyMessage newMessage = (MyMessage) message.createCopy();
                ((MyMessage) newMessage).getCustomer().setCount(myAgent().getPauseCounter());
                addPauseToEmployerGui(((MyMessage) message).isExecuteWithCertficated(), ((MyMessage) message).getProcessEndTime());

                newMessage.setExecuteWithCertficated(((MyMessage) message).isExecuteWithCertficated());
                newMessage.setAddressee(myAgent().findAssistant(Id.processLunchPauseInspection));

                startContinualAssistant(newMessage);
                pause = true;
            }

        }

        if (((MyMessage) message).isExecuteWithCertficated()) {
            ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());
        } else {
            ((MyMessage) message).setCertificate2(0);
        }

        if (!pause) {
            ((MyMessage) message).setAvailableEmployee(true);
            message.setCode(Mc.mechanicExecute);
            response(message);
        } else {
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
            case Mc.mechanicExecute: {
                try {
                    processMechanicExecute(message);
                } catch (Exception ex) {
                    Logger.getLogger(ManagerMechanics.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.processInsepction: {
                        try {
                            processFinish(message);
                        } catch (Exception ex) {
                            Logger.getLogger(ManagerMechanics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                    case Id.processLunchPauseInspection:
                        processFinishProcessLunchPause(message);
                        break;

                    case Id.processMoveToInspection: {
                        try {
                            processFinishProcessMoveToInspection(message);
                        } catch (Exception ex) {
                            Logger.getLogger(ManagerMechanics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                }
                break;

            case Mc.mechanicsAvailability:
                processMechanicsAvailability(message);
                break;

            case Mc.noticeLunchPause:
                processNoticeLunchPause(message);
                break;

            case Mc.noticeTruckInspection:
                processNoticeTruckInspection(message);
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
        if (myAgent().getCountOfAllWorking() < myAgent().getTotalCountOfEmployees()) {
            ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());
            ((MyMessage) message).setAvailableEmployee(true);
        } else {
            ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());
            ((MyMessage) message).setAvailableEmployee(false);
        }
        response(message);
    }

    public void addToEmployer(CustomerObject customer, boolean c2, double endTime) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            int start = 0;
            if (c2) {
                start = myAgent().getTotalCountOfEmployeesWithCertificate1();
            }
            for (int i = start; i < myAgent().getTotalCountOfEmployees(); i++) {
                if (myAgent().getGuiEmployers().get(i) == null) {

                    myAgent().getGuiEmployers().set(i, customer);
                    customer.setInspectionRewrite(true);
                    //addAnimToWork(i, endTime);
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
                    //removeAnimToWork(i);
                    break;
                }
            }
        }
    }

    private void processNoticeLunchPause(MessageForm message) {
        //System.out.println("pauza 2");
        myAgent().setPauseTimeStarted(true);
        myAgent().setPauseTimeStartedTime(mySim().currentTime());
        myAgent().addPausedEmployees();
        ((MyMessage) message).setProcessStartTime(mySim().currentTime());
        for (int i = 0; i < myAgent().getCountOfPausedCertificate1(); i++) {
            message = message.createCopy();
            message.setCode(Mc.start);
            ((MyMessage) message).setExecuteWithCertficated(false);
            message.setAddressee(myAgent().findAssistant(Id.processLunchPauseInspection));
            ((MyMessage) message).setCustomer(new CustomerObject());

            ((MyMessage) message).getCustomer().setCount(myAgent().getPauseCounter());
            startContinualAssistant(message);
            addPauseToEmployerGui(false, ((MyMessage) message).getProcessEndTime());

        }

        for (int i = 0; i < myAgent().getCountOfPausedCertificate2(); i++) {
            message = message.createCopy();
            message.setCode(Mc.start);
            ((MyMessage) message).setExecuteWithCertficated(true);
            message.setAddressee(myAgent().findAssistant(Id.processLunchPauseInspection));
            if (((MyMessage) message).getCustomer() == null) {
                ((MyMessage) message).setCustomer(new CustomerObject());
            }
            ((MyMessage) message).getCustomer().setCount(myAgent().getPauseCounter());

            startContinualAssistant(message);
            addPauseToEmployerGui(true, ((MyMessage) message).getProcessEndTime());

        }
    }

    public void addPauseToEmployerGui(boolean c2, double endTime) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            int start = 0;
            if (c2) {
                start = myAgent().getTotalCountOfEmployeesWithCertificate1();
            }
            for (int i = start; i < myAgent().getTotalCountOfEmployees(); i++) {
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
            removeAnimFromPause(index);
        }
    }

    private void processFinishProcessLunchPause(MessageForm message) {
        if (((MyMessage) message).isExecuteWithCertficated()) {
            myAgent().removePausedEmployeesC2();
            ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());

        } else {
            myAgent().removePausedEmployeesC1();
            ((MyMessage) message).setCertificate2(0);

        }

        removePauseFromEmployer();
        ((MyMessage) message).setAvailableEmployee(true);
        message.setAddressee(Id.agentVehicleInspection);
        message.setCode(Mc.noticeFreeMechanic);
        notice(message);
    }

    private void processNoticeTruckInspection(MessageForm message) {
        for (int i = 0; i < myAgent().getTotalCountOfEmployees() - myAgent().getCountOfWorkingC1() - myAgent().getCountOfWorkingC2(); i++) {
            MessageForm newMessage = message.createCopy();
            ((MyMessage) newMessage).setAvailableEmployee(true);
            ((MyMessage) newMessage).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2() - i);
            newMessage.setAddressee(Id.agentVehicleInspection);
            newMessage.setCode(Mc.noticeFreeMechanic);
            notice(newMessage);
        }

    }

    private void addAnimToPause(int count, double endTime) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);
            a.setStartTime(mySim().currentTime());
            a.setEndTime(mySim().currentTime() + endTime);
            a.setType(ActivityType.ADD_PAUSE_TO_EMPLOYER_TYPE_2);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private void removeAnimFromPause(int count) {
        if (((MySimulation) mySim()).getAnimator() != null) {
            EmployeeAnimActivity a = new EmployeeAnimActivity();
            a.setCount(count);

            a.setType(ActivityType.REMOVE_PAUSE_FROM_EMPLOYER_TYPE_2);
            ((MySimulation) mySim()).getAnimator().addAnimActivity(a);
        }
    }

    private void processFinishProcessMoveToInspection(MessageForm message) throws Exception {

        ((MyMessage) message).setProcessStartTime(mySim().currentTime());
        message.setAddressee(myAgent().findAssistant(Id.processInsepction));
        startContinualAssistant(message);
        // addToEmployer(((MyMessage) message).getCustomer(), ((MyMessage) message).isExecuteWithCertficated(), 0);

    }

    private void startMoveToInspection(MessageForm message) throws Exception {
        if (((MyMessage) message).getCustomer().isTruck()) {
            myAgent().addWorkingEmployeeC2();

            ((MyMessage) message).setExecuteWithCertficated(true);

        } else {
            if (myAgent().getCountOfWorkingC1() < myAgent().getTotalCountOfEmployeesWithCertificate1()) {
                myAgent().addWorkingEmployeeC1();
                ((MyMessage) message).setExecuteWithCertficated(false);
            } else {

                myAgent().addWorkingEmployeeC2();
                ((MyMessage) message).setExecuteWithCertficated(true);
            }
        }

        if (mySim().currentTime() != myAgent().getMessageTime()) {
            myAgent().setMessageTime(mySim().currentTime());
            MessageForm newMessage = message.createCopy();
            newMessage.setAddressee(Id.agentMechanics);
            newMessage.setCode(Mc.noticeTruckInspection);
            notice(newMessage);
        }

        ((MyMessage) message).setProcessStartTime(mySim().currentTime());
        message.setAddressee(myAgent().findAssistant(Id.processMoveToInspection));
        startContinualAssistant(message);
    }
}
