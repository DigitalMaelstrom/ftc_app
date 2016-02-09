package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.ftcrobotcontroller.opmodes.rhtp.AutoOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Internet on 12/8/2015.
 */
public class AUTODriveAndDumpPosARed extends AutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        int time = 0;
        double dumpamount=0;
        servofront = hardwareMap.servo.get("servoFront");
        motorRight = hardwareMap.dcMotor.get("motorR");
        motorLeft = hardwareMap.dcMotor.get("motorL");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        gyroSensor = hardwareMap.gyroSensor.get("gyro");
        servotop=hardwareMap.servo.get("servoTop");
        servomid = hardwareMap.servo.get("servoMid");
        motorBack = hardwareMap.dcMotor.get("motorWheelie");
        gyroSensor.calibrate();
        motorBack.setPower(-0.5);
        initializeServos();
        waitForStart();
        initializeGyro();
        InitializeEncoders();

        initializeServos();
        motorBack.setPower(-0.5);
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

