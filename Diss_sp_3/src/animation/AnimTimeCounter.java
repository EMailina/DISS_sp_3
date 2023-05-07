/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

/**
 *
 * @author Erik
 */
public class AnimTimeCounter {

    private Animator animator;
    private double disPerPx;

    public AnimTimeCounter(Animator animator) {
        this.animator = animator;
        getTimePerPx();
    }

    private void getTimePerPx() {
        double distanceInPx = animator.getMaxLength();

        disPerPx = 800.0 / distanceInPx;
    }

    public double getTimePerRouteToEmp1(int employee) {
        double length = (double) animator.getLenght(animator.getRouteToEmp1(employee));
        double metres = length * disPerPx;
        double help = metres / (AnimConfig.speed / 3.6) / 60;
        if (25.0 / 9 * 60 != metres / help) {
            System.out.println("xxxx");
        }
        return help;

    }

    public double getTimePerRouteFromEmp1(int employee) {
        double length = (double) animator.getLenght(animator.getRouteFromEmp1(employee));
        double metres = length * disPerPx;
        double help = metres / (AnimConfig.speed / 3.6) / 60;
        
        return help;
    }

    public double getTimePerRouteToInspection(int employee) {
        double length = (double) animator.getLenght(animator.getRouteToInspection(employee));
        double metres = length * disPerPx;
        double help = metres / (AnimConfig.speed / 3.6) / 60;
       
        return help;
    }

    public double getTimePerRouteFromInspection(int employee) {
        double length = (double) animator.getLenght(animator.getRouteFromInspection(employee));
        double metres = length * disPerPx;
        double help = metres / (AnimConfig.speed / 3.6) / 60;
       
        return help;
    }

    public double getTimePerRouteFromPayment(int employee) {
        double length = (double) animator.getLenght(animator.getRouteFromPayment(employee));
        double metres = length * disPerPx;
        double help = metres / (AnimConfig.speed / 3.6) / 60;
       
        return help;
    }

    public double getTimePerRouteToEmp1Payment(int employee) {
        double length = (double) animator.getLenght(animator.getRouteToEmp1Payment(employee));
        double metres = length * disPerPx;
        double help = metres / (AnimConfig.speed / 3.6) / 60;
        
        return help;
    }

}
