package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBRed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        MoveForward(2285);


        TurnLeft(41);


        MoveForward(8600, -0.7);
        MoveBackward(ONEWHEELROTATION+500);
        TurnLeft(38);

        MoveForward((ONEWHEELROTATION * 2));
        if (MoveForwardTilDistance(0.03,ONEWHEELROTATION+600)) {
            dump();
        }
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon, true);
    }



}


