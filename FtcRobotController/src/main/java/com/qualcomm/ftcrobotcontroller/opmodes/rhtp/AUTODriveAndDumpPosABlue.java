package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosABlue extends AutoOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();



        MoveForward(4380);
        //Log.d("AutoDrive", "Just Moved Forward");

        TurnRight(40);
        Thread.sleep(90);

        MoveForward(4900);

        TurnRight(40);
        Thread.sleep(90);
        MoveForward(ONEWHEELROTATION * 2);

        dump();
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon,false);
    }



}


