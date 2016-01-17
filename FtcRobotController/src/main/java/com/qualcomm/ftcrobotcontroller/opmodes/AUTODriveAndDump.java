package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Internet on 12/8/2015.
 */
public class AUTODriveAndDump extends LinearOpMode {

    DcMotorController motorController;
    Servo servotop;
    Servo servomid;

    DcMotor motorLeft;
    DcMotor motorRight;
    GyroSensor gyroSensor;
    int count=0;
    int xVal, yVal, zVal = 0;
    public int heading = 0;
    int turndelay=1000;
    int encoderatstart=0;
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
        while(gyroSensor.isCalibrating()) {
            telemetry.addData("pos2", "run withou");
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

            motorController = hardwareMap.dcMotorController.get("Motor Controller 1");
            Thread.sleep(50);
            motorController.setMotorChannelMode(motorRight.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
            motorController.setMotorChannelMode(motorLeft.getPortNumber(), DcMotorController.RunMode.RESET_ENCODERS);
            telemetry.addData("pos", motorLeft.getCurrentPosition());
            Thread.sleep(400);
            motorController.setMotorChannelMode(motorRight.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            motorController.setMotorChannelMode(motorLeft.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            telemetry.addData("pos3", "run without encoders");
            Thread.sleep(40);
        //motorRight.setTargetPosition(2240);
        //motorLeft.setTargetPosition(2240);
        servotop.setPosition(0.0);
        servomid.setPosition(0.5);



        encoderatstart=motorLeft.getCurrentPosition();
        motorLeft.setPower(1);
        motorRight.setPower(1);
            while(motorLeft.getCurrentPosition()>=-4480+encoderatstart) {
            }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        TurnLeft(45);
        Thread.sleep(90);

        encoderatstart=motorLeft.getCurrentPosition();
        motorLeft.setPower(1);
        motorRight.setPower(1);
        while(motorLeft.getCurrentPosition()>= -5400+encoderatstart) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        TurnLeft(20);
        Thread.sleep(90);
        encoderatstart=motorLeft.getCurrentPosition();
        motorLeft.setPower(1);
        motorRight.setPower(1);
        while(motorLeft.getCurrentPosition()>= -1220+encoderatstart) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
        dumpamount=0;
        while (dumpamount<=0.85) {
            dumpamount=dumpamount+0.000003;
            servotop.setPosition(dumpamount);
        }
    }

    public void TurnRight(int degrees)throws InterruptedException {
        // Turn Right
        telemetry.addData("turn", "right");
        gyroSensor.resetZAxisIntegrator();
        telemetry.addData("Gyro2", gyroSensor.getHeading());
        Thread.sleep(turndelay);
        motorLeft.setPower(1);
        motorRight.setPower(-1);
        while (gyroSensor.getHeading() <= degrees) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnLeft(int degrees) throws InterruptedException{
        // Turn Left
        telemetry.addData("turnn", "notright");
        gyroSensor.resetZAxisIntegrator();
        telemetry.addData("Gyro3", gyroSensor.getHeading());
        Thread.sleep(turndelay);
        motorLeft.setPower(-1);
        motorRight.setPower(1);
        while (gyroSensor.getHeading() <= 360-degrees) {
        }
        while (gyroSensor.getHeading() >= 360-degrees) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

    }
}


