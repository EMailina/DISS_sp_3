/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import simulation.MySimulation;
import static animation.ActivityType.MOVING;

/**
 *
 * @author Erik
 */
public class Animator extends BaseAnimator {

    private int countType1;
    private int countType2;
    private int queueTakeOverLength = 0;
    private int queuePaymentLength = 0;
    private int queueParkingLength = 0;
    private CopyOnWriteArrayList<WorkType> employees1;
    private CopyOnWriteArrayList<WorkType> employees2;
    private boolean zoomed = false;
    private boolean clear = false;

    public Animator(MySimulation simulation, Canvas canvas) throws InterruptedException {
        super(simulation, canvas);

    }

    public int getMaxLength() {
        int l = getLenght(451, 100 + (countType1 - 1) * 100 + 10, 486, 100 + (countType1 - 1) * 100 + 10);
        l += getLenght(486, 100 + (countType1 - 1) * 100 + 10, 486, 160);
        l += getLenght(486, 160, 520, 160);
        return l;
    }

    public int getLenght(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    @Override
    public void drawBackGround() {

        Graphics g = getCanvas().getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        if (clear != zoomed) {
            try {
                g.setColor(Color.white);
                g.drawRect(0, 0, 16000, 16000);
                clear = zoomed;
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            g.drawLine(300, y + 25, 400, y + 25);
            g.drawLine(450, y + 25, 500, y + 25);
            y += 100;
        }

        g.drawLine(500, 125, 500, y - 75 < 250 ? 175 + 200 : y - 75 + 100);
        g.drawLine(0, y - 75 < 250 ? 175 + 200 : y - 75 + 100, 500, y - 75 < 250 ? 175 + 200 : y - 75 + 100);
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
            g.drawLine(800, y + 25, 900, y + 25);
            g.drawLine(950, y + 25, 1000, y + 25);
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
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < queueTakeOverLength; i++) {

            g.setColor(Color.BLUE);
            g.fillRect(AnimConfig.START_QUEUE_TAKE_OVER_X - i * AnimConfig.QUEUE_SPACE, AnimConfig.START_QUEUE_TAKE_OVER_Y, 30, 30);
            g.setColor(Color.BLACK);
            g.drawRect(AnimConfig.START_QUEUE_TAKE_OVER_X - i * AnimConfig.QUEUE_SPACE, AnimConfig.START_QUEUE_TAKE_OVER_Y, 30, 30);
        }
        g.setColor(Color.WHITE);
        g.fillRect(1, AnimConfig.START_QUEUE_TAKE_OVER_Y, AnimConfig.START_QUEUE_TAKE_OVER_X - ((queueTakeOverLength - 1) * AnimConfig.QUEUE_SPACE) - 1, 31);

        for (int i = 0; i < queuePaymentLength; i++) {

            g.setColor(Color.MAGENTA);
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
                    case WORK_PAYMENT:
                        g.setColor(Color.MAGENTA);
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
            if (activity.getType() == MOVING) {
                g.setColor(Color.WHITE);
                g.fillRect(activity.getPointToDeleteX(), activity.getPointToDeleteY(), activity.getWidth(), activity.getWidth());
                g.setColor(activity.getColor());
                g.fillRect(activity.getActualX(), activity.getActualY(), activity.getWidth(), activity.getWidth());

            } else {
                g.setColor(Color.WHITE);
                g.fillRect(activity.getStartX(), activity.getStartY(), 50, 10);
                g.setColor(Color.BLACK);
                g.fillOval(activity.getActualX(), activity.getActualY(), 10, 10);
                g.drawLine(activity.getStartX(), activity.getActualY() + 5, activity.getEndX() + 9, activity.getActualY() + 5);
            }
        }

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 150, 25);
        g.setColor(Color.BLACK);

        g.drawString("Time: " + String.format("%.3f", getSimulation().currentTime()), 10, 15);

    }

