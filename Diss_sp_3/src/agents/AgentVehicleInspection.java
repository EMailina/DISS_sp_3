package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="6"
public class AgentVehicleInspection extends Agent
{
	public AgentVehicleInspection(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ManagerVehicleInspection(Id.managerVehicleInspection, mySim(), this);
		addOwnMessage(Mc.mechanicExecute);
		addOwnMessage(Mc.customerService);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.checkParkingPlace);
		addOwnMessage(Mc.paymentExecute);
		addOwnMessage(Mc.receptionExecute);
		addOwnMessage(Mc.noticeFreeParking);
		addOwnMessage(Mc.noticeLeaveParking);
	}
	//meta! tag="end"
}
