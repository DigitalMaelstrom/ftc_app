package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.robotcore.hardware.Servo;

public class AUTOPresentation extends AutoOpMode {

    Servo servofront;

    @Override
    public void runOpMode() throws InterruptedException {
        servofront = hardwareMap.servo.get("servoFront");

        StartAutoOp();

        servotop.setPosition(.89);

        motorBack.setPower(1);
        Thread.sleep(5000);
        motorBack.setPower(0);

        servofront.setPosition(.85);






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


