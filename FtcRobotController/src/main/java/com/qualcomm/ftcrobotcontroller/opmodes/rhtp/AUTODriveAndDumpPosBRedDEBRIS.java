package com.qualcomm.ftcrobotcontroller.opmodes.rhtp;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Internet on 12/8/2015.
 */
public class AUTODriveAndDumpPosBRedDEBRIS extends AutoOpMode {

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

        MoveForward(3085);
        MoveBackward(500);

        TurnLeft(40);
        Thread.sleep(90);

        MoveForward(7500);
        MoveBackward(1000);

        TurnLeft(40);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION*3)+800);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000003;
            servotop.setPosition(dumpamount);
        }
        motorBack.setPower(0);

    }



}


