package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBRedDistanceTest extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        MoveForward(2085);
        Thread.sleep(90);

        telemetry.addData("Message", "I got here");


        TurnLeft(48);
        Thread.sleep(90);
        telemetry.addData("Message2", "I got past the Left");

        MoveForward(7800);

        TurnLeft(45);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION * 2));
        /*if (MoveForwardTilDistance(.05, ONEWHEELROTATION)) {


            dump();
            motorBack.setPower(0);
            hitTheBeacon(hitthatbeacon, true);
        }*/
    }



}


