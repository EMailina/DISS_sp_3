/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import java.awt.Canvas;
import java.awt.Dimension;
import static java.lang.Thread.sleep;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import simulation.MySimulation;

/**
 *
 * @author Erik
 */
public abstract class BaseAnimator {

    private MySimulation simulation;
    private Canvas canvas;
    private double tau = 10;
    private CopyOnWriteArrayList<AnimActivity> activities;
    boolean stop = false;
    Timer timer;

    public BaseAnimator(MySimulation simulation, Canvas canvas) throws InterruptedException {
        this.canvas = canvas;
        this.simulation = simulation;
        activities = new CopyOnWriteArrayList<>();
        init();

    }

    public void addAnimator() {
        simulation.setAnimator(this);
    }

    public void refresh() throws InterruptedException {

        double t = simulation.currentTime();

        CopyOnWriteArrayList<AnimActivity> toRemove = new CopyOnWriteArrayList<>();

        for (AnimActivity activity : activities) {
            if (activity.isEndOfAnimation()) {
                toRemove.add(activity);
            } else {
                activity.takeStep(t);
            }
        }
        activities.removeAll(toRemove);
        clearBackgroundOnDeleted(toRemove);
        drawBackGround();
        drawActivities();

    }

    public abstract void drawBackGround();

    public abstract void drawActivities();

    public abstract void init();

    public MySimulation getSimulation() {
        return simulation;
    }

    public void setSimulation(MySimulation simulation) {
        this.simulation = simulation;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public double getTau() {
        return tau;
    }

    public void setTau(double tau) {
        this.tau = tau;
    }

    public void addAnimActivity(AnimActivity animActivity) {
        //activities.add(animActivity);
        processNewActivity(animActivity);
    }

    public CopyOnWriteArrayList<AnimActivity> getActivities() {
        return activities;
    }

    public void setActivities(CopyOnWriteArrayList<AnimActivity> activities) {
        this.activities = activities;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public abstract void processNewActivity(AnimActivity animActivity);

    public abstract void clearBackgroundOnDeleted(CopyOnWriteArrayList<AnimActivity> toRemove);

    void createCanvas() {
        this.canvas = new Canvas();
    }
}
