package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="4"
public class ManagerMechanics extends Manager
{
	public ManagerMechanics(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
	}

	//meta! sender="AgentVehicleInspection", id="44", type="Request"
	public void processMechanicExecute(MessageForm message)
	{
	}

	//meta! sender="AgentVehicleInspection", id="52", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="ProcessInsepction", id="28", type="Finish"
	public void processFinish(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.mechanicExecute:
			processMechanicExecute(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentMechanics myAgent()
	{
		return (AgentMechanics)super.myAgent();
	}

}
