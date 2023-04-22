package simulation;

import OSPABA.*;
import OSPStat.Stat;
import agents.*;
import java.util.Random;

public class MySimulation extends Simulation {

    private Random generatorOfGenerators = new Random(0);

    private Stat avgWaitingTime;

    public MySimulation() {
        init();
    }

    @Override
    public void prepareSimulation() {
        super.prepareSimulation();
        // Create global statistcis
        avgWaitingTime = new Stat();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Reset entities, queues, local statistics, etc...
        _agentModel.startSimulation();
    }

    @Override
    public void replicationFinished() {
        // Collect local statistics into global, update UI, etc...
        super.replicationFinished();
        avgWaitingTime.addSample(_agentReception.getStatWaitingTime().mean());

    }

    @Override
    public void simulationFinished() {
        // Dysplay simulation results
        super.simulationFinished();
        System.out.println("Waiting Time: " + avgWaitingTime.mean());
      
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        setAgentModel(new AgentModel(Id.agentModel, this, null));
        setAgentEnviroment(new AgentEnviroment(Id.agentEnviroment, this, agentModel()));
        setAgentVehicleInspection(new AgentVehicleInspection(Id.agentVehicleInspection, this, agentModel()));
        setAgentReception(new AgentReception(Id.agentReception, this, agentVehicleInspection()));
        setAgentMechanics(new AgentMechanics(Id.agentMechanics, this, agentVehicleInspection()));
        setAgentParking(new AgentParking(Id.agentParking, this, agentVehicleInspection()));
    }

    private AgentModel _agentModel;

    public AgentModel agentModel() {
        return _agentModel;
    }

    public void setAgentModel(AgentModel agentModel) {
        _agentModel = agentModel;
    }

    private AgentEnviroment _agentEnviroment;

    public AgentEnviroment agentEnviroment() {
        return _agentEnviroment;
    }

    public void setAgentEnviroment(AgentEnviroment agentEnviroment) {
        _agentEnviroment = agentEnviroment;
    }

    private AgentVehicleInspection _agentVehicleInspection;

    public AgentVehicleInspection agentVehicleInspection() {
        return _agentVehicleInspection;
    }

    public void setAgentVehicleInspection(AgentVehicleInspection agentVehicleInspection) {
        _agentVehicleInspection = agentVehicleInspection;
    }

    private AgentReception _agentReception;

    public AgentReception agentReception() {
        return _agentReception;
    }

    public void setAgentReception(AgentReception agentReception) {
        _agentReception = agentReception;
    }

    private AgentMechanics _agentMechanics;

    public AgentMechanics agentMechanics() {
        return _agentMechanics;
    }

    public void setAgentMechanics(AgentMechanics agentMechanics) {
        _agentMechanics = agentMechanics;
    }

    private AgentParking _agentParking;

    public AgentParking agentParking() {
        return _agentParking;
    }

    public void setAgentParking(AgentParking agentParking) {
        _agentParking = agentParking;
    }
    //meta! tag="end"

    public Random getGeneratorOfGenerators() {
        return generatorOfGenerators;
    }

}
