package simulation;

import OSPABA.*;
import OSPStat.Stat;
import agents.*;
import diss_sp_3.RunType;
import java.time.LocalTime;
import java.util.Random;

public class MySimulation extends Simulation {

    private Random generatorOfGenerators = new Random(0);

    private Stat avgWaitingTime;
    private Stat avgQueueLength;
    private Stat avgTimeInSystem;
    private Stat avgFreeEmp1;
    private Stat avgFreeEmp2;
    private Stat avgCOuntOfVehicles;
    private Stat avgCountOfCustomers;
    private RunType type;
    private boolean validationRun = false;

    public MySimulation() {
        init();
    }
    
    public MySimulation(long i) {
        generatorOfGenerators = new Random(i);
        init();
        
    }

    public MySimulation(RunType type) {
        this.type = type;
    }

    @Override
    public void prepareSimulation() {
        super.prepareSimulation();
        // Create global statistcis
        avgWaitingTime = new Stat();
        avgQueueLength = new Stat();
        avgTimeInSystem = new Stat();
        avgFreeEmp1 = new Stat();
        avgFreeEmp2 = new Stat();
        avgCOuntOfVehicles = new Stat();
        avgCountOfCustomers = new Stat();

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
        
        
        agentReception().getQueueTakeOver().enqueue(null);
        agentReception().getQueueTakeOver().dequeue();
        agentReception().getEmployee().enqueue(null);
        agentReception().getEmployee().dequeue();
        agentEnviroment().getCustomers().enqueue(null);
        agentEnviroment().getCustomers().dequeue();
        agentMechanics().getEmployee().enqueue(null);
        agentMechanics().getEmployee().dequeue();
        
        avgWaitingTime.addSample(_agentReception.getStatWaitingTime().mean());
        avgQueueLength.addSample(_agentReception.getQueueTakeOverAvgLength().mean());
        avgTimeInSystem.addSample(_agentEnviroment.getAverageTimeInSystem().mean());
        avgFreeEmp1.addSample(_agentReception.getEmployee().lengthStatistic().mean());
        avgFreeEmp2.addSample(_agentMechanics.getEmployee().lengthStatistic().mean());
        avgCOuntOfVehicles.addSample(_agentEnviroment.getCustomers().size());
        avgCountOfCustomers.addSample(_agentEnviroment.getCustomers().lengthStatistic().mean());
        super.replicationFinished();
    }

    @Override
    public void simulationFinished() {
        // Dysplay simulation results
        
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println("Waiting Time: " + avgWaitingTime.mean());
//        System.out.println("Queue len Time: " + avgQueueLength.mean());
//        System.out.println("EMP 1 : " + (1-avgFreeEmp1.mean()));
//        System.out.println("EMP 2: " + (1-avgFreeEmp2.mean()));
//        System.out.println("Time in sys : " + avgTimeInSystem.mean());
//        System.out.println("vehicles: " + avgCOuntOfVehicles.mean());
//        System.out.println("countOfCustomers: " + avgCountOfCustomers.mean());
       
        super.simulationFinished();

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

    public LocalTime getTime() {
        return LocalTime.of(9, 0).plusMinutes((long) currentTime());
    }

    public Stat getAvgWaitingTime() {
        return avgWaitingTime;
    }

    public Stat getAvgQueueLength() {
        return avgQueueLength;
    }

    public Stat getAvgTimeInSystem() {
        return avgTimeInSystem;
    }

    public Stat getAvgFreeEmp1() {
        return avgFreeEmp1;
    }

    public Stat getAvgFreeEmp2() {
        return avgFreeEmp2;
    }

    public Stat getAvgCOuntOfVehicles() {
        return avgCOuntOfVehicles;
    }

    public Stat getAvgCountOfCustomers() {
        return avgCountOfCustomers;
    }

    public RunType getType() {
        return type;
    }

    public void setType(RunType type) {
        this.type = type;
    }

    public void setCountOfEmployeeType1(Integer count) {
        _agentReception.setTotalCountOfEmployees(count);
    }

    public void setCountOfEmployeeType2WithCertificate1(Integer count) {
        _agentMechanics.setTotalCountOfEmployeesWithCertificate1(count);
    }

    public void setSpeedChange(int value) {

        if (type == RunType.SIMULATION) {

            if (value == 1) {
                setSimSpeed(1, 1);
            } else if (value == 2) {
                setSimSpeed(1, 0.350);
            } else if (value == 3) {
                setSimSpeed(1.0 / 60.0, 0.0001);
            } else if (value == 4) {
                setSimSpeed(1/60.0, 0.001);
            } else {
                setSimSpeed(1/60.0, 0.00010);
            }
        }

    }

    public boolean isValidationRun() {
        return validationRun;
    }

    public void setValidationRun(boolean validationRun) {
        this.validationRun = validationRun;
    }

    public void setGeneratorOfGenerators(Random generatorOfGenerators) {
        this.generatorOfGenerators = generatorOfGenerators;
    }

    public void setCountOfEmployeeType2WithCertificate2(Integer count) {
         _agentMechanics.setTotalCountOfEmployeesWithCertificate2(count);
    }
    
    

}
