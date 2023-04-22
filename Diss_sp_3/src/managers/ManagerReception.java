package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

                MyMessage newMessage = (MyMessage) myAgent().getQueueTakeOver().dequeue();
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
            myAgent().removeWorkingEmployee();
            myAgent().removeReservedParkingPlace();

            //payment
            if (myAgent().getQueuePaying().size() > 0) {
                MyMessage nextMessage = (MyMessage) myAgent().getQueuePaying().dequeue();
                //nextMessage.setTotalWaitingTime(mySim().currentTime() - nextMessage.getStartWaitingTime());
                startWorkOnPayment(nextMessage);
            }

            message.setCode(Mc.receptionExecute);
            response(message);

            //takeover
            if (myAgent().getQueueTakeOver().size() > 0 && myAgent().getCountOfReservedParkingPlaces() < 5) {
                MyMessage newMessage = (MyMessage) myAgent().getQueueTakeOver().get(0);

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
        System.out.println("Start registration: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

    }

    private void startWorkOnPayment(MyMessage message) throws Exception {
        System.out.println("Payment: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        myAgent().addWorkingEmployee();
        message.setAddressee(myAgent().findAssistant(Id.processPaying));
        startContinualAssistant(message);
    }

    //meta! sender="ProcessPaying", id="22", type="Finish"
    public void processFinishProcessPaying(MessageForm message) {
        try {
            myAgent().removeWorkingEmployee();

            //payment
            if (myAgent().getQueuePaying().size() > 0) {
                MyMessage nextMessage = (MyMessage) myAgent().getQueuePaying().dequeue();

                //nextMessage.getCustomer().setmySim().currentTime() - nextMessage.getCustomer().getStartOfWaitingForTakeOver());
                startWorkOnPayment(nextMessage);
            }

            //takeover
            if (myAgent().getQueueTakeOver().size() > 0 && myAgent().getCountOfReservedParkingPlaces() < 5) {
                MyMessage newMessage = (MyMessage) myAgent().getQueueTakeOver().get(0);
                if (((MyMessage) newMessage).getCustomer().getCount() == 7) {
                    System.out.println("xxxxx7");
                }
                newMessage.setCode(Mc.checkParkingPlace);
                newMessage.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
                request(newMessage);
            }

            message.setCode(Mc.paymentExecute);
            response(message);
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
                //System.out.println("v rade:" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());
            } else {
                //startWorkOnTakeOver((MyMessage) message);
                MyMessage newMessage = (MyMessage) message.createCopy();
                //System.out.println("" + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());
                //((MyMessage) newMessage).setCustomer(((MyMessage) message).getCustomer());
                newMessage.setCode(Mc.checkParkingPlace);
                newMessage.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
                request(newMessage);
                myAgent().getQueueTakeOver().enqueue(message);
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
            if (myAgent().getCountOfWorkingEmployees() < myAgent().getTotalCountOfEmployees()
                    || myAgent().getCountOfReservedParkingPlaces() /*+ ((MyMessage) message).getCountOfParkingPlaces() */ >= myAgent().getTotalCountOfParkingPlaces()) {
                //((MyMessage) message).getCustomer().setStartOfWaitingForTakeOver(mySim().currentTime());
                MyMessage mess = (MyMessage) myAgent().getQueueTakeOver().dequeue();
                startWorkOnTakeOver((MyMessage) mess);
            }
        } catch (Exception ex) {
            Logger.getLogger(ManagerReception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
