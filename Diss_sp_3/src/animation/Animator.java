/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import static animation.ActivityType.MOVING_EMP_1;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Thread.sleep;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.MySimulation;

/**
 *
 * @author Erik
 */
public class Animator extends BaseAnimator {

    private int countType1 = 1;
    private int countType2 = 1;
    private int queueTakeOverLength = 0;
    private int queuePaymentLength = 0;
    private int queueParkingLength = 0;
    private CopyOnWriteArrayList<WorkType> employees1;
    private CopyOnWriteArrayList<WorkType> employees2;

    public Animator(MySimulation simulation, Canvas canvas) throws InterruptedException {
        super(simulation, canvas);

    }

    @Override
    public void drawBackGround() {
        Graphics g = getCanvas().getGraphics();

        g.setColor(Color.white);

        //  g.fillRect(450, 0, 70, 1000);
        g.setColor(Color.BLACK);

        g.drawString("Queue before payment", 90, 140);
        g.drawRect(0, 150, 250, 50);
        g.drawString("Queue before take-over", 90, 240);
        g.drawRect(0, 250, 250, 50);
        g.drawString("Parking", 625, 140);
        g.drawRect(550, 150, 200, 50);

        g.drawLine(250, 275, 300, 275);
        g.drawLine(250, 175, 300, 175);
        g.drawLine(500, 175, 550, 175);
        g.drawLine(750, 175, 800, 175);

        //TAKEOVER
        int y = 100;
        for (int i = 0; i < countType1; i++) {
//            g.setColor(Color.GREEN);
//            g.fillRect(400, y, 50, 50);
//            g.setColor(Color.BLACK);
//            g.drawRect(400, y, 50, 50);
//
//            //TAKEOVER PROCESS
//            g.drawLine(400, y - 10, 450, y - 10);
//            g.fillOval(400, y - 15, 10, 10);
//
            g.drawLine(300, y + 25, 305, y + 25);
            g.drawLine(495, y + 25, 500, y + 25);
            y += 100;
        }

        g.drawLine(500, 125, 500, y - 75 < 150 ? 175 : y - 75);
        if (y - 75 < 300) {
            y = 350;
        }
        g.drawLine(300, 125, 300, y - 75);

        g.drawLine(150, 30, 1000, 30);
        g.drawLine(150, 30, 150, 100);

        y = 100;

        for (int i = 0; i < countType2; i++) {
//            g.setColor(Color.GREEN);
//            g.fillRect(900, y, 50, 50);
//            g.setColor(Color.BLACK);
//            g.drawRect(900, y, 50, 50);

            //TAKEOVER PROCESS
//            g.drawLine(900, y - 10, 950, y - 10);
//            g.fillOval(900, y - 15, 10, 10);
            g.drawLine(800, y + 25, 805, y + 25);
            g.drawLine(995, y + 25, 1000, y + 25);
            y += 100;
        }
        if (y - 75 < 325) {
            y = 300;
        }

        if (countType2 == 1) {
            g.drawLine(800, 125, 800, 175);
            g.drawLine(1000, 30, 1000, 125);

        } else if (countType2 == 2) {
            g.drawLine(800, 125, 800, 225);
            g.drawLine(1000, 30, 1000, y - 75 < 200 ? 200 : y - 75);

        } else {
            g.drawLine(800, 125, 800, y - 75);
            g.drawLine(1000, 30, 1000, y - 75 < 200 ? 200 : y - 75);
        }

    }

