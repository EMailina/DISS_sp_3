package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import diss_sp_3.RunType;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.CustomerObject;

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

        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
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

    private void processParkingPlaceInfo(MessageForm message) {
        //System.out.println("Posielam stav do recepcie: Q: " + myAgent().getQueue().size());
        ((MyMessage) message).setCountOfParkingPlaces(myAgent().getQueue().size());
        response(message);
    }

    private void processNoticeParkingVehicle(MessageForm message) {
        try {
            if (myAgent().getQueue().size() < myAgent().getTotalCountOfParkingPlaces()) {
                myAgent().getQueue().enqueue(message);
                //System.out.println("Added to parking: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime() + " Q: " + myAgent().getQueue().size());
                ((MyMessage) message).setCountOfParkingPlaces(myAgent().getQueue().size());

                message.setCode(Mc.parkingPlaceInfoMechanics);
                message.setAddressee(mySim().findAgent(Id.agentVehicleInspection));
                request(message);
                addToParkingPlace(((MyMessage) message).getCustomer());
            } else {
                throw new Exception("Parking places full!");
            }
        } catch (Exception ex) {
            Logger.getLogger(ManagerParking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processParkingPlaceInfoMechanics(MessageForm message) {
        if (((MyMessage) myAgent().getQueue().peek()).getCustomer().getCount() == 35) {
            System.out.println("");
        }
        boolean truck = ((MyMessage) myAgent().getQueue().peek()).getCustomer().isTruck();
        if ((truck && ((MyMessage) message).getCertificate2() > 0) || (!truck && ((MyMessage) message).isAvailableEmployee())) {
            MyMessage nextMessage = (MyMessage) myAgent().getQueue().dequeue();
            nextMessage.setCode(Mc.leaveParking);
            nextMessage.setAddressee(Id.agentVehicleInspection);
            ((MyMessage) message).setCountOfParkingPlaces(myAgent().getQueue().size());
            notice(nextMessage);
            //System.out.println("Leave parking: " + ((MyMessage) nextMessage).getCustomer().getCount() + " " + mySim().currentTime());
            removeFromParkingPlace(((MyMessage) nextMessage).getCustomer());
        }

    }

    private void processNoticeFreeMechanic(MessageForm message) {

        int cert2 = ((MyMessage) message).getCertificate2();

        if (myAgent().getQueue().size() > 0) {
            if (((MyMessage) myAgent().getQueue().peek()).getCustomer().getCount() == 35) {
                System.out.println("");
            }
            boolean truck = ((MyMessage) myAgent().getQueue().peek()).getCustomer().isTruck();

            if (((MyMessage) message).isAvailableEmployee() && canTakeVehicle(cert2, truck)) {
                MyMessage nextMessage = (MyMessage) myAgent().getQueue().dequeue();
                nextMessage.setCode(Mc.leaveParking);
                nextMessage.setAddressee(Id.agentVehicleInspection);
                ((MyMessage) message).setCountOfParkingPlaces(myAgent().getQueue().size());
                notice(nextMessage);
                // System.out.println("Leave parking: " + ((MyMessage) nextMessage).getCustomer().getCount() + " " + mySim().currentTime());
                removeFromParkingPlace(((MyMessage) nextMessage).getCustomer());
            }
        }
    }

    private void removeFromParkingPlace(CustomerObject customer) {
        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            for (int i = 0; i < myAgent().getTotalCountOfParkingPlaces(); i++) {
                if (customer.equals(myAgent().getParkingPlaces().get(i))) {
                    myAgent().getParkingPlaces().set(i, null);
                    break;
                }
            }
        }
    }

    public void addToParkingPlace(CustomerObject customer) {
        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            for (int i = 0; i < myAgent().getTotalCountOfParkingPlaces(); i++) {
                if (myAgent().getParkingPlaces().get(i) == null) {
                    myAgent().getParkingPlaces().set(i, customer);
                    customer.setParkingRewrite(true);
                    break;
                }
            }
        }
    }

    private boolean canTakeVehicle(int cert2, boolean truck) {
        if (cert2 > 0 && truck) {
            return true;
        } else if (cert2 <= 0 && truck) {
            return false;
        }
        return true;
    }

}
