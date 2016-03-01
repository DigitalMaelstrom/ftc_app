package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosARed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        StartAutoOp();

        //move forward
        MoveForward(4000);
        //Log.d("AutoDrive", "Just Moved Forward");
        //turn left
        TurnLeft(30);
        Thread.sleep(90);

        MoveForward(4890);

        TurnLeft(33);
        Thread.sleep(90);
        MoveForward(ONEWHEELROTATION*2);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000002;
            servotop.setPosition(dumpamount);
        }
        motorBack.setPower(0);
    }
}