    @Override
    public void drawActivities() {
        Graphics g = getCanvas().getGraphics();
        for (int i = 0; i < queueTakeOverLength; i++) {

            g.setColor(Color.BLUE);
            g.fillRect(AnimConfig.START_QUEUE_TAKE_OVER_X - i * AnimConfig.QUEUE_SPACE, AnimConfig.START_QUEUE_TAKE_OVER_Y, 30, 30);
            g.setColor(Color.BLACK);
            g.drawRect(AnimConfig.START_QUEUE_TAKE_OVER_X - i * AnimConfig.QUEUE_SPACE, AnimConfig.START_QUEUE_TAKE_OVER_Y, 30, 30);
        }
        g.setColor(Color.WHITE);
        g.fillRect(1, AnimConfig.START_QUEUE_TAKE_OVER_Y, AnimConfig.START_QUEUE_TAKE_OVER_X - ((queueTakeOverLength - 1) * AnimConfig.QUEUE_SPACE) - 1, 31);

        for (int i = 0; i < queuePaymentLength; i++) {

            g.setColor(Color.BLUE);
            g.fillRect(AnimConfig.START_QUEUE_PAYMENT_X - i * AnimConfig.QUEUE_SPACE, AnimConfig.START_QUEUE_PAYMENT_Y, 30, 30);
            g.setColor(Color.BLACK);
            g.drawRect(AnimConfig.START_QUEUE_PAYMENT_X - i * AnimConfig.QUEUE_SPACE, AnimConfig.START_QUEUE_PAYMENT_Y, 30, 30);
        }
        g.setColor(Color.WHITE);
        g.fillRect(1, AnimConfig.START_QUEUE_PAYMENT_Y, AnimConfig.START_QUEUE_PAYMENT_X - ((queuePaymentLength - 1) * AnimConfig.QUEUE_SPACE) - 1, 31);

        g.setColor(Color.WHITE);
        g.fillRect(551, AnimConfig.START_QUEUE_PARKING_Y, 198, 31);
        for (int i = 0; i < queueParkingLength; i++) {
            g.setColor(Color.BLUE);
            g.fillRect(AnimConfig.START_QUEUE_PARKING_X - i * AnimConfig.QUEUE_SPACE_PARKING, AnimConfig.START_QUEUE_PARKING_Y, 30, 30);
            g.setColor(Color.BLACK);
            g.drawRect(AnimConfig.START_QUEUE_PARKING_X - i * AnimConfig.QUEUE_SPACE_PARKING, AnimConfig.START_QUEUE_PARKING_Y, 30, 30);
        }

        for (int i = 0; i < countType1; i++) {
            if (null != employees1.get(i)) {
                switch (employees1.get(i)) {
                    case FREE:
                        g.setColor(Color.GREEN);
                        break;
                    case PAUSE:
                        g.setColor(Color.YELLOW);
                        break;
                    case WORK:
                        g.setColor(Color.BLACK);
                        break;
                    default:
                        break;
                }
            }
            g.fillRect(400, AnimConfig.EMPLOYEE_START_Y + i * 100, 50, 50);
            g.setColor(Color.BLACK);
            g.drawRect(400, AnimConfig.EMPLOYEE_START_Y + i * 100, 50, 50);
        }

        for (int i = 0; i < countType2; i++) {
            if (null != employees2.get(i)) {
                switch (employees2.get(i)) {
                    case FREE:
                        g.setColor(Color.GREEN);
                        break;
                    case PAUSE:
                        g.setColor(Color.YELLOW);
                        break;
                    case WORK:
                        g.setColor(Color.BLACK);
                        break;
                    default:
                        break;
                }
            }
            g.fillRect(900, AnimConfig.EMPLOYEE_START_Y + i * 100, 50, 50);
            g.setColor(Color.BLACK);
            g.drawRect(900, AnimConfig.EMPLOYEE_START_Y + i * 100, 50, 50);
        }

        for (AnimActivity activity : getActivities()) {
            if (activity.getType() == MOVING_EMP_1) {
                g.setColor(Color.WHITE);
                g.fillRect(activity.getPointToDeleteX(), activity.getPointToDeleteY(), 30, 30);
                g.setColor(Color.BLACK);
                g.fillRect(activity.getActualX(), activity.getActualY(), 30, 30);
            } else {
                g.setColor(Color.WHITE);
                g.fillRect(activity.getStartX(), activity.getStartY(), 50, 10);
                g.setColor(Color.BLACK);
                g.fillOval(activity.getActualX(), activity.getActualY(), 10, 10);
                g.drawLine(activity.getStartX(), activity.getActualY() + 5, activity.getEndX() + 9, activity.getActualY() + 5);
            }
        }
    }

