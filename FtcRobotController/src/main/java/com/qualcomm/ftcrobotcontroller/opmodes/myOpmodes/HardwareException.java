package com.qualcomm.ftcrobotcontroller.opmodes.myOpmodes;

/**
 * Created by shanky on 12/18/15.
 */
public class HardwareException extends Exception {
    public HardwareException() { super(); }
    public HardwareException(String message) { super(message); }
    public HardwareException(String message, Throwable cause) { super(message, cause); }
    public HardwareException(Throwable cause) { super(cause); }
}
