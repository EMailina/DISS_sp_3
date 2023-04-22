package agents;

import OSPABA.*;
import simulation.*;
import managers.*;
import continualAssistants.*;

//meta! id="4"
public class AgentMechanics extends Agent {

    private int totalCountOfEmployees = 1;
    private int countOfWorking;

    public AgentMechanics(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        countOfWorking = 0;
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new ManagerMechanics(Id.managerMechanics, mySim(), this);
        new ProcessInsepction(Id.processInsepction, mySim(), this);
        addOwnMessage(Mc.mechanicExecute);
        addOwnMessage(Mc.mechanicsAvailability);
        addOwnMessage(Mc.noticeEndInspection);
    }
    //meta! tag="end"

    public int getTotalCountOfEmployees() {
        return totalCountOfEmployees;
    }

    public int getCountOfWorking() {
        return countOfWorking;
    }

    public void addWorkingEmployee() {
        if (countOfWorking < totalCountOfEmployees) {
            countOfWorking++;
        }
    }
    
    public void removeWorkingEmployee() {
        if (countOfWorking > 0) {
            countOfWorking--;
        }
    }

}
