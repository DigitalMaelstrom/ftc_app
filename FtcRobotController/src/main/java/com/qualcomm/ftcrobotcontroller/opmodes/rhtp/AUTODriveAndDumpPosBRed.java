package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBRed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        MoveForward(2585);
        Thread.sleep(90);
        telemetry.addData("Message", "I got here");


        TurnLeft(44);
        Thread.sleep(90);
        telemetry.addData("Message2", "I got past the Left");

        MoveForward(6800);

        TurnLeft(41);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION*3)+800);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000004;
            servotop.setPosition(dumpamount);
        }
        motorBack.setPower(0);
        if (hitthatbeacon==true)
        {
            Thread.sleep(5000);
            //servotop.setPosition(0);
            MoveBackward(ONEWHEELROTATION/3);
            TurnRight(80);
            MoveBackward(ONEWHEELROTATION/2);
            MoveForwardTilWhite();
            BeaconPressRed();
            Thread.sleep(1000);
            MoveBackward(ONEWHEELROTATION/5);
        }
    }



}


