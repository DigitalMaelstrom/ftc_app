package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Internet on 12/8/2015.
 */
public class AUTODriveAndDumpTest extends LinearOpMode {

    DcMotorController motorController;
    Servo servotop;
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
        servotop=hardwareMap.servo.get("servoTop");
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

            motorController = hardwareMap.dcMotorController.get("Motor Controller 2");
            Thread.sleep(50);
            motorController.setMotorChannelMode(motorRight.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
            motorController.setMotorChannelMode(motorLeft.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
        telemetry.addData("pos", motorLeft.getCurrentPosition());
            Thread.sleep(40);
        motorController.setMotorChannelMode(motorRight.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            motorController.setMotorChannelMode(motorLeft.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            Thread.sleep(40);
       // motorRight.setTargetPosition(2240);
        //motorLeft.setTargetPosition(2240);
        motorLeft.setPower(1);
        motorRight.setPower(1);
            while(motorLeft.getCurrentPosition()>=-2240) {

                telemetry.addData("pos", motorLeft.getCurrentPosition());
            }
        telemetry.addData("done", "Done moving forward");
        motorLeft.setPower(0);
        motorRight.setPower(0);
            TurnRight(90);
        Thread.sleep(40);
            TurnLeft(180);
        telemetry.addData("Gyro", gyroSensor.getHeading());
        telemetry.addData("Version ", "1");


    }

    public void TurnRight(int degrees) {
        // Turn Right
        gyroSensor.resetZAxisIntegrator();
        motorLeft.setPower(1);
        motorRight.setPower(-1);
        while(gyroSensor.getHeading() <= degrees) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnLeft(int degrees) {
        // Turn Left
        gyroSensor.resetZAxisIntegrator();
        motorLeft.setPower(-1);
        motorRight.setPower(1);
        while (gyroSensor.getHeading() >= 360-degrees) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}


