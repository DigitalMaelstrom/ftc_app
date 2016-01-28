package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Internet on 12/8/2015.
 */
public class AUTODriveAndDumpPosABlue extends AutoOpMode {


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

        waitForStart();
        initializeGyro();
        InitializeEncoders();
        servotop.setPosition(0.0);
        servomid.setPosition(0.5);
        servofront.setPosition(0.81);
        motorBack.setPower(0.1);


        MoveForward(4080);
        //Log.d("AutoDrive", "Just Moved Forward");

        TurnRight(37);
        Thread.sleep(90);

        MoveForward(4900);

        TurnRight(40);
        Thread.sleep(90);
        MoveForward(ONEWHEELROTATION * 2);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000002;
            servotop.setPosition(dumpamount);
        }
    }


}


