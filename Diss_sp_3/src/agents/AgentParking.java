package agents;

import OSPABA.*;
import OSPDataStruct.SimQueue;
import OSPStat.WStat;
import simulation.*;
import managers.*;
import continualAssistants.*;
import diss_sp_3.RunType;
import java.util.ArrayList;
import objects.CustomerObject;

//meta! id="5"
public class AgentParking extends Agent {

    private double chanceCarAndVan = 0.86;
    private int totalCountOfParkingPlaces = 5;
    private SimQueue<MessageForm> queue;
    private ArrayList<CustomerObject> parkingPlaces;

    public AgentParking(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
        queue = new SimQueue<>(new WStat(_mySim));
        parkingPlaces = new ArrayList<>();
        for (int i = 0; i < totalCountOfParkingPlaces; i++) {
            parkingPlaces.add(null);
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerParking(Id.managerParking, mySim(), this);
        addOwnMessage(Mc.parkingPlaceInfo);
        addOwnMessage(Mc.parkingPlaceInfoMechanics);
        addOwnMessage(Mc.leaveParking);
        addOwnMessage(Mc.noticeParkingVehicle);
        addOwnMessage(Mc.noticeFreeMechanic);
    }
    //meta! tag="end"

    public int getTotalCountOfParkingPlaces() {
        return totalCountOfParkingPlaces;
    }

    public SimQueue<MessageForm> getQueue() {
        return queue;
    }

    public long getCOuntOfVehicles() {
        return queue.size();
    }

    public ArrayList<CustomerObject> getParkingPlaces() {
        return parkingPlaces;
    }

    public void setParkingPlaces(ArrayList<CustomerObject> parkingPlaces) {
        this.parkingPlaces = parkingPlaces;
    }

    public double getChanceCarAndVan() {
        return chanceCarAndVan;
    }


   
}
