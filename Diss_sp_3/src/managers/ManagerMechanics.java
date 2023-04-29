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
    public void processMechanicExecute(MessageForm message) throws Exception {
        if (myAgent().getCountOfAllWorking() < myAgent().getTotalCountOfEmployees()) {

            

            if (((MyMessage) message).getCustomer().isTruck()) {
                myAgent().addWorkingEmployeeC2();
                //System.out.println("TYPE 2 BERE:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

                ((MyMessage) message).setExecuteWithCertficated(true);

                if (myAgent().getCountOfWorkingC1() < myAgent().getTotalCountOfEmployeesWithCertificate1()) {
                    for (int i = 0; i < myAgent().getTotalCountOfEmployeesWithCertificate1() - myAgent().getCountOfWorkingC1(); i++) {
                        MessageForm newMessage = message.createCopy();
                        ((MyMessage) newMessage).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2() - i);
                        newMessage.setAddressee(Id.agentVehicleInspection);
                        newMessage.setCode(Mc.noticeFreeMechanic);
                        notice(newMessage);
                    }
                }
            } else {
                if (myAgent().getCountOfWorkingC1() < myAgent().getTotalCountOfEmployeesWithCertificate1()) {
                    //System.out.println("TYPE 1 BERE:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());
                    myAgent().addWorkingEmployeeC1();
                    ((MyMessage) message).setExecuteWithCertficated(false);
                } else {
                    //System.out.println("TYPE 2 BERE:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

                    myAgent().addWorkingEmployeeC2();
                    ((MyMessage) message).setExecuteWithCertficated(true);
                }
            }

            addToEmployer(((MyMessage) message).getCustomer(), ((MyMessage) message).isExecuteWithCertficated());
            
            ((MyMessage) message).setProcessStartTime(mySim().currentTime());
            message.setAddressee(myAgent().findAssistant(Id.processInsepction));
            startContinualAssistant(message);
            //ystem.out.println("Start service: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());
            String typeOfCar = ((MyMessage) message).getCustomer().isTruck() ? "TRUCK" : ((MyMessage) message).getCustomer().getProbabilityVehicle() < 0.65 ? "PERSONAL" : "VAN";          //  System.out.println(((MyMessage) message).getCustomer().getCount() + "| Customer Start Service | " + typeOfCar + "| " + mySim().currentTime());
            if (((MyMessage) message).isExecuteWithCertficated()) {
                System.out.println(((MyMessage) message).getCustomer().getCount()
                        + "|" + typeOfCar + "| Start | W:TYPE_2_C2"
                        + " | " + mySim().currentTime());
            } else {
                System.out.println(((MyMessage) message).getCustomer().getCount()
                        + "|" + typeOfCar + "| Start | W:TYPE_2_C1"
                        + " | " + mySim().currentTime());
            }

        }
    }

    //meta! sender="ProcessInsepction", id="28", type="Finish"
    public void processFinish(MessageForm message) throws Exception {

        if (((MyMessage) message).getCustomer().getCount() == 13) {
            System.out.println("");
        }

        String typeOfCar = ((MyMessage) message).getCustomer().isTruck() ? "TRUCK" : ((MyMessage) message).getCustomer().getProbabilityVehicle() < 0.65 ? "PERSONAL" : "VAN";
        //  System.out.println(((MyMessage) message).getCustomer().getCount() + "| Customer Start Service | " + typeOfCar + "| " + mySim().currentTime());
        if (((MyMessage) message).isExecuteWithCertficated()) {
            System.out.println(((MyMessage) message).getCustomer().getCount()
                    + "|" + typeOfCar + "| Stop | W:TYPE_2_C2"
                    + " | " + mySim().currentTime());
        } else {
            System.out.println(((MyMessage) message).getCustomer().getCount()
                    + "|" + typeOfCar + "| Stop | W:TYPE_2_C1"
                    + " | " + mySim().currentTime());
        }

        removeFromEmployer(((MyMessage) message).getCustomer());
        if (((MyMessage) message).isExecuteWithCertficated()) {
            //System.out.println("TYPE 2 KONCI:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

            myAgent().removeWorkingEmployeeC2();
        } else {
            //System.out.println("TYPE 1 KONCI:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

            myAgent().removeWorkingEmployeeC1();
        }
        //myAgent().removeWorkingEmployee();
        //System.out.println("END service: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        boolean pause = false;
        //pause
        if (myAgent().isPauseTimeStarted()) {
            if (((MyMessage) message).getProcessStartTime() < myAgent().getPauseTimeStartedTime()) {
                if (((MyMessage) message).isExecuteWithCertficated()) {
                    myAgent().addEmployeeToPauseC2();
                } else {
                    myAgent().addEmployeeToPauseC1();
                }
                addPauseToEmployerGui(((MyMessage) message).isExecuteWithCertficated());
                MyMessage newMessage = (MyMessage) message.createCopy();
                newMessage.setExecuteWithCertficated(((MyMessage) message).isExecuteWithCertficated());
                newMessage.setAddressee(myAgent().findAssistant(Id.processLunchPauseInspection));
                startContinualAssistant(newMessage);
                pause = true;
            }

        }

        if (!pause) {
            ((MyMessage) message).setAvailableEmployee(true);
            ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());
            message.setCode(Mc.mechanicExecute);
            response(message);
        } else {
            ((MyMessage) message).setAvailableEmployee(false);
            ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());
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
        if (myAgent().getCountOfAllWorking() < myAgent().getTotalCountOfEmployees()) {
            ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());
            ((MyMessage) message).setAvailableEmployee(true);
        } else {
            ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());
            ((MyMessage) message).setAvailableEmployee(false);
        }
        response(message);
    }

    public void addToEmployer(CustomerObject customer, boolean c2) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            int start = 0;
            if(c2){
                start = myAgent().getTotalCountOfEmployeesWithCertificate1();
            }
            for (int i = start; i < myAgent().getTotalCountOfEmployees(); i++) {
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
            startContinualAssistant(message);
            addPauseToEmployerGui(false);
            //System.out.println("TYPE 1 BERE:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        }

        for (int i = 0; i < myAgent().getCountOfPausedCertificate2(); i++) {
            message = message.createCopy();
            message.setCode(Mc.start);
            ((MyMessage) message).setExecuteWithCertficated(true);
            message.setAddressee(myAgent().findAssistant(Id.processLunchPauseInspection));
            startContinualAssistant(message);
            addPauseToEmployerGui(true);
            //System.out.println("TYPE 2 BERE:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        }
    }

    public void addPauseToEmployerGui(boolean c2) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            int start = 0;
            if(c2){
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
        }
    }

    private void processFinishProcessLunchPause(MessageForm message) {
        //System.out.println("stop pauza 2");
        if (((MyMessage) message).isExecuteWithCertficated()) {
            myAgent().removePausedEmployeesC2();
            //System.out.println("TYPE 1 KONCI:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        } else {
            //System.out.println("TYPE 2 KONCI:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

            myAgent().removePausedEmployeesC1();
        }

        removePauseFromEmployer();
        ((MyMessage) message).setCertificate2(myAgent().getTotalCountOfEmployeesWithCertificate2() - myAgent().getCountOfWorkingWithCertificate2());
        message.setAddressee(Id.agentVehicleInspection);
        message.setCode(Mc.noticeFreeMechanic);
        notice(message);
    }
}
