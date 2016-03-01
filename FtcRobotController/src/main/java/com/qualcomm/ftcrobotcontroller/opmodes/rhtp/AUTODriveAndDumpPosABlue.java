package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosABlue extends AutoOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        StartAutoOp();



        MoveForward(4380);
        //Log.d("AutoDrive", "Just Moved Forward");

        TurnRight(37);
        Thread.sleep(90);

        MoveForward(4900);

        TurnRight(37);
        Thread.sleep(90);
        MoveForward(ONEWHEELROTATION * 2);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000002;
            servotop.setPosition(dumpamount);
        }
        motorBack.setPower(0);

    }



}


