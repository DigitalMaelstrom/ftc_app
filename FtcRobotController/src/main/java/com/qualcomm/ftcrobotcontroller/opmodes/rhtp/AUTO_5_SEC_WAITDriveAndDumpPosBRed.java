package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTO_5_SEC_WAITDriveAndDumpPosBRed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        Thread.sleep(7000);
        MoveForward(2285);


        TurnLeft(39);


        MoveForward(8600, -0.7);
        Thread.sleep(50);
        MoveBackward(ONEWHEELROTATION + 300);
        TurnLeft(38);

        MoveForward((ONEWHEELROTATION * 2));
        if (MoveForwardTilDistance(0.03,ONEWHEELROTATION+600)) {
            dump();
        }
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon, true);
    }



}


