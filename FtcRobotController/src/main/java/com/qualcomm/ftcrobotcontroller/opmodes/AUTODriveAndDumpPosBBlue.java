package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Internet on 12/8/2015.
 */
public class AUTODriveAndDumpPosBBlue extends AutoOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        int time = 0;
        double dumpamount=0;
        motorRight = hardwareMap.dcMotor.get("motorR");
        motorLeft = hardwareMap.dcMotor.get("motorL");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        gyroSensor = hardwareMap.gyroSensor.get("gyro");
        servotop=hardwareMap.servo.get("servoTop");
        servomid = hardwareMap.servo.get("servoMid");

        gyroSensor.calibrate();

        waitForStart();
        initializeGyro();

        InitializeEncoders();

        servotop.setPosition(0.0);
        servomid.setPosition(0.5);



        MoveForward(2585);


        TurnRight(30);
        Thread.sleep(90);

        MoveForward(7000);

        TurnRight(30);
        Thread.sleep(90);
        MoveForward((ONEWHEELROTATION*2)+800);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000003;
            servotop.setPosition(dumpamount);
        }
        Thread.sleep(10000);
        servotop.setPosition(0);
        MoveBackward(ONEWHEELROTATION / 2);
        TurnRight(80);
        MoveBackward(ONEWHEELROTATION / 2);
    }




}


