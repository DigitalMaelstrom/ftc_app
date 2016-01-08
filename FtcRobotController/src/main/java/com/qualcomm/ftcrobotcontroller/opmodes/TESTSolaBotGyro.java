package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Internet on 1/7/2016.
 */
public class TESTSolaBotGyro extends OpMode{

    DcMotor motorRight;
    DcMotor motorLeft;
    ColorSensor colorGround;
    GyroSensor gyroSensor;
    int count=0;
    int xVal, yVal, zVal = 0;
    public int heading = 0;


    @Override
    public void init() {

        motorRight = hardwareMap.dcMotor.get("motorR");
        motorLeft = hardwareMap.dcMotor.get("motorL");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        gyroSensor = hardwareMap.gyroSensor.get("gyro");

        gyroSensor.calibrate();

    }


    @Override
    public void loop() {
        TurnRight(90);


        heading = gyroSensor.getHeading();

        xVal = gyroSensor.rawX();
        yVal = gyroSensor.rawY();
        zVal = gyroSensor.rawZ();

        telemetry.addData("1. x", String.format("%03d", xVal));
        telemetry.addData("2. y", String.format("%03d", yVal));
        telemetry.addData("3. z", String.format("%03d", zVal));
        telemetry.addData("4. h", String.format("%03d", heading));


        telemetry.addData("Gyro", gyroSensor.getRotation());
        telemetry.addData("Version ", "1");
    }


        public void TurnRight(int degrees) {
            // Turn Right
            gyroSensor.resetZAxisIntegrator();
            if(heading<=degrees) {
                motorLeft.setPower(.3);
                motorRight.setPower(1);
            }
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }

        public void TurnLeft(int degrees) {
            // Turn Left
            gyroSensor.resetZAxisIntegrator();
            motorLeft.setPower(1);
            motorRight.setPower(.3);
            if (heading>=360-degrees) {
                motorLeft.setPower(1);
                motorRight.setPower(.3);
            }
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }



    @Override
    public void stop() {

    }


}

