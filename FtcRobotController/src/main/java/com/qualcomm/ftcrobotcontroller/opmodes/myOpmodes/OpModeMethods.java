package com.qualcomm.ftcrobotcontroller.opmodes.myOpmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by shanky on 11/14/15.
 */
public abstract class OpModeMethods extends OpMode {
    private DcMotorController dcMotorController = null;

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    //private DcMotor base;
    //private DcMotor arm;
    //private DcMotor;
    //private DcMotor;
    //private DcMotor;
    //private DcMotor;

    //private Servo leftFlipper;
    //private Servo rightFlipper;
    //private Servo;
    //private Servo;

    private final double MIN_MOTOR_POWER = 1.0;
    private final double MAX_MOTOR_POWER = -1.0;

    private final String LEFT_DRIVE_NAME = "leftDrive";
    private final String RIGHT_DRIVE_NAME = "rightDrive";

    private final Integer TICKS_PER_ROTATION = 1393;

    private int leftDrivePort= 0;
    private int rightDrivePort = 0;


    @Override
    public void init() {
        //I'm initializing the motors
        leftDrive = hardwareMap.dcMotor.get(LEFT_DRIVE_NAME);
        rightDrive = hardwareMap.dcMotor.get(RIGHT_DRIVE_NAME);

        //I'm initializing the dcMotorController so that I can
        //use only on object throughout the entire code
        //which makes convenient




        try {
            setDcMotorController();
            setTheMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            setTheDirection(null, 0);
            resetEncoders();
        }catch(HardwareException e){
            e.printStackTrace();
        }

    }

    //all getters methods that get the requested variable
    public DcMotor getLeftDrive(){
        return leftDrive;
    }

    public DcMotor getRightDrive() {
        return rightDrive;
    }

