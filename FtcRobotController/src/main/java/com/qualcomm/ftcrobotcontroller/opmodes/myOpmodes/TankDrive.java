package com.qualcomm.ftcrobotcontroller.opmodes.myOpmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by shanky on 11/14/15.
 */
public class TankDrive extends NewOpModeMethods{
    //public DcMotor base;

    public TankDrive(){
        //this is a supplemental iniatlizaion method to initalize some extra components
    }

    @Override
    public void loop(){
        /*
        super.getArm().setPower(0.0);
        //all the buttons, control states of the servo, whether open or not
        if(gamepad1.a){
            super.getArm().setPower(0.3);
        }

        if(gamepad1.b){
            super.getArm().setPower(-0.3);
        }

        if(gamepad1.x){
            super.getRightFlipper().setPosition(0.0);
        }

        if(gamepad1.y) {
            super.getRightFlipper().setPosition(0.5);
        }
        */
        //the dpad controls the arm
        /*if(gamepad1.dpad_down){
            base.setPower(-0.8);
        }

        if(gamepad1.dpad_up){
            base.setPower(0.8);
        }

        if(gamepad1.dpad_left){
            arm.setPower(-0.8);
        }

        if(gamepad1.dpad_right){
            arm.setPower(0.8);
        }*/

        //the joysticks control the tracks
        super.manualDrive();
    }
}
