package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

public class AUTODriveAndDumpPosBBlue extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        boolean hitthatbeacon=true;
        StartAutoOp();
        MoveForward(2585);


        TurnRight(43);
        Thread.sleep(90);

        MoveForward(8200);

        TurnRight(39);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION)+1000);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000003;
            servotop.setPosition(dumpamount);
        }
        motorBack.setPower(0);
        hitTheBeacon(hitthatbeacon,false);
    }



}


