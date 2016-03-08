package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosARed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();

        //move forward
        MoveForward(3500);
        //Log.d("AutoDrive", "Just Moved Forward");
        //turn left
        TurnLeft(30);
        Thread.sleep(90);

        MoveForward(4890);

        TurnLeft(33);
        Thread.sleep(90);
        MoveForward(ONEWHEELROTATION*2);

        dump();
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon,true);
    }
}

