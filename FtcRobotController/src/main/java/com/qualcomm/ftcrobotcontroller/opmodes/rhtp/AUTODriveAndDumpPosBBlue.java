package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBBlue extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        StartAutoOpChimayo();
        MoveForward(2585);


        TurnRight(58);
        Thread.sleep(90);

        MoveForward(7800);

        TurnRight(50);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION*3));

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000003;
            servotop.setPosition(dumpamount);
        }
        motorBack.setPower(0);

    }




}


