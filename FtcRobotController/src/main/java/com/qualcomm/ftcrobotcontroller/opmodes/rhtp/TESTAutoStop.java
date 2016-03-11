package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class TESTAutoStop extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        StartAutoOp();
        MoveForwardTilDistance(.05, ONEWHEELROTATION);



    }


}


