package agents;

import OSPABA.*;
import continualAssistants.ProcessLeaveFromPayment;
import continualAssistants.ProcessMoveFromInspection;
import continualAssistants.ProcessMoveToInspection;
import simulation.*;
import managers.*;

//meta! id="6"
public class AgentVehicleInspection extends Agent {

    public AgentVehicleInspection(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerVehicleInspection(Id.managerVehicleInspection, mySim(), this);
        new ProcessMoveFromInspection(Id.processMoveFromInspection, mySim(), this);
        new ProcessLeaveFromPayment(Id.processLeaveFromPayment, mySim(), this);

        addOwnMessage(Mc.mechanicExecute);
        addOwnMessage(Mc.customerService);
        addOwnMessage(Mc.parkingPlaceInfo);
        addOwnMessage(Mc.checkParkingPlace);
        addOwnMessage(Mc.paymentExecute);
        addOwnMessage(Mc.receptionExecute);
        addOwnMessage(Mc.leaveParking);
        addOwnMessage(Mc.parkingPlaceInfoMechanics);
        addOwnMessage(Mc.mechanicsAvailability);
        addOwnMessage(Mc.mechanicsAvailability);
        addOwnMessage(Mc.noticeLunchPause);
        addOwnMessage(Mc.noticeFreeMechanic);
        addOwnMessage(Mc.noticeEndMoveFromInspection);
        addOwnMessage(Mc.noticeEndMoveLeaveSystem);
       

    }
    //meta! tag="end"
}