    public void setTheDirection(DcMotor.Direction direction, int port) throws HardwareException{
        if(direction != null){
            if(leftDrivePort == port){
                leftDrive.setDirection(direction);
            } else if(rightDrivePort == port){
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

    public DcMotorController setDcMotorController() throws HardwareException{
        if (leftDrive.getController().equals(rightDrive.getController())) {
            return leftDrive.getController();

        } else if(leftDrive.getController() != rightDrive.getController()){
            throw new HardwareException ("The drivetrain motors do not have the same controller" +
                            "or there was a problem in the code in using the dcMotorController" +
                            "variable. If this this exception pops up, let's stick with just using the " +
                            "motor names, eh?"
            );
        }

        return null;
    }

    //Used for setting the runMode for each of the motors
    public void setTheMode(DcMotorController.RunMode runMode) throws HardwareException {
        dcMotorController.setMotorChannelMode(leftDrivePort, runMode);
        dcMotorController.setMotorChannelMode(rightDrivePort, runMode);

        if(dcMotorController.getMotorChannelMode(leftDrivePort) != runMode
                && dcMotorController.getMotorChannelMode(rightDrivePort) != runMode){
            throw new HardwareException("setting the run mode was not possible");
        }
    }

    public void resetEncoders() throws HardwareException {
        dcMotorController.setMotorChannelMode(leftDrivePort,
                DcMotorController.RunMode.RESET_ENCODERS);

        dcMotorController.setMotorChannelMode(rightDrivePort,
                DcMotorController.RunMode.RESET_ENCODERS);

        if(!dcMotorController.isBusy(leftDrivePort)
                && !dcMotorController.isBusy(rightDrivePort)
                && dcMotorController.getMotorCurrentPosition(leftDrivePort) == 0
                && dcMotorController.getMotorCurrentPosition(rightDrivePort) == 0);
        else{
            throw new HardwareException("Resetting the encoders had encountered a problem");
        }
    }

    public void basictel(){
        telemetry.addData("Dcmotor contolller name: " , dcMotorController.getDeviceName());
        telemetry.addData("Dcmotor port number and name: " , leftDrivePort + ", "
                + leftDrive.getDeviceName());
        telemetry.addData(leftDrive.getDeviceName() + " connect info:" ,
                leftDrive.getConnectionInfo());
        telemetry.addData("" , "=============================");
        telemetry.addData("Dcmotor port number and name: " , rightDrivePort
                + rightDrive.getDeviceName());
        telemetry.addData(rightDrive.getDeviceName() + " connection info:" ,
                rightDrive.getConnectionInfo());
        telemetry.addData(leftDrive.getDeviceName() + " current position",
                leftDrive.getCurrentPosition());
        telemetry.addData(rightDrive.getDeviceName() + " current position",
                rightDrive.getCurrentPosition());
        telemetry.addData("Time:", telemetry.getTimestamp());
    }

    public boolean hasReached(){
        return Math.abs(dcMotorController.getMotorCurrentPosition(leftDrivePort)
                - dcMotorController.getMotorTargetPosition(leftDrivePort)) == 0
                && Math.abs(dcMotorController.getMotorCurrentPosition(rightDrivePort)
                + dcMotorController.getMotorTargetPosition(rightDrivePort)) == 0;
    }

    public void drive(int leftPos, int rightPos, double leftPow, double rightPow) throws HardwareException, IllegalArgumentException {
        //ElapsedTime time = new ElapsedTime();
        //time.startTime();
        if(leftPow > MAX_MOTOR_POWER || leftPow < MIN_MOTOR_POWER
                || rightPow > MAX_MOTOR_POWER || rightPow < MIN_MOTOR_POWER)
            throw new IllegalArgumentException();

        try{
            setTargetPos(leftPos, rightPos);
            setMotorPowers(leftPow, rightPow);
            if(hasReached()) {
                setMotorPowers(0.0, 0.0);
            }
        } catch(HardwareException e){
            e.printStackTrace();
        }

        if(!dcMotorController.isBusy(leftDrivePort)
                && !dcMotorController.isBusy(rightDrivePort)){
            throw new HardwareException("The drive() method did not work.");
        }
    }

    public void setTargetPos(int leftPos, int rightPos)throws HardwareException{
        dcMotorController.setMotorTargetPosition(leftDrivePort, leftPos);
        dcMotorController.setMotorTargetPosition(rightDrivePort, rightPos);

        if(dcMotorController.getMotorTargetPosition(leftDrivePort) != leftPos
                && dcMotorController.getMotorTargetPosition(rightDrivePort) != rightPos){
            throw new HardwareException("There was an error trying to assign the target position");
        }
    }

    public void setMotorPowers(double leftPow, double rightPow)throws HardwareException{
        setLeftMotorPower(leftPow);
        setRightMotorPower(rightPow);

        if(!dcMotorController.isBusy(leftDrivePort)
                && !dcMotorController.isBusy(rightDrivePort)
                && dcMotorController.getMotorPower(leftDrivePort) != leftPow
                && dcMotorController.getMotorPower(rightDrivePort) != rightPow){
            throw new HardwareException("There was an error in trying to set the power of the motors.");
        }
    }

    public boolean setLeftMotorPower(double leftPow){
        dcMotorController.setMotorPower(leftDrivePort, leftPow);

        return dcMotorController.isBusy(leftDrivePort)
                && dcMotorController.getMotorPower(leftDrivePort) == leftPow;
    }

    public boolean setRightMotorPower(double rightPow){
        dcMotorController.setMotorPower(rightDrivePort, rightPow);

        return dcMotorController.isBusy(rightDrivePort)
                   && dcMotorController.getMotorPower(rightDrivePort) == rightPow;
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

    @Override
    public abstract void loop();

    @Override
    public void stop(){
        leftDrive.close();
        rightDrive.close();
        dcMotorController.close();

        leftDrive = null;
        rightDrive = null;
        dcMotorController = null;

        leftDrivePort = 0;
        rightDrivePort = 0;

        if(telemetry.hasData()){
            telemetry.clearData();
            telemetry.addData("Time:", telemetry.getTimestamp());
        }
    }
}
