package com.qualcomm.ftcrobotcontroller.opmodes.myOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by shanky on 11/14/15.
 */
public abstract class NewOpModeMethods extends OpMode {
    private DcMotorController dcMotorController = null;

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    //private DcMotor tapeMeasureMotor;
    //private DcMotor;
    //private DcMotor;
    //private DcMotor;
    //private DcMotor;

    /*private Servo tapeMeasureServo;
    private Servo leftServo;
    private Servo rightServo;
    private Servo hookServo;
    private Servo climberServo;*/

    private final double MIN_MOTOR_POWER = 1.0;
    private final double MAX_MOTOR_POWER = -1.0;
    private final int ROBOT_WIDTH = 18;
    private final int TICKS_PER_ROTATION = 3193;

    private final String LEFT_DRIVE_NAME = "leftDrive";
    private final String RIGHT_DRIVE_NAME = "rightDrive";

    private int leftDrivePort= 0;
    private int rightDrivePort = 0;


    @Override
    public void init() {
        //I'm initializing the motors
        leftDrive = hardwareMap.dcMotor.get(LEFT_DRIVE_NAME);
        rightDrive = hardwareMap.dcMotor.get(RIGHT_DRIVE_NAME);
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        //tapeMeasureMotor = hardwareMap.dcMotor.get("tapeMeasureMotor");

        leftDrivePort = leftDrive.getPortNumber();
        rightDrivePort = rightDrive.getPortNumber();


        //I'm initializing the dcMotorController so that I can
        //use only on object throughout the entire code
        //which makes convenient

        try {
            setTheMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            setTheDirection(null, null);
            resetEncoders();
            initializeServos();
        }catch(HardwareException e){
            e.printStackTrace();
        }

    }

    private void initializeServos() throws HardwareException {
        /*leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
        tapeMeasureServo = hardwareMap.servo.get("tapeMeasureServo");
        hookServo = hardwareMap.servo.get("hookServo");
        climberServo = hardwareMap.servo.get("climberServo");

        rightServo.setDirection(Servo.Direction.REVERSE);

        if(leftServo.getPosition()<=0.5)
            leftServo.scaleRange(leftServo.getPosition(), leftServo.getPosition()+0.5);
        else
            throw new HardwareException();

        if(rightServo.getPosition()<=0.5)
            rightServo.scaleRange(rightServo.getPosition(), rightServo.getPosition()+0.5);
        else
            throw new HardwareException();

        if(tapeMeasureServo.getPosition()<=0.8)
            tapeMeasureServo.scaleRange(tapeMeasureServo.getPosition()-0.5, tapeMeasureServo.getPosition()+0.2);
        else
            throw new HardwareException();

        if(hookServo.getPosition()<=0.5)
            hookServo.scaleRange(hookServo.getPosition(), hookServo.getPosition()+0.5);
        else
            throw new HardwareException();

        if(climberServo.getPosition()<=0.5)
            climberServo.scaleRange(climberServo.getPosition(), Servo.MAX_POSITION);*/
    }


    //all getters methods that get the requested variable
    public DcMotor getLeftDrive(){
        return leftDrive;
    }

    public DcMotor getRightDrive() {return rightDrive;}

    public void setTheDirection(DcMotor.Direction direction, String name) throws HardwareException{
        if(direction != null && name != null){
            if(LEFT_DRIVE_NAME == name){
                leftDrive.setDirection(direction);
            } else if(RIGHT_DRIVE_NAME == name){
                rightDrive.setDirection(direction);
            } else{
                throw new HardwareException("There was an error");
            }
        } else{
            if (leftDrivePort < rightDrivePort) {
                rightDrive.setDirection(DcMotor.Direction.REVERSE);
            } else if (rightDrivePort > leftDrivePort) {
                leftDrive.setDirection(DcMotor.Direction.REVERSE);
            } else if (leftDrivePort == rightDrivePort) {
                throw new HardwareException("There was an error, " +
                        leftDrive.getDeviceName() + " and " + rightDrive.getDeviceName() +
                        "port's are equal. What doi I do now?"
                );
            } else {
                throw new HardwareException("There was an error, and I " +
                        "don't know exactly what happened.");
            }
        }
    }

    //Used for setting the runMode for each of the motors
    public void setTheMode(DcMotorController.RunMode runMode) throws HardwareException {

        leftDrive.setMode(runMode);
        rightDrive.setMode(runMode);

        if(leftDrive.getMode() != runMode
                && rightDrive.getMode() != runMode){
            throw new HardwareException("setting the run mode was not possible");
        }
    }

    public void resetEncoders() throws HardwareException {
        leftDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        if(!leftDrive.isBusy()
                && !rightDrive.isBusy()
                && leftDrive.getCurrentPosition() == 0
                && rightDrive.getCurrentPosition() == 0);
        else{
            throw new HardwareException("Resetting the encoders had encountered a problem");
        }
    }

