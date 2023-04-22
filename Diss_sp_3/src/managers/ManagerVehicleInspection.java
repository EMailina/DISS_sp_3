package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;

//meta! id="6"
public class ManagerVehicleInspection extends Manager
{
	public ManagerVehicleInspection(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="AgentMechanics", id="44", type="Response"
	public void processMechanicExecute(MessageForm message)
	{
	}

	//meta! sender="AgentModel", id="33", type="Request"
	public void processCustomerService(MessageForm message)
	{
	}

	//meta! sender="AgentModel", id="48", type="Notice"
	public void processInit(MessageForm message)
	{
	}

	//meta! sender="AgentReception", id="62", type="Request"
	public void processCheckParkingPlace(MessageForm message)
	{
	}

	//meta! sender="AgentReception", id="53", type="Response"
	public void processPaymentExecute(MessageForm message)
	{
	}

	//meta! sender="AgentReception", id="36", type="Response"
	public void processReceptionExecute(MessageForm message)
	{
	}

	//meta! sender="AgentParking", id="60", type="Notice"
	public void processNoticeFreeParking(MessageForm message)
	{
	}

	//meta! sender="AgentParking", id="39", type="Notice"
	public void processNoticeLeaveParking(MessageForm message)
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
		case Mc.paymentExecute:
			processPaymentExecute(message);
		break;

		case Mc.receptionExecute:
			processReceptionExecute(message);
		break;

		case Mc.mechanicExecute:
			processMechanicExecute(message);
		break;

		case Mc.checkParkingPlace:
			processCheckParkingPlace(message);
		break;

		case Mc.customerService:
			processCustomerService(message);
		break;

		case Mc.noticeFreeParking:
			processNoticeFreeParking(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.noticeLeaveParking:
			processNoticeLeaveParking(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AgentVehicleInspection myAgent()
	{
		return (AgentVehicleInspection)super.myAgent();
	}

}
