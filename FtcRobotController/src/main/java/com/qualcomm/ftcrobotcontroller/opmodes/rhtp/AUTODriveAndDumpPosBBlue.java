package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBBlue extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        MoveForward(2775);


        TurnRight(43);
        Thread.sleep(90);

        MoveForward(7800);

        TurnRight(34);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION)+900);

        dump();
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon,false);
    }



}


