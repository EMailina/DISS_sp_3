package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="3"
public class ManagerReception extends Manager
{
	public ManagerReception(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentVehicleInspection", id="50", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentVehicleInspection", id="62", type="Response"
	public void processCheckParkingPlace(MessageForm message)
	{
	}

	//meta! sender="AgentVehicleInspection", id="53", type="Request"
	public void processPaymentExecute(MessageForm message)
	{
	}

	//meta! sender="ProcessTakeOverVehicle", id="20", type="Finish"
	public void processFinishProcessTakeOverVehicle(MessageForm message)
	{
	}

	//meta! sender="ProcessPaying", id="22", type="Finish"
	public void processFinishProcessPaying(MessageForm message)
	{
	}

	//meta! sender="AgentVehicleInspection", id="36", type="Request"
	public void processReceptionExecute(MessageForm message)
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
		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.processTakeOverVehicle:
				processFinishProcessTakeOverVehicle(message);
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

		case Mc.init:
			processInit(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentReception myAgent()
	{
		return (AgentReception)super.myAgent();
	}

}
