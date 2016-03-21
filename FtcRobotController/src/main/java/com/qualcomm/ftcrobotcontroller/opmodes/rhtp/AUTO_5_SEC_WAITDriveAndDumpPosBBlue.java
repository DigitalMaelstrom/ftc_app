package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTO_5_SEC_WAITDriveAndDumpPosBBlue extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        Thread.sleep(7000);
        MoveForward(2295);


        TurnRight(43);


        MoveForward(8900, -0.65);
        Thread.sleep(50);
        MoveBackward(ONEWHEELROTATION-200);
        TurnRight(42);

        MoveForward((ONEWHEELROTATION)+800);
        if (MoveForwardTilDistance(0.03,ONEWHEELROTATION+600)) {
            dump();
        }
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon, false);
    }



}