    @Override
    public void init() {
        countType1 = getSimulation().getCountOfEmpType1();
        countType2 = getSimulation().getCountOfEmpType2();
        Graphics g = getCanvas().getGraphics();
        g.setColor(Color.WHITE);
        try {
            sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(Animator.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.fillRect(0, 0, 8000, 8000);
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
        if (null != animActivity.getType())
        {
            switch (animActivity.getType()) {
                case REMOVE_FROM_TAKE_OVER_QUEUE:
                    queueTakeOverLength--;
                    break;

                case ADD_TO_TAKE_OVER_QUEUE:
                    queueTakeOverLength++;
                    break;

                case ADD_TO_PAYMENT_QUEUE:
                    queuePaymentLength++;
                    break;

                case REMOVE_FROM_PAYMENT_QUEUE:
                    queuePaymentLength--;
                    break;

                case ADD_TO_PARKING_QUEUE:
                    queueParkingLength++;
                    break;

                case REMOVE_FROM_PARKING_QUEUE:
                    queueParkingLength--;
                    break;

                case ADD_PAUSE_TO_EMPLOYER_TYPE_1:
                    employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.PAUSE);
                    initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_1_START_X);
                    getActivities().add(animActivity);
                    break;

                case REMOVE_PAUSE_FROM_EMPLOYER_TYPE_1:
                    employees1.set(((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);
                    break;

                case ADD_WORK_TO_EMPLOYER_TYPE_1:
                    //initWorkEmp1(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_1_START_X, WorkType.WORK);
                    employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.WORK);
                    initMoveEmployerType1(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());
                    initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_1_START_X);
                    getActivities().add(animActivity);
                    break;

                case ADD_WORK_TO_EMPLOYER_TYPE_1_PAYMENT:
                    employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.WORK_PAYMENT);
                    initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_1_START_X);
                    showPaymentVehicle(((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());
                    getActivities().add(animActivity);
                    break;

                case REMOVE_WORK_FROM_EMPLOYER_TYPE_1:
                    employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);
                    break;

                case REMOVE_WORK_FROM_EMPLOYER_TYPE_1_PAYMENT:
                    employees1.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);
                    break;

                case ADD_PAUSE_TO_EMPLOYER_TYPE_2:
                    employees2.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.PAUSE);
                    initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_2_START_X);
                    getActivities().add(animActivity);
                    break;

                case REMOVE_PAUSE_FROM_EMPLOYER_TYPE_2:
                    employees2.set(((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);
                    break;

                case ADD_WORK_TO_EMPLOYER_TYPE_2:
                    showInspectionVehicle(((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());

                    employees2.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.WORK);
                    initProcessActivity(animActivity, ((EmployeeAnimActivity) animActivity).getCount(), AnimConfig.PROCESS_EMP_2_START_X);
                    getActivities().add(animActivity);
                    break;

                case REMOVE_WORK_FROM_EMPLOYER_TYPE_2:
                    employees2.set((int) ((EmployeeAnimActivity) animActivity).getCount(), WorkType.FREE);
                    break;

                case ADD_MOVE_TO_TAKE_OVER:
                    initMoveToTakeOver(((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());
                    break;

                case ADD_MOVE_TO_PAYMENT:
                    initMoveToPayment(((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());
                    break;

                case ADD_MOVE_TO_INSPECTION:
                    initMoveToInspection(((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());
                    break;

                case ADD_MOVE_FROM_INSPECTION:
                    initMoveFromInspection(((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());
                    break;

                case ADD_MOVE_FROM_PAYMENT:
                    initMoveFromPayment(((EmployeeAnimActivity) animActivity).getCount(), animActivity.getStartTime(), animActivity.getEndTime());
                    break;

                default:
                    break;
            }
        }

    }

    private void initProcessActivity(AnimActivity animActivity, int count, int x) {
        animActivity.setStartMovingTime(-1);
        animActivity.setStaticObject(false);
        animActivity.addTurningPoints(x, AnimConfig.EMPLOYEE_START_Y + 100 * count - 15);
        animActivity.addTurningPoints(x + AnimConfig.PROCESS_WIDTH_LINE, AnimConfig.EMPLOYEE_START_Y + 100 * count - 15);

    }

    @Override
    public void clearBackgroundOnDeleted(CopyOnWriteArrayList<AnimActivity> toRemove) {
        Graphics g = getCanvas().getGraphics();


        for (AnimActivity animActivity : toRemove) {

            if (animActivity.getType() == ActivityType.MOVING) {
                g.setColor(Color.WHITE);
                g.fillRect(animActivity.getActualX(), animActivity.getActualY(), animActivity.getWidth(), animActivity.getWidth());
            }

            if (animActivity instanceof EmployeeAnimActivity) {
                g.setColor(Color.WHITE);
                g.fillRect(animActivity.getStartX() - 1, animActivity.getStartY() - 1, 52, 12);
            }
        }
    }

 

    private void initMoveEmployerType1(AnimActivity activity, int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING);
        animActivity.setStartTime(startTime);
        animActivity.setEndTime(endTime);
        animActivity.setStartMovingTime(activity.getStartMovingTime());
        animActivity.setStaticObject(activity.isStaticObject());
        animActivity.setTurningPoints(getRouteFromEmp1(count), getLenght(getRouteFromEmp1(count)));
        getActivities().add(animActivity);

    }

    private void initMoveToTakeOver(int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING);
        animActivity.setStartTime(startTime);
        animActivity.setEndTime(endTime);
        animActivity.setTurningPoints(getRouteToEmp1(count), getLenght(getRouteToEmp1(count)));
        getActivities().add(animActivity);

    }

    private void initMoveToPayment(int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING);
        animActivity.setStartTime(startTime);
        animActivity.setEndTime(endTime);
        animActivity.setColor(Color.magenta);
        animActivity.setTurningPoints(getRouteToEmp1Payment(count), getLenght(getRouteToEmp1Payment(count)));
        getActivities().add(animActivity);

    }

    private void initMoveToInspection(int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING);
        animActivity.setStartTime(startTime);
        animActivity.setEndTime(endTime);

        animActivity.setTurningPoints(getRouteToInspection(count), getLenght(getRouteToInspection(count)));

        getActivities().add(animActivity);
    }

    private void initMoveFromInspection(int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING);
        animActivity.setStartTime(startTime);
        animActivity.setEndTime(endTime);

        animActivity.setTurningPoints(getRouteFromInspection(count), getLenght(getRouteFromInspection(count)));

        getActivities().add(animActivity);
    }

    private void initMoveFromPayment(int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING);
        animActivity.setStartTime(startTime);
        animActivity.setEndTime(endTime);
        animActivity.setColor(Color.MAGENTA);
        animActivity.setTurningPoints(getRouteFromPayment(count), getLenght(getRouteFromPayment(count)));

        getActivities().add(animActivity);
    }

    public ArrayList<Point> getRouteToEmp1(int count) {

        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(250, 260));
        points.add(new Point(285, 260));
        points.add(new Point(285, 100 + count * 100 + 10));
        points.add(new Point(369, 100 + count * 100 + 10));

        return points;
    }

    public ArrayList<Point> getRouteToEmp1Payment(int count) {

        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(250, 160));
        points.add(new Point(285, 160));
        points.add(new Point(285, 100 + count * 100 + 10));
        points.add(new Point(369, 100 + count * 100 + 10));

        return points;
    }

    public ArrayList<Point> getRouteFromEmp1(int count) {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(451, 100 + count * 100 + 10));
        points.add(new Point(486, 100 + count * 100 + 10));
        points.add(new Point(486, 160));
        points.add(new Point(520, 160));

        return points;
    }

    public int getLenght(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p2.getY() - p1.getY());
    }

    public int getLenght(ArrayList<Point> points) {
        int l = 0;
        for (int i = 1; i < points.size(); i++) {
            l += getLenght(points.get(i - 1), points.get(i));
        }
        return l;
    }

    private void showPaymentVehicle(int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING);
        animActivity.setStartTime(startTime);
        animActivity.setColor(Color.MAGENTA);
        animActivity.setEndTime(endTime);
        animActivity.addTurningPoints(451, 100 + count * 100 + 10);
        animActivity.setStaticObject(true);
        getActivities().add(animActivity);
    }

    private void showInspectionVehicle(int count, double startTime, double endTime) {
        EmployeeAnimActivity animActivity = new EmployeeAnimActivity();
        animActivity.setCount(count);
        animActivity.setType(MOVING);
        animActivity.setStartTime(startTime);
        animActivity.setEndTime(endTime);
        animActivity.addTurningPoints(870, 100 + count * 100 + 10);
        animActivity.setStaticObject(true);
        getActivities().add(animActivity);
        animActivity.setWidth(30);
    }

    public ArrayList<Point> getRouteToInspection(int count) {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(750, 160));
        points.add(new Point(785, 160));
        points.add(new Point(785, 100 + count * 100 + 10));
        points.add(new Point(869, 100 + count * 100 + 10));

        return points;
    }

    public ArrayList<Point> getRouteFromInspection(int count) {
        ArrayList<Point> points = new ArrayList<>();

        points.add(new Point(950, 100 + count * 100 + 10));
        points.add(new Point(985, 100 + count * 100 + 10));
        points.add(new Point(985, 15));
        points.add(new Point(135, 15));
        points.add(new Point(135, 110));

        return points;
    }

    public ArrayList<Point> getRouteFromPayment(int count) {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(451, 100 + count * 100 + 10));
        points.add(new Point(486, 100 + count * 100 + 10));
        points.add(new Point(486, (countType1) * 100 + 100 - 75 < 250 ? 175 + 200 - 15 : countType1 * 100 + 100 - 75 + 100 - 15));
        points.add(new Point(1, countType1 * 100 + 100 - 75 < 250 ? 175 + 200 - 15 : countType1 * 100 + 100 - 75 + 100 - 15));

        return points;
    }



}
