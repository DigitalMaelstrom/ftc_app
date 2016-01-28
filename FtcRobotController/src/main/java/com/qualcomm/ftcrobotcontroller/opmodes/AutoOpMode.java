package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Internet on 1/27/2016.
 */
public abstract class AutoOpMode extends LinearOpMode{
    public static final int ONEWHEELROTATION = 1220;

    DcMotorController motorController;
    Servo servotop;
    Servo servomid;
    Servo servofront;
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorBack;
    GyroSensor gyroSensor;
    double speed = .6;
    int count=0;
    int xVal, yVal, zVal = 0;
    int heading = 0;
    int turndelay=300;
    int encoderatstart=0;
    //end of variable initilization
    protected void initializeGyro() throws InterruptedException {
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
    }
    protected void InitializeEncoders() throws InterruptedException {
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
    }

    protected void MoveBackward(int moveamount) {
        encoderatstart=motorLeft.getCurrentPosition();
        motorLeft.setPower(-speed);
        motorRight.setPower(-speed);
        while(motorLeft.getCurrentPosition()<= moveamount+encoderatstart) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    protected void MoveForward(int moveamount) {
        encoderatstart=motorLeft.getCurrentPosition();
        motorLeft.setPower(speed);
        motorRight.setPower(speed);
        while(motorLeft.getCurrentPosition()>= -moveamount+encoderatstart) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnRight(int degrees)throws InterruptedException {
        // Turn Right
        Thread.sleep(turndelay);
        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(turndelay);
        Log.d("RightTurn", "Start Position: " + gyroSensor.getHeading());
        motorLeft.setPower(speed);
        motorRight.setPower(-speed);
        while (gyroSensor.getHeading() <= degrees) {
            Thread.sleep(20);
            Log.d("RightTurn", "Position: "+gyroSensor.getHeading());
        }
        Log.d("RightTurn", "Finish Position: "+gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnLeft(int degrees) throws InterruptedException{
        // Turn Left
        Thread.sleep(turndelay);
        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(turndelay);
        Log.d("LeftTurn", "Start Position: " + gyroSensor.getHeading());
        motorLeft.setPower(-speed);
        motorRight.setPower(speed);
        while (gyroSensor.getHeading() <= 360-degrees) {
        }
        while (gyroSensor.getHeading() >= 360-degrees) {
            Thread.sleep(20);
            Log.d("LeftTurn", "Position: "+gyroSensor.getHeading());
        }
        Log.d("LeftTurn", "End Position: "+gyroSensor.getHeading());
        motorLeft.setPower(0);
        motorRight.setPower(0);

    }
}
