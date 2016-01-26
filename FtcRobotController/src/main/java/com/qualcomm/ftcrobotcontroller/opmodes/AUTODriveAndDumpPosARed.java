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
public class AUTODriveAndDumpPosARed extends LinearOpMode {

    public static final int ONEWHEELROTATION = 1220;

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
        //set servos to starting positions
        servotop.setPosition(0.0);
        servomid.setPosition(0.5);


        //move forward
        MoveForward(3880);
        //Log.d("AutoDrive", "Just Moved Forward");
        //turn left
        TurnLeft(37);
        Thread.sleep(90);

        MoveForward(4950);

        TurnLeft(37);
        Thread.sleep(90);
        MoveForward(ONEWHEELROTATION*2);

        dumpamount=0;
        while (dumpamount<=0.89) {
            dumpamount=dumpamount+0.000002;
            servotop.setPosition(dumpamount);
        }
        Thread.sleep(10000);
        servotop.setPosition(0);
        MoveBackward(ONEWHEELROTATION / 2);
        TurnRight(80);
        MoveForward(ONEWHEELROTATION/2);
    }


    private void MoveBackward(int moveamount) {
        //MoveBackward
        encoderatstart=motorLeft.getCurrentPosition();
        motorLeft.setPower(-0.6);
        motorRight.setPower(-0.6);
        while(motorLeft.getCurrentPosition()<= moveamount+encoderatstart) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    private void MoveForward(int moveamount) {
        //Move Forward
        encoderatstart=motorLeft.getCurrentPosition();
        motorLeft.setPower(0.6);
        motorRight.setPower(0.6);
        while(motorLeft.getCurrentPosition()>= -moveamount+encoderatstart) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnRight(int degrees)throws InterruptedException {
        // Turn Right
        telemetry.addData("turn", "right");
        gyroSensor.resetZAxisIntegrator();
        telemetry.addData("Gyro2", gyroSensor.getHeading());
        Log.d("RightTurn", "Start Position: " + gyroSensor.getHeading());
        Thread.sleep(turndelay);
        motorLeft.setPower(.6);
        motorRight.setPower(-.6);
        while (gyroSensor.getHeading() <= degrees) {
            Thread.sleep(2);
            Log.d("RightTurn", "Position: "+gyroSensor.getHeading());
        }
        Log.d("RightTurn", "Finish Position: "+gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnLeft(int degrees) throws InterruptedException{
        // Turn Left
        telemetry.addData("turnn", "notright");
        gyroSensor.resetZAxisIntegrator();
        telemetry.addData("Gyro3", gyroSensor.getHeading());
        Log.d("LeftTurn", "Start Position: " + gyroSensor.getHeading());
        Thread.sleep(turndelay);
        motorLeft.setPower(-.6);
        motorRight.setPower(.6);
        while (gyroSensor.getHeading() <= 360-degrees) {
        }
        while (gyroSensor.getHeading() >= 360-degrees) {
            Thread.sleep(2);
            Log.d("LeftTurn", "Position: "+gyroSensor.getHeading());
        }
        Log.d("LeftTurn", "End Position: "+gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);

    }
}


