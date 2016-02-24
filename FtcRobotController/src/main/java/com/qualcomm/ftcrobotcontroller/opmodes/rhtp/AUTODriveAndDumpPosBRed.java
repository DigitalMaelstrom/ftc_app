package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBRed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        StartAutoOpChimayo();
        MoveForward(2585);
        Thread.sleep(90);
        telemetry.addData("Message","I got here");


        TurnLeft(45);
        Thread.sleep(90);
        telemetry.addData("Message2","I got past the Left");

        MoveForward(6400);

        TurnLeft(45);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION*3)+400);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000003;
            servotop.setPosition(dumpamount);
        }
        motorBack.setPower(0);

    }



}


