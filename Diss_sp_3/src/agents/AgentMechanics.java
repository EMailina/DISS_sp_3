package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="4"
public class AgentMechanics extends Agent
{
	public AgentMechanics(int id, Simulation mySim, Agent parent)
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
		new ManagerMechanics(Id.managerMechanics, mySim(), this);
		new ProcessInsepction(Id.processInsepction, mySim(), this);
		addOwnMessage(Mc.mechanicExecute);
		addOwnMessage(Mc.init);
	}
	//meta! tag="end"
}
