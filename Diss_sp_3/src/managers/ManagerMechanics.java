package managers;

import OSPABA.*;
import simulation.*;
import agents.*;
import continualAssistants.*;
import diss_sp_3.RunType;
import objects.CustomerObject;

//meta! id="4"
public class ManagerMechanics extends Manager {

    public ManagerMechanics(int id, Simulation mySim, Agent myAgent) {
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

    //meta! sender="AgentVehicleInspection", id="44", type="Request"
    public void processMechanicExecute(MessageForm message) {
        if (myAgent().getCountOfWorking() < myAgent().getTotalCountOfEmployees()) {
            addToEmployer(((MyMessage) message).getCustomer());
            myAgent().addWorkingEmployee();
            message.setAddressee(myAgent().findAssistant(Id.processInsepction));
            startContinualAssistant(message);
            System.out.println("Start service: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        }
    }

    //meta! sender="ProcessInsepction", id="28", type="Finish"
    public void processFinish(MessageForm message) {
        removeFromEmployer(((MyMessage) message).getCustomer());
        myAgent().removeWorkingEmployee();
        System.out.println("END service: " + ((MyMessage) message).getCustomer().getCount() + " " + mySim().currentTime());

        message.setCode(Mc.mechanicExecute);
        response(message);
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        if (message.code() == Mc.customerService) {
            System.out.println("mech");
        }
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.mechanicExecute:
                processMechanicExecute(message);
                break;

            case Mc.finish:
                processFinish(message);
                break;

            case Mc.mechanicsAvailability:
                processMechanicsAvailability(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public AgentMechanics myAgent() {
        return (AgentMechanics) super.myAgent();
    }

    private void processMechanicsAvailability(MessageForm message) {
        if (myAgent().getCountOfWorking() < myAgent().getTotalCountOfEmployees()) {
            ((MyMessage) message).setAvailableEmployee(true);
        } else {
            ((MyMessage) message).setAvailableEmployee(false);
        }
        response(message);
    }

    public void addToEmployer(CustomerObject customer) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            for (int i = 0; i < myAgent().getTotalCountOfEmployees(); i++) {
                if (myAgent().getGuiEmployers().get(i) == null) {

                    myAgent().getGuiEmployers().set(i, customer);
                    customer.setInspectionRewrite(true);
//                    if (takeOver) {
//                        customer.setTakeOverRewrite(true);
//                    } else {
//                        customer.setPaymentRewrite(true);
//                    }
                    break;
                }
            }
        }
    }

    private void removeFromEmployer(CustomerObject customer) {

        if (((MySimulation) mySim()).getType() == RunType.SIMULATION) {
            for (int i = 0; i < myAgent().getTotalCountOfEmployees(); i++) {
                if (customer.equals(myAgent().getGuiEmployers().get(i))) {
                    myAgent().getGuiEmployers().set(i, null);
                    break;
                }
            }
        }
    }
}
