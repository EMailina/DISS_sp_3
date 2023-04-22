package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="3"
public class AgentReception extends Agent
{
	public AgentReception(int id, Simulation mySim, Agent parent)
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
		new ManagerReception(Id.managerReception, mySim(), this);
		new ProcessTakeOverVehicle(Id.processTakeOverVehicle, mySim(), this);
		new ProcessPaying(Id.processPaying, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.checkParkingPlace);
		addOwnMessage(Mc.paymentExecute);
		addOwnMessage(Mc.receptionExecute);
	}
	//meta! tag="end"
}
