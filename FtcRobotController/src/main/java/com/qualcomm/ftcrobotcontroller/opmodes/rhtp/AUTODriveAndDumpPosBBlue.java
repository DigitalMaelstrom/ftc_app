package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.ftcrobotcontroller.opmodes.rhtp.AutoOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AUTODriveAndDumpPosBBlue extends AutoOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        StartAutoOp();
        MoveForward(1685);


        TurnRight(38);
        Thread.sleep(90);

        MoveForward(8300);

        TurnRight(37);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION*2));

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000003;
            servotop.setPosition(dumpamount);
        }
        motorBack.setPower(0);

    }




}