    @Override
    public void init() {
        countType1 = getSimulation().getCountOfEmpType1();
        countType2 = getSimulation().getCountOfEmpType2();
        Graphics g = getCanvas().getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 2000, 2000);
        employees1 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < countType1; i++) {
            employees1.add(WorkType.FREE);
        }

        employees2 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < countType2; i++) {
            employees2.add(WorkType.FREE);
        }
    }

    @Override
    public void processNewActivity(AnimActivity animActivity) {
        // InspectionActivity act = (InspectionActivity) animActivity;
        if (animActivity.getType() == ActivityType.REMOVE_FROM_TAKE_OVER_QUEUE) {
//            int min = Integer.MAX_VALUE;
//            AnimActivity activityToDelete = null;
            queueTakeOverLength--;
//            for (AnimActivity activity : getActivities()) {
//                if (min > activity.getStartX()) {
//                    min = activity.getStartX();
//                    activityToDelete = activity;
//                }
//            }
//            getActivities().remove(activityToDelete);
        } else if (animActivity.getType() == ActivityType.ADD_TO_TAKE_OVER_QUEUE) {
            queueTakeOverLength++;
        } else if (animActivity.getType() == ActivityType.ADD_TO_PAYMENT_QUEUE) {
            queuePaymentLength++;
        } else if (animActivity.getType() == ActivityType.REMOVE_FROM_PAYMENT_QUEUE) {
            queuePaymentLength--;
        } else if (animActivity.getType() == ActivityType.ADD_TO_PARKING_QUEUE) {
            queueParkingLength++;
        } else if (animActivity.getType() == ActivityType.REMOVE_FROM_PARKING_QUEUE) {
            queueParkingLength--;
        } else if (animActivity.getType() == ActivityType.ADD_PAUSE_TO_EMPLOYER_TYPE_1) {
            employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.PAUSE);
            initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_1_START_X);
            getActivities().add(animActivity);

        } else if (animActivity.getType() == ActivityType.REMOVE_PAUSE_FROM_EMPLOYER_TYPE_1) {
            employees1.set(((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);

        } else if (animActivity.getType() == ActivityType.ADD_WORK_TO_EMPLOYER_TYPE_1) {
            employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.WORK);
            initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_1_START_X);
            getActivities().add(animActivity);
            initMoveEmployerType1(((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());
        } else if (animActivity.getType() == ActivityType.ADD_WORK_TO_EMPLOYER_TYPE_1_PAYMENT) {
            employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.WORK);
            initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_1_START_X);
            getActivities().add(animActivity);
        } else if (animActivity.getType() == ActivityType.REMOVE_WORK_FROM_EMPLOYER_TYPE_1) {
            employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);
        } else if (animActivity.getType() == ActivityType.REMOVE_WORK_FROM_EMPLOYER_TYPE_1_PAYMENT) {
            employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);
        } else if (animActivity.getType() == ActivityType.ADD_PAUSE_TO_EMPLOYER_TYPE_2) {
            employees2.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.PAUSE);
            initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_2_START_X);
            getActivities().add(animActivity);
        } else if (animActivity.getType() == ActivityType.REMOVE_PAUSE_FROM_EMPLOYER_TYPE_2) {
            employees2.set(((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);

        } else if (animActivity.getType() == ActivityType.ADD_WORK_TO_EMPLOYER_TYPE_2) {
            employees2.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.WORK);
            initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_2_START_X);
            getActivities().add(animActivity);
        } else if (animActivity.getType() == ActivityType.REMOVE_WORK_FROM_EMPLOYER_TYPE_2) {
            employees2.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);
        }
    }

    private void initProcessActivity(AnimActivity animActivity, int count, int x) {
//        animActivity.setStartX(x);
//        animActivity.setStartY();
//        animActivity.setEndX();
//        animActivity.setEndY();

        animActivity.addTurningPoints(x, AnimConfig.EMPLOYEE_START_Y + 100 * count - 15);
        animActivity.addTurningPoints(x + AnimConfig.PROCESS_WIDTH_LINE, AnimConfig.EMPLOYEE_START_Y + 100 * count - 15);

    }

    @Override
    public void clearBackgroundOnDeleted(CopyOnWriteArrayList<AnimActivity> toRemove) {
        Graphics g = getCanvas().getGraphics();
        for (AnimActivity animActivity : toRemove) {

            if (animActivity.getType() == ActivityType.MOVING_EMP_1) {
                g.setColor(Color.WHITE);
                g.fillRect(animActivity.getActualX(), animActivity.getActualY(), 30, 30);
            }

            if (animActivity instanceof EmployeeAnimActivity) {
                g.setColor(Color.WHITE);
                g.fillRect(animActivity.getStartX() - 1, animActivity.getStartY() - 1, 52, 12);
            }
        }
    }

    private void initMoveEmployerType1(int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING_EMP_1);
        animActivity.setStartTime(startTime);
        animActivity.setEndTime(endTime);

//        animActivity.addTurningPoints(451, 110);
//        animActivity.addTurningPoints(486, 110);
//        animActivity.addTurningPoints(486, 160);
//        animActivity.addTurningPoints(520, 160);
        animActivity.addTurningPoints(451, 100 + count * 100 + 10);
        animActivity.addTurningPoints(486, 100 + count * 100 + 10);
        animActivity.addTurningPoints(486, 160);
        animActivity.addTurningPoints(520, 160);
        getActivities().add(animActivity);

    }

}
