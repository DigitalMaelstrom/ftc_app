package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBRed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=false;
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
        MoveForward((ONEWHEELROTATION*3));

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000004;
            servotop.setPosition(dumpamount);
        }

        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon,true);
    }



}


