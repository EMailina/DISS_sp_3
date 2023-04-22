package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="5"
public class ManagerParking extends Manager
{
	public ManagerParking(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentVehicleInspection", id="51", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentVehicleInspection", id="59", type="Notice"
	public void processNoticeIsFreeParking(MessageForm message)
	{
	}

	//meta! sender="AgentVehicleInspection", id="38", type="Notice"
	public void processNoticeParkVehicle(MessageForm message)
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
		case Mc.init:
			processInit(message);
		break;

		case Mc.noticeParkVehicle:
			processNoticeParkVehicle(message);
		break;

		case Mc.noticeIsFreeParking:
			processNoticeIsFreeParking(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentParking myAgent()
	{
		return (AgentParking)super.myAgent();
	}

}
