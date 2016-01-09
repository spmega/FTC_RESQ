package com.qualcomm.ftcrobotcontroller.opmodes.myOpmodes;

import android.net.NetworkInfo;

/**
 * Created by shanky on 11/15/15.
 */

public class AutoOpMode extends NewOpModeMethods implements AutoOp{

    private States state = States.INIT_RESET;

    @Override
    public void init_loop() throws RuntimeException{
        try {
            resetEncoders();
        } catch (HardwareException e) {
            stop();
            throw new RuntimeException();
        }
    }

    @Override
    public void loop() {
        switch(state){
            case FIRST_STAGE:
                try {
                    drive(1000, 1000, 0.5, 0.5);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.SECOND_STAGE;
                break;
            case SECOND_STAGE:
                try {
                    turnRobot(90);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.THIRD_STAGE;
                break;
            case THIRD_STAGE:
                try {
                    drive(500, 500, 0.5, 0.5);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.FOURTH_STAGE;
                break;
            case FOURTH_STAGE:
                try {
                    turnRobot(90);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.FIFTH_STAGE;
                break;
            case FIFTH_STAGE:
                try {
                    drive(500, 500, 0.5, 0.5);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.SIXTH_STAGE;
                break;
            case SIXTH_STAGE:
                try {
                    turnRobot(90);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.SECOND_STAGE;
                break;
            case SEVENTH_STAGE:
                try {
                    drive(500, 500, 0.5, 0.5);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.EIGHTH_STAGE;
                break;
            case EIGHTH_STAGE:
                try {
                    turnRobot(90);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.NINTH_STAGE;
                break;
            case NINTH_STAGE:
                try {
                    drive(500, 500, 0.5, 0.5);
                } catch (HardwareException e) {
                    e.printStackTrace();
                }
                state = States.TENTH_STAGE;
                break;
            case TENTH_STAGE:
                stop();
                break;
        }
    }
}
