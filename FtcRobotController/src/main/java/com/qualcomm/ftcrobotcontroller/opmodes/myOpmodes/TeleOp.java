package com.qualcomm.ftcrobotcontroller.opmodes.myOpmodes;

/**
 * Created by shanky on 11/14/15.
 */
public class TeleOp extends NewOpModeMethods{
    //public DcMotor base;

    public TeleOp(){
        //this is a supplemental iniatlizaion method to initalize some extra components
    }

    @Override
    public void loop(){
        super.manualDrive();
    }
}