    public void basictel(){
        telemetry.addData("Dcmotor port number and name: " , leftDrivePort + ", "
                + leftDrive.getDeviceName());
        telemetry.addData(leftDrive.getDeviceName() + "'s connect info:" ,
                leftDrive.getConnectionInfo());
        //telemetry.addData("" , "=============================");
        telemetry.addData("Dcmotor port number and name: " , rightDrivePort
                + rightDrive.getDeviceName());
        telemetry.addData(rightDrive.getDeviceName() + " connection info:" ,
                rightDrive.getConnectionInfo());
        telemetry.addData(leftDrive.getDeviceName() + " current position",
                leftDrive.getCurrentPosition());
        telemetry.addData(rightDrive.getDeviceName() + " current position",
                rightDrive.getCurrentPosition());
        telemetry.addData("Time:", telemetry.getTimestamp());
        /*telemetry.addData("leftServo:", leftServo.getPosition());
        telemetry.addData("rightServo:", rightServo.getPosition());
        telemetry.addData("climberServo:", climberServo.getPosition());
        telemetry.addData("HookServo", hookServo.getPosition());
        telemetry.addData("tapeMeasureServo:", tapeMeasureServo.getPosition());*/
    }

    public boolean hasReached(){
        return Math.abs(leftDrive.getCurrentPosition()
                - leftDrive.getTargetPosition()) == 0
                && Math.abs(rightDrive.getCurrentPosition()
                - rightDrive.getTargetPosition()) == 0;
    }

    public void drive(int leftPos, int rightPos, double leftPow, double rightPow) throws HardwareException, IllegalArgumentException {
        if(leftPow > MAX_MOTOR_POWER || leftPow < MIN_MOTOR_POWER
                || rightPow > MAX_MOTOR_POWER || rightPow < MIN_MOTOR_POWER)
            throw new IllegalArgumentException();

        if(rightPow > 0 && rightPos > 0)
            rightPos = rightPos * -1;

        try{
            setTargetPos(leftPos, rightPos);
            setMotorPowers(leftPow, rightPow);
            if(hasReached()) {
                setMotorPowers(0.0, 0.0);
            }
        } catch(HardwareException e){
            e.printStackTrace();
        }

        if(!leftDrive.isBusy()
                && !rightDrive.isBusy()){
            throw new HardwareException("The drive() method did not work.");
        }
    }

    public void setTargetPos(int leftPos, int rightPos)throws HardwareException{
        leftDrive.setTargetPosition(leftPos);
        rightDrive.setTargetPosition(rightPos);

        if(leftDrive.getTargetPosition() != leftPos
                && rightDrive.getTargetPosition() != rightPos){
            throw new HardwareException("There was an error trying to assign the target position");
        }
    }

    public void setMotorPowers(double leftPow, double rightPow)throws HardwareException{
        setLeftMotorPower(leftPow);
        setRightMotorPower(rightPow);

        if(!leftDrive.isBusy()
                && !rightDrive.isBusy()
                && leftDrive.getPower() != leftPow
                && rightDrive.getPower() != rightPow){
            throw new HardwareException("There was an error in trying to set the power of the motors.");
        }
    }

    public boolean setLeftMotorPower(double leftPow){
        leftDrive.setPower(leftPow);

        return leftDrive.isBusy()
                && leftDrive.getPower() == leftPow;
    }

    public boolean setRightMotorPower(double rightPow){
        rightDrive.setPower(rightPow);

        return rightDrive.isBusy()
                && rightDrive.getPower() == rightPow;
    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    public void turnRobot(int degrees) throws HardwareException{
        //get the percent the angle of the sector compared to the angle of a circle
        double percent = degrees/360;
        //get the percent of the total circumference, giving me
        //amount of inches needed to move each side of the robot
        double partialCircumference = ((ROBOT_WIDTH*Math.PI) * percent);
        //calculate the ticks needed to move to that amount and pass it to setTargetPos()
        setTargetPos((int)partialCircumference*TICKS_PER_ROTATION
                ,(int)partialCircumference*TICKS_PER_ROTATION);
        setMotorPowers(0.3, -0.3);
        if(hasReached())
            setMotorPowers(0.0, 0.0);
    }

    public void manualDrive(){
        float leftPower = Range.clip(gamepad1.left_stick_y, -1, 1);
        float rightPower = Range.clip(gamepad1.right_stick_y, -1, 1);
        //float tapeMeasureMotorPower = Range.clip(gamepad2.right_stick_y, -1, 1);;

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        rightPower = (float) scaleInput(rightPower);
        leftPower =  (float) scaleInput(leftPower);
        //tapeMeasureMotorPower = (float) scaleInput(tapeMeasureMotorPower);

        // write the values to the motors
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
        //tapeMeasureMotor.setPower(tapeMeasureMotorPower);

        senseButtons();
    }

    public void senseButtons(){
        /*if(gamepad1.a){
            leftServo.setPosition(leftServo.getPosition()-0.1);
            rightServo.setPosition(rightServo.getPosition()-0.1);
        } else if(gamepad1.b){
            leftServo.setPosition(leftServo.getPosition()+0.1);
            rightServo.setPosition(rightServo.getPosition()+0.1);
        } else if(gamepad2.a){
            hookServo.setPosition(hookServo.getPosition()-0.1);
        } else if(gamepad2.b){
            hookServo.setPosition(hookServo.getPosition()+0.1);
        } else if(gamepad2.x) {
            climberServo.setPosition(climberServo.getPosition()-0.1);
        } else if(gamepad2.y) {
            climberServo.setPosition(climberServo.getPosition()+0.1);
        }*/
    }

    @Override
    public abstract void loop();

    @Override
    public void stop(){
        leftDrive.close();
        rightDrive.close();

        leftDrive = null;
        rightDrive = null;

        leftDrivePort = 0;
        rightDrivePort = 0;

        if(telemetry.hasData()){
            telemetry.clearData();
            telemetry.addData("Time:", telemetry.getTimestamp());
        }
    }
}
