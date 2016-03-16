package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBBlue extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        MoveForward(2605);


        TurnRight(42);


        MoveForward(8900, -0.7);
        Thread.sleep(50);
        MoveBackward(ONEWHEELROTATION-100);
        TurnRight(41);

        MoveForward((ONEWHEELROTATION)+800);
        if (MoveForwardTilDistance(0.03,ONEWHEELROTATION+600)) {
            dump();
        }
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon, false);
    }



}


