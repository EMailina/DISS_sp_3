package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import diss_sp_3.RunType;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.CustomerObject;

//meta! id="3"
public class ManagerReception extends Manager {

    public ManagerReception(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentVehicleInspection", id="50", type="Notice"
    public void processInit(MessageForm message) {
    }

    //meta! sender="AgentVehicleInspection", id="62", type="Response"
    public void processCheckParkingPlace(MessageForm message) {
        try {
            if (myAgent().getCountOfWorkingEmployees() >= myAgent().getTotalCountOfEmployees()
                    || myAgent().getCountOfReservedParkingPlaces() + ((MyMessage) message).getCountOfParkingPlaces() >= myAgent().getTotalCountOfParkingPlaces()) {
                // ((MyMessage) message).getCustomer().setStartOfWaitingForTakeOver(mySim().currentTime());
                //myAgent().getQueueTakeOver().enqueue(message);

            } else {
               // System.out.println("AFTER CHECK TIME: " + mySim().currentTime());
                MyMessage newMessage = (MyMessage) myAgent().getQueueTakeOver().dequeue();
                myAgent().getQueueTakeOverGui().poll();
                startWorkOnTakeOver((MyMessage) newMessage);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //meta! sender="AgentVehicleInspection", id="53", type="Request"
    public void processPaymentExecute(MessageForm message) {
        try {
            if (myAgent().getCountOfWorkingEmployees() >= myAgent().getTotalCountOfEmployees()) {
                //((MyMessage) message).getCustomer().setStartWaitingTime(mySim().currentTime());
                myAgent().getQueuePaying().enqueue(message);
                myAgent().getQueuePayingGui().add(message);
                ((MyMessage) message).getCustomer().setWaitingForPayment(true);

            } else {
                startWorkOnPayment((MyMessage) message);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //meta! sender="ProcessTakeOverVehicle", id="20", type="Finish"
    public void processFinishProcessTakeOverVehicle(MessageForm message) {
        try {
            removeFromEmployer(((MyMessage) message).getCustomer());
            myAgent().removeWorkingEmployee();
            myAgent().removeReservedParkingPlace();

            boolean pause = false;
            //pause
            if (myAgent().isPauseTimeStarted()) {

                if (((MyMessage) message).getProcessStartTime() < myAgent().getPauseTimeStartedTime()) {
                    addPauseToEmployerGui();

                    myAgent().addEmployeeToPause();
                    message.setAddressee(myAgent().findAssistant(Id.processLunchPauseReception));
                    startContinualAssistant(message);
                    pause = true;

                }

            }

            //payment
            if (myAgent().getQueuePaying().size() > 0 && !pause) {
                MyMessage nextMessage = (MyMessage) myAgent().getQueuePaying().dequeue();
                myAgent().getQueuePayingGui().poll();
                //nextMessage.setTotalWaitingTime(mySim().currentTime() - nextMessage.getStartWaitingTime());
                startWorkOnPayment(nextMessage);
            }

            MyMessage nextMessage = (MyMessage) message.createCopy();
            nextMessage.setCode(Mc.receptionExecute);
            response(nextMessage);

            //takeover
            if (myAgent().getQueueTakeOver().size() > 0 && myAgent().getCountOfReservedParkingPlaces() < 5 && !pause) {
                MyMessage newMessage = (MyMessage) myAgent().getQueueTakeOver().peek();

                newMessage.setCode(Mc.checkParkingPlace);
                newMessage.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
                request(newMessage);
            }

        } catch (Exception ex) {
            Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startWorkOnTakeOver(MyMessage message) throws Exception {
        myAgent().addWorkingEmployee();
        myAgent().addReservedParkingPlace();
        ((MyMessage) message).getCustomer().setEndOfWaitingForTakeOver(mySim().currentTime());
        myAgent().getStatWaitingTime().addSample(mySim().currentTime() - ((MyMessage) message).getCustomer().getStartOfWaitingForTakeOver());
        message.setAddressee(myAgent().findAssistant(Id.processTakeOverVehicle));
        startContinualAssistant(message);
         //System.out.println("Start registration: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());
        addToEmployer(((MyMessage) message).getCustomer(), true);
    }

    private void startWorkOnPayment(MyMessage message) throws Exception {
         //System.out.println("Payment: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        myAgent().addWorkingEmployee();
        message.setAddressee(myAgent().findAssistant(Id.processPaying));
        startContinualAssistant(message);
        addToEmployer(((MyMessage) message).getCustomer(), false);

    }

    //meta! sender="ProcessPaying", id="22", type="Finish"
    public void processFinishProcessPaying(MessageForm message) {
        try {
            removeFromEmployer(((MyMessage) message).getCustomer());
            myAgent().removeWorkingEmployee();

            boolean pause = false;
            //pause
            if (myAgent().isPauseTimeStarted()) {
                if (((MyMessage) message).getProcessStartTime() < myAgent().getPauseTimeStartedTime()) {
                    addPauseToEmployerGui();

                    myAgent().addEmployeeToPause();
                    message.setAddressee(myAgent().findAssistant(Id.processLunchPauseReception));
                    startContinualAssistant(message);
                    pause = true;
                }

            }
            //payment
            if (myAgent().getQueuePaying().size() > 0 && !pause) {
                MyMessage nextMessage = (MyMessage) myAgent().getQueuePaying().dequeue();
                myAgent().getQueuePayingGui().poll();
                //nextMessage.getCustomer().setmySim().currentTime() - nextMessage.getCustomer().getStartOfWaitingForTakeOver());
                startWorkOnPayment(nextMessage);
            }
            //takeover
            if (myAgent().getQueueTakeOver().size() > 0 && myAgent().getCountOfReservedParkingPlaces() < 5 && !pause) {
                MyMessage newMessage = (MyMessage) myAgent().getQueueTakeOver().peek();

                newMessage.setCode(Mc.checkParkingPlace);
                newMessage.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
                request(newMessage);
            }

            MyMessage newMessage = (MyMessage) message.createCopy();
            newMessage.setCode(Mc.paymentExecute);
            response(newMessage);
        } catch (Exception ex) {
            Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //meta! sender="AgentVehicleInspection", id="36", type="Request"
    public void processReceptionExecute(MessageForm message) {
        try {

            ((MyMessage) message).getCustomer().setStartOfWaitingForTakeOver(mySim().currentTime());
            if (myAgent().getCountOfWorkingEmployees() >= myAgent().getTotalCountOfEmployees()
                    || myAgent().getCountOfReservedParkingPlaces() >= myAgent().getTotalCountOfParkingPlaces()) {

                myAgent().getQueueTakeOver().enqueue(message);
                myAgent().getQueueTakeOverGui().add(message);

                //System.out.println("v rade:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());
            } else {
                //startWorkOnTakeOver((MyMessage) message);
                MyMessage newMessage = (MyMessage) message.createCopy();
                //System.out.println("BEFORE CHECK TIME: " + mySim().currentTime());
                //System.out.println("" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());
                //((MyMessage) newMessage).setCustomer(((MyMessage) message).getCustomer());
                newMessage.setCode(Mc.checkParkingPlace);
                newMessage.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
                request(newMessage);
                myAgent().getQueueTakeOver().enqueue(message);
                myAgent().getQueueTakeOverGui().add(message);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try {
//            if (myAgent().getCountOfWorkingEmployees() >= myAgent().getTotalCountOfEmployees()
//                    || myAgent().getCountOfReservedParkingPlaces() >= myAgent().getTotalCountOfParkingPlaces()) {
//                ((MyMessage) message).getCustomer().setStartOfWaitingForTakeOver(mySim().currentTime());
//                myAgent().getQueueTakeOver().enqueue(message);
//            } else {
//                startWorkOnTakeOver((MyMessage) message);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
//        }
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
            case Mc.finish:
                switch (message.sender().id()) {
                    case Id.processTakeOverVehicle: {
                        processFinishProcessTakeOverVehicle(message);
                    }
                    break;

                    case Id.processPaying:
                        processFinishProcessPaying(message);
                        break;

                    case Id.processLunchPauseReception:
                        processFinishProcessLunchPause(message);
                        break;

                }
                break;

            case Mc.paymentExecute:
                processPaymentExecute(message);
                break;

            case Mc.receptionExecute:
                processReceptionExecute(message);
                break;

            case Mc.checkParkingPlace:
                processCheckParkingPlace(message);
                break;

            case Mc.noticeFreeParking:
                processNoticeFreeParking(message);
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
    public AgentReception myAgent() {
        return (AgentReception) super.myAgent();
    }

    private void processNoticeFreeParking(MessageForm message) {
        try {
            if (myAgent().getQueueTakeOver().size() > 0 && myAgent().getCountOfWorkingEmployees() < myAgent().getTotalCountOfEmployees()
                    && myAgent().getCountOfReservedParkingPlaces() /*+ ((MyMessage) message).getCountOfParkingPlaces() */ < myAgent().getTotalCountOfParkingPlaces()) {
                //((MyMessage) message).getCustomer().setStartOfWaitingForTakeOver(mySim().currentTime());
                MyMessage mess = (MyMessage) myAgent().getQueueTakeOver().dequeue();
                myAgent().getQueueTakeOverGui().poll();
                startWorkOnTakeOver((MyMessage) mess);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addToEmployer(CustomerObject customer, boolean takeOver) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            for (int i = 0; i < myAgent().getTotalCountOfEmployees(); i++) {
                if (myAgent().getGuiEmployers().get(i) == null) {

                    myAgent().getGuiEmployers().set(i, customer);
                    if (takeOver) {
                        customer.setTakeOverRewrite(true);
                    } else {
                        customer.setPaymentRewrite(true);

                    }
                    break;
                }
            }
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

    private void processNoticeLunchPause(MessageForm message) {
       // System.out.println("pauza");
        myAgent().setPauseTimeStarted(true);
        myAgent().setPauseTimeStartedTime(mySim().currentTime());
        myAgent().addPausedEmployees();
        ((MyMessage) message).setProcessStartTime(mySim().currentTime());
        for (int i = 0; i < myAgent().getCountOfPaused(); i++) {
            message = message.createCopy();
            message.setCode(Mc.start);
            message.setAddressee(myAgent().findAssistant(Id.processLunchPauseReception));
            startContinualAssistant(message);
            addPauseToEmployerGui();
            
        }
    }

    private void processFinishProcessLunchPause(MessageForm message) {
     //  System.out.println("stop pauza");
        myAgent().removePausedEmployees();
        removePauseFromEmployer();
        //payment
        if (myAgent().getQueuePaying().size() > 0) {
            MyMessage nextMessage = (MyMessage) myAgent().getQueuePaying().dequeue();
            myAgent().getQueuePayingGui().poll();
            try {
                startWorkOnPayment(nextMessage);
            } catch (Exception ex) {
                Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
            }
        } //takeover
        else if (myAgent().getQueueTakeOver().size() > 0 && myAgent().getCountOfReservedParkingPlaces() < 5) {
            MyMessage newMessage = (MyMessage) myAgent().getQueueTakeOver().peek();
            MyMessage nextMessage = (MyMessage) newMessage.createCopy();
            nextMessage.setCode(Mc.checkParkingPlace);
            nextMessage.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
            request(nextMessage);
        }

    }
}
