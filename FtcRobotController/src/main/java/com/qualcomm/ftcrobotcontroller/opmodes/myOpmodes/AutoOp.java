package com.qualcomm.ftcrobotcontroller.opmodes.myOpmodes;

/**
 * Created by shanky on 12/19/15.
 */
public interface AutoOp{
    enum States{
        INIT_RESET,
        FIRST_STAGE,
        SECOND_RESET,
        SECOND_STAGE,
        THIRD_RESET,
        THIRD_STAGE,
        FOURTH_RESET,
        FOURTH_STAGE,
        FIFTH_RESET,
        FIFTH_STAGE,
        SIXTH_RESET,
        SIXTH_STAGE,
        SEVENTH_RESET,
        SEVENTH_STAGE,
        EIGHTH_RESET,
        EIGHTH_STAGE,
        NINTH_RESET,
        NINTH_STAGE,
        TENTH_RESET,
        TENTH_STAGE,
    }

    public void loop();
}
