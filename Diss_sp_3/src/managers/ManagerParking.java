package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//meta! id="5"
public class ManagerParking extends Manager {

    public ManagerParking(int id, Simulation mySim, Agent myAgent) {
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

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        if (message.code() == Mc.customerService) {
            System.out.println("park");
        }
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
//            case Mc.leaveParking:
//                processLeaveParking(message);
//                break;

            case Mc.noticeParkingVehicle:
                processNoticeParkingVehicle(message);
                break;

            case Mc.parkingPlaceInfo:
                processParkingPlaceInfo(message);
                break;
            case Mc.parkingPlaceInfoMechanics:
                processParkingPlaceInfoMechanics(message);
                break;

            case Mc.noticeFreeMechanic:
                processNoticeFreeMechanic(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentParking myAgent() {
        return (AgentParking) super.myAgent();
    }

//    private void processLeaveParking(MessageForm message) {
//        MyMessage nextMessage = (MyMessage) myAgent().getQueue().dequeue();
//        ((MyMessage) message).setCustomer(nextMessage.getCustomer());
//
//        notice(message);
//    }
    private void processParkingPlaceInfo(MessageForm message) {
        System.out.println("Posielam stav do recepcie: Q: " + myAgent().getQueue().size());
        ((MyMessage) message).setCountOfParkingPlaces(myAgent().getQueue().size());
        response(message);
    }

    private void processNoticeParkingVehicle(MessageForm message) {
        try {
            if (myAgent().getQueue().size() < myAgent().getTotalCountOfParkingPlaces()) {
                myAgent().getQueue().enqueue(message);
                System.out.println("Added to parking: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime() + " Q: " + myAgent().getQueue().size());

                message.setCode(Mc.parkingPlaceInfoMechanics);
                message.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
                request(message);
            } else {
                throw new Exception("Parking places full!");
            }
        } catch (Exception ex) {
            Logger.getLogger(ManagerParking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processParkingPlaceInfoMechanics(MessageForm message) {
        if (((MyMessage) message).isAvailableEmployee()) {
            MyMessage nextMessage = (MyMessage) myAgent().getQueue().dequeue();
            nextMessage.setCode(Mc.leaveParking);
            nextMessage.setAddressee(Id.agentVehicleInspection);
            nextMessage.setCountOfParkingPlaces(myAgent().getQueue().size());
            notice(nextMessage);
            System.out.println("Leave parking: " + ((MyMessage) nextMessage).getCustomer().getCount() + " " + mySim().currentTime());

        }

    }

    private void processNoticeFreeMechanic(MessageForm message) {
        if (myAgent().getQueue().size() > 0) {
            MyMessage nextMessage = (MyMessage) myAgent().getQueue().dequeue();
            nextMessage.setCode(Mc.leaveParking);
            nextMessage.setAddressee(Id.agentVehicleInspection);
            nextMessage.setCountOfParkingPlaces(myAgent().getQueue().size());
            notice(nextMessage);
            System.out.println("Leave parking: " + ((MyMessage) nextMessage).getCustomer().getCount() + " " + mySim().currentTime());

        }
    }

}
