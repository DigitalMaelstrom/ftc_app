package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBRed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        MoveForward(2085);
        Thread.sleep(90);

        telemetry.addData("Message", "I got here");


        TurnLeft(42);
        Thread.sleep(90);
        telemetry.addData("Message2", "I got past the Left");

        MoveForward(7785);

        TurnLeft(41);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION*3));

        dump();
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon,true);
    }



}


