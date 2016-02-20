package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.ftcrobotcontroller.opmodes.rhtp.AutoOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class AUTODriveAndDumpPosARed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        StartAutoOp();

        //move forward
        MoveForward(3680);
        //Log.d("AutoDrive", "Just Moved Forward");
        //turn left
        TurnLeft(37);
        Thread.sleep(90);

        MoveForward(4950);

        TurnLeft(40);
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

