/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Erik
 */
public class AnimActivity {

    private double startTime;
    private double endTime;
    private CopyOnWriteArrayList<Point> turningPoints;
    private Point actualPoint;
    private int length;
    private int lengthTraveled;
    private Point pointToDelete;
    private Color color;
    private ActivityType type;
    private boolean staticObject;
    private boolean endOfAnimation = false;
    private int width;
    private double startMovingTime = -1;

    public AnimActivity() {
        turningPoints = new CopyOnWriteArrayList<Point>();
        lengthTraveled = 0;
        color = Color.BLACK;
        width = 30;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public int getStartX() {
        return turningPoints.get(0).getX();
    }

    public int getStartY() {
        return turningPoints.get(0).getY();
    }

    public int getEndX() {
        return turningPoints.get(1).getX();
    }

    public boolean isStaticObject() {
        return staticObject;
    }

    public void setStaticObject(boolean staticObject) {
        this.staticObject = staticObject;
    }

    public int getEndY() {
        return turningPoints.get(1).getY();
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public void takeStep(double time) {
        if (!staticObject) {
            pointToDelete = new Point(actualPoint.getX(), actualPoint.getY());
            Point start = turningPoints.get(0);
            Point end = turningPoints.get(1);
            if (actualPoint.getX() == end.getX() && actualPoint.getY() == end.getY()) {
                endOfAnimation = true;
            } else {

                double duration = endTime - startTime;
                double progress = time - startTime;

                //moving Y
                if (actualPoint.getX() == end.getX()) {
                    //moving down
                    if (end.getY() > actualPoint.getY()) {
                        int moveDistance = (int) ((progress / duration) * (length));

                        if (start.getY() + moveDistance - lengthTraveled >= end.getY()) {
                            //end of route
                            if (turningPoints.size() == 2) {
                                actualPoint.setY(end.getY());

                            } else {
                                int moved = end.getY() - actualPoint.getY();

                                actualPoint.setY(end.getY());
                                lengthTraveled += getLenght(turningPoints.get(0), turningPoints.get(1));
                                if (end.getX() < turningPoints.get(2).getX()) {

                                    actualPoint.setX(actualPoint.getX() + Math.abs(moveDistance - moved - lengthTraveled));
                                } else {
                                    actualPoint.setX(actualPoint.getX() + (moveDistance - moved - lengthTraveled));
                                }
                                turningPoints.remove(0);
                            }
                        } else {
                            actualPoint.setY(start.getY() + moveDistance - lengthTraveled);
                        }
                        //moving up
                    } else {
                        int moveDistance = (int) ((progress / duration) * (length)) * (-1);
                        int lengthTraveled = (-1) * this.lengthTraveled;
                        if (start.getY() + moveDistance - lengthTraveled <= end.getY()) {
                            //end of route
                            if (turningPoints.size() == 2) {
                                actualPoint.setY(end.getY());

                            } else {
                                int moved = Math.abs(end.getY() - actualPoint.getY());

                                actualPoint.setY(end.getY());
                                this.lengthTraveled += getLenght(turningPoints.get(0), turningPoints.get(1));
                                lengthTraveled = -this.lengthTraveled;
                                if (end.getX() < turningPoints.get(2).getX()) {

                                    actualPoint.setX(actualPoint.getX() + Math.abs(moveDistance - moved - lengthTraveled));
                                } else {
                                    actualPoint.setX(actualPoint.getX() + (moveDistance - moved - lengthTraveled));
                                }
                                turningPoints.remove(0);
                            }
                        } else {
                            actualPoint.setY(start.getY() + moveDistance - lengthTraveled);
                        }
                    }

                    //moving X    
                } else {
                    //moving right
                    if (end.getX() > actualPoint.getX()) {
                        int moveDistance = (int) ((progress / duration) * (length));

                        if (start.getX() + moveDistance - lengthTraveled >= end.getX()) {
                            //end of route
                            if (turningPoints.size() == 2) {
                                actualPoint.setX(end.getX());

                            } else {
                                int moved = end.getX() - actualPoint.getX();

                                actualPoint.setX(end.getX());
                                lengthTraveled += getLenght(turningPoints.get(0), turningPoints.get(1));
                                if (end.getY() < turningPoints.get(2).getY()) {

                                    actualPoint.setY(actualPoint.getY() + Math.abs(moveDistance - moved - lengthTraveled));
                                } else {

                                    actualPoint.setY(actualPoint.getY() + Math.abs(moveDistance - moved - lengthTraveled));
                                }
                                turningPoints.remove(0);
                            }
                        } else {
                            actualPoint.setX(start.getX() + moveDistance - lengthTraveled);
                        }

                        //moving left
                    } else {
                        int moveDistance = (int) ((progress / duration) * (length)) * (-1);
                        int lengthTraveled = (-1) * this.lengthTraveled;
                        if (start.getX() + moveDistance - lengthTraveled <= end.getX()) {
                            //end of route
                            if (turningPoints.size() == 2) {
                                actualPoint.setX(end.getX());

                            } else {
                                int moved = Math.abs(end.getX() - actualPoint.getX());

                                actualPoint.setX(end.getX());
                                this.lengthTraveled += getLenght(turningPoints.get(0), turningPoints.get(1));
                                lengthTraveled = -this.lengthTraveled;
                                if (end.getY() < turningPoints.get(2).getY()) {

                                    actualPoint.setY(actualPoint.getY() + Math.abs(moveDistance - moved - lengthTraveled));
                                } else {

                                    actualPoint.setY(actualPoint.getY() + Math.abs(moveDistance - moved - lengthTraveled));
                                }
                                turningPoints.remove(0);
                            }
                        } else {
                            actualPoint.setX(start.getX() + moveDistance - lengthTraveled);
                        }
                    }
                }

            }

            if (pointToDelete.getX() == actualPoint.getX() && pointToDelete.getY() == actualPoint.getY()) {
                pointToDelete = null;
            }
        } else {
            if (startMovingTime != -1) {
                if (time >= startMovingTime) {
                    staticObject = false;
                    startTime = startMovingTime;
                }
            }
            if (time > endTime) {
                endOfAnimation = true;
            }
        }
    }

    public double getStartMovingTime() {
        return startMovingTime;
    }

    public void setStartMovingTime(double startMovingTime) {
        this.startMovingTime = startMovingTime;
    }

    public int getActualX() {
        return actualPoint.getX();
    }

    public int getActualY() {
        return actualPoint.getY();
    }

    public boolean isEndOfAnimation() {
        return endOfAnimation;
    }

    public void addTurningPoints(int x, int y) {
        if (turningPoints.size() == 0) {
            actualPoint = new Point(x, y);
        } else {
            length += Math.abs(x - turningPoints.get(turningPoints.size() - 1).getX()) + Math.abs(y - turningPoints.get(turningPoints.size() - 1).getY());
        }
        turningPoints.add(new Point(x, y));
    }

    public int getLenght(Point p1, Point p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p2.getY() - p1.getY());
    }

    public int getPointToDeleteX() {
        if (pointToDelete == null) {
            return -1000;
        }
        return pointToDelete.getX();
    }

    public int getPointToDeleteY() {
        if (pointToDelete == null) {
            return -1000;
        }
        return pointToDelete.getY();
    }

    public CopyOnWriteArrayList<Point> getTurningPoints() {
        return turningPoints;
    }

    public void setTurningPoints(ArrayList<Point> turningPoints, int length) {
        for (Point turningPoint : turningPoints) {
            if(actualPoint == null){
                actualPoint = new Point(turningPoint.getX(), turningPoint.getY());
            }
            this.turningPoints.add(turningPoint);
        }
        this.length = length;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
