/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author Erik
 */
public class CustomerObject {

    double probabilityVehicle;
    private long count;
    private double startOfWaitingForTakeOver;
    private double endOfWaitingForTakeOver;

    private double endTimeInSystem;
    private boolean inspectionRewrite = false;

    private boolean paymentRewrite = false;
    private boolean takeOverRewrite = false;
    private boolean parkingRewrite = false;
    private boolean waitingForPayment = false;
    private boolean park = false;
    private boolean pause = false;

    private double carLimit = 0.65;
    private double truckLimit = 0.86;
    
    
    public CustomerObject() {
       

    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setProbabilityVehicle(double probabilityVehicle) {
        this.probabilityVehicle = probabilityVehicle;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setStartOfWaitingForTakeOver(double startOfWaitingForTakeOver) {
        this.startOfWaitingForTakeOver = startOfWaitingForTakeOver;
    }

    
    
    public boolean isPark() {
        return park;
    }

    public void setPark(boolean park) {
        this.park = park;
    }

    
    public boolean isParkingRewrite() {
        return parkingRewrite;
    }

    public void setParkingRewrite(boolean parkingRewrite) {
        this.parkingRewrite = parkingRewrite;
    }

    public boolean isWaitingForPayment() {
        return waitingForPayment;
    }

    public void setWaitingForPayment(boolean waitingForPayment) {
        this.waitingForPayment = waitingForPayment;
    }

    public boolean isInspectionRewrite() {
        return inspectionRewrite;
    }

    public void setInspectionRewrite(boolean inspectionRewrite) {
        this.inspectionRewrite = inspectionRewrite;
    }

    public boolean isPaymentRewrite() {
        return paymentRewrite;
    }

    public void setPaymentRewrite(boolean paymentRewrite) {
        this.paymentRewrite = paymentRewrite;
    }

    public boolean isTakeOverRewrite() {
        return takeOverRewrite;
    }

    public void setTakeOverRewrite(boolean takeOverRewrite) {
        this.takeOverRewrite = takeOverRewrite;
    }

    public double getProbabilityVehicle() {
        return probabilityVehicle;
    }

    public long getCount() {
        return count;
    }

    public double getStartOfWaitingForTakeOver() {
        return startOfWaitingForTakeOver;
    }

    public double getEndOfWaitingForTakeOver() {
        return endOfWaitingForTakeOver;
    }

    public void setEndOfWaitingForTakeOver(double endOfWaitingForTakeOver) {
        this.endOfWaitingForTakeOver = endOfWaitingForTakeOver;
    }

    public double getWaitingTime() {

        return endOfWaitingForTakeOver - startOfWaitingForTakeOver;
    }

    public double getEndTimeInSystem() {
        return endTimeInSystem;
    }

    public void setEndTimeInSystem(double endTimeInSystem) {
        this.endTimeInSystem = endTimeInSystem;
    }

    public double getTimeInSystem() {
        return endTimeInSystem - startOfWaitingForTakeOver;
    }

    public boolean isTruck() {
        if(probabilityVehicle < VehicleConstants.TRUCK_LIMIT){
            return false;
        }
        return true;
    }
    
      public boolean isCar() {
        if(probabilityVehicle < carLimit){
            return true;
        }
        return false;
    }

      public String getNameVehicle(){
          if(isTruck()){
              return "TRUCK";
          }else if (isCar()){
              return "PERSONAL";
          }
          return "VAN";
      }
      
}
