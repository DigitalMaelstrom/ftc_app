package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Internet on 12/8/2015.
 */
public class SolaBotGyroTest extends LinearOpMode {

    DcMotor motorLeft;
    DcMotor motorRight;
    GyroSensor gyroSensor;
    int count=0;
    int xVal, yVal, zVal = 0;
    public int heading = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        int time = 0;
        motorRight = hardwareMap.dcMotor.get("motorR");
        motorLeft = hardwareMap.dcMotor.get("motorL");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        gyroSensor = hardwareMap.gyroSensor.get("gyro");

        gyroSensor.calibrate();

        waitForStart();
        while(gyroSensor.isCalibrating()) {
            Thread.sleep(50);
        }
            heading = gyroSensor.getHeading();


            xVal = gyroSensor.rawX();
            yVal = gyroSensor.rawY();
            zVal = gyroSensor.rawZ();

            telemetry.addData("1. x", String.format("%03d", xVal));
            telemetry.addData("2. y", String.format("%03d", yVal));
            telemetry.addData("3. z", String.format("%03d", zVal));
            telemetry.addData("4. h", String.format("%03d", heading));




            TurnRight(90);

        telemetry.addData("Gyro", gyroSensor.getHeading());
        telemetry.addData("Version ", "1");


    }

    public void TurnRight(int degrees) {
        // Turn Right
        gyroSensor.resetZAxisIntegrator();
        motorLeft.setPower(.3);
        motorRight.setPower(1);
        while(gyroSensor.getHeading() <= degrees) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnLeft(int degrees) {
        // Turn Left
        gyroSensor.resetZAxisIntegrator();
        motorLeft.setPower(1);
        motorRight.setPower(.3);
        while (gyroSensor.getHeading() >= 360-degrees) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}


