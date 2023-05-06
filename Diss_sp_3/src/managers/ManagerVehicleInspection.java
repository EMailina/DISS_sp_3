package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//meta! id="6"
public class ManagerVehicleInspection extends Manager {

    public ManagerVehicleInspection(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentMechanics", id="44", type="Response"
    public void processMechanicExecute(MessageForm message) {
        if (((MySimulation) mySim()).getAnimator() == null) {
            message.setCode(Mc.paymentExecute);
            message.setAddressee(mySim().findAgent(Id.agentReception));
            request(message);
            //find another vehicle
            MessageForm newMessage = message.createCopy();

            newMessage.setCode(Mc.noticeFreeMechanic);

            newMessage.setAddressee(mySim().findAgent(Id.agentParking));
            notice(newMessage);
        } else {
            message.setCode(Mc.start);
            message.setAddressee(myAgent().findAssistant(Id.processMoveFromInspection));
            startContinualAssistant(message);

        }
    }

    //meta! sender="AgentModel", id="33", type="Request"
    public void processCustomerService(MessageForm message) {
        message.setCode(Mc.receptionExecute);
        message.setAddressee(mySim().findAgent(Id.agentReception));
        request(message);
    }

    //meta! sender="AgentReception", id="62", type="Request"
    public void processCheckParkingPlace(MessageForm message) {

        message.setCode(Mc.parkingPlaceInfo);
        message.setAddressee(mySim().findAgent(Id.agentParking));
        request(message);
    }

    //meta! sender="AgentReception", id="53", type="Response"
    public void processPaymentExecute(MessageForm message) {
        if (((MySimulation) mySim()).getAnimator() == null) {
            message.setCode(Mc.customerService);
            message.setAddressee(Id.agentModel);
            notice(message);
        } else {
            message.setCode(Mc.start);
            message.setAddressee(myAgent().findAssistant(Id.processLeaveFromPayment));
            notice(message);
        }
    }

    //meta! sender="AgentReception", id="36", type="Response"
    public void processReceptionExecute(MessageForm message) {

        message.setCode(Mc.noticeParkingVehicle);
        message.setAddressee(mySim().findAgent(Id.agentParking));
        request(message);
    }

    //meta! sender="AgentParking", id="60", type="Notice"
    public void processNoticeFreeParking(MessageForm message) {

    }

    //meta! sender="AgentParking", id="39", type="Notice"
    public void processNoticeLeaveParking(MessageForm message) {
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
                    case Id.processMoveFromInspection: {
                        try {
                            processNoticeEndMoveFromInspection(message);
                        } catch (Exception ex) {
                            Logger.getLogger(ManagerMechanics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                    case Id.processLeaveFromPayment: {
                        try {
                            processNoticeEndLeavePayment(message);
                        } catch (Exception ex) {
                            Logger.getLogger(ManagerMechanics.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
                break;

            case Mc.paymentExecute:
                processPaymentExecute(message);
                break;

            case Mc.receptionExecute:
                processReceptionExecute(message);
                break;

            case Mc.mechanicExecute:
                processMechanicExecute(message);
                break;

            case Mc.checkParkingPlace:
                processCheckParkingPlace(message);
                break;

            case Mc.customerService:
                processCustomerService(message);
                break;

            case Mc.parkingPlaceInfo:
                processParkingPlaceInfo(message);
                break;

            case Mc.leaveParking:
                processLeaveParking(message);
                break;

            case Mc.parkingPlaceInfoMechanics:
                processParkingPlaceInfoMechanics(message);
                break;

            case Mc.mechanicsAvailability:
                processMechanicsAvailability(message);
                break;

            case Mc.noticeLunchPause:
                processNoticeLunchPause(message);
                break;

            case Mc.noticeFreeMechanic:
                processNoticeFreeMechanic(message);
                break;

            case Mc.noticeEndMoveFromInspection:
                processNoticeEndMoveFromInspection(message);
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

    private void processParkingPlaceInfo(MessageForm message) {
        message.setCode(Mc.checkParkingPlace);
        response(message);
    }

    private void processLeaveParking(MessageForm message) {
        //plan mechanic
        message.setCode(Mc.mechanicExecute);
        message.setAddressee(mySim().findAgent(Id.agentMechanics));
        request(message);

        //inform about new place
        MessageForm newMessage = message.createCopy();
        newMessage.setCode(Mc.noticeFreeParking);
        newMessage.setAddressee(mySim().findAgent(Id.agentReception));
        notice(newMessage);
    }

    private void processParkingPlaceInfoMechanics(MessageForm message) {
        message.setCode(Mc.mechanicsAvailability);
        message.setAddressee(mySim().findAgent(Id.agentMechanics));
        request(message);
    }

    private void processMechanicsAvailability(MessageForm message) {
        message.setCode(Mc.parkingPlaceInfoMechanics);
        response(message);
    }

    private void processNoticeLunchPause(MessageForm message) {
        message.setCode(Mc.noticeLunchPause);
        message.setAddressee(mySim().findAgent(Id.agentMechanics));
        notice(message);

        MessageForm newMessage = message.createCopy();
        newMessage.setAddressee(mySim().findAgent(Id.agentReception));
        notice(newMessage);
    }

    private void processNoticeFreeMechanic(MessageForm message) {
        message.setCode(Mc.noticeFreeMechanic);
        ((MyMessage) message).setAvailableEmployee(true);
        message.setAddressee(mySim().findAgent(Id.agentParking));
        notice(message);
    }

    private void processNoticeEndMoveFromInspection(MessageForm message) {
        message.setCode(Mc.paymentExecute);
        message.setAddressee(mySim().findAgent(Id.agentReception));
        request(message);
        //find another vehicle
        MessageForm newMessage = message.createCopy();

        newMessage.setCode(Mc.noticeFreeMechanic);

        newMessage.setAddressee(mySim().findAgent(Id.agentParking));
        notice(newMessage);
    }

    private void processNoticeEndLeavePayment(MessageForm message) {
        message.setCode(Mc.customerService);
        message.setAddressee(Id.agentModel);
        notice(message);
    }

}
