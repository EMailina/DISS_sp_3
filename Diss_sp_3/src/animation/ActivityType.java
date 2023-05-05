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
public enum ActivityType {
    //QUEUE
    ADD_TO_TAKE_OVER_QUEUE,
    REMOVE_FROM_TAKE_OVER_QUEUE,
    ADD_TO_PAYMENT_QUEUE,
    REMOVE_FROM_PAYMENT_QUEUE,
    ADD_TO_PARKING_QUEUE,
    REMOVE_FROM_PARKING_QUEUE,
    //TYPE 1 EMPLOYEES
    ADD_PAUSE_TO_EMPLOYER_TYPE_1,
    REMOVE_PAUSE_FROM_EMPLOYER_TYPE_1,
    ADD_WORK_TO_EMPLOYER_TYPE_1,
    REMOVE_WORK_FROM_EMPLOYER_TYPE_1,
    ADD_WORK_TO_EMPLOYER_TYPE_1_PAYMENT,
    REMOVE_WORK_FROM_EMPLOYER_TYPE_1_PAYMENT,
    //TYPE 1 EMPLOYEES
    ADD_PAUSE_TO_EMPLOYER_TYPE_2,
    REMOVE_PAUSE_FROM_EMPLOYER_TYPE_2,
    ADD_WORK_TO_EMPLOYER_TYPE_2,
    REMOVE_WORK_FROM_EMPLOYER_TYPE_2,
    MOVING_EMP_1, 
    ADD_MOVE_TO_TAKE_OVER;
}