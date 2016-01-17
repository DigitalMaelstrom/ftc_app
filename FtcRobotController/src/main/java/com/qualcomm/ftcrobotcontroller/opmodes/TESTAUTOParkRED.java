package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Internet on 12/8/2015.
 */
public class TESTAUTOParkRED extends LinearOpMode {

    DcMotorController motorController;
    //Servo servotop;
    DcMotor motorLeft;
    DcMotor motorRight;
    GyroSensor gyroSensor;
    int count=0;
    int xVal, yVal, zVal = 0;
    public int heading = 0;
    int encoderatstart=0;

    @Override
    public void runOpMode() throws InterruptedException {

        int time = 0;
        motorRight = hardwareMap.dcMotor.get("motorR");
        motorLeft = hardwareMap.dcMotor.get("motorL");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        gyroSensor = hardwareMap.gyroSensor.get("gyro");
        // servotop=hardwareMap.servo.get("servoTop");
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
        Thread.sleep(40);
        motorController.setMotorChannelMode(motorRight.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorController.setMotorChannelMode(motorLeft.getPortNumber(), DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        telemetry.addData("pos3", "run without encoders");
        Thread.sleep(40);
        // motorRight.setTargetPosition(2240);
        //motorLeft.setTargetPosition(2240);
        encoderatstart=motorLeft.getCurrentPosition();
        MoveForward(-1220);
        Thread.sleep(100);
      //  MoveForward(5000);
        Thread.sleep(50);
        telemetry.addData("done1", "Done moving forward2");
        TurnLeft(90);
        telemetry.addData("done2", "I TURNED");
        Thread.sleep(150);
        TurnRight(90);
        Thread.sleep(50);
        telemetry.addData("done4", "I TURNED2");
        telemetry.addData("done","DONE WITH EVERYTHINGGG");
        telemetry.addData("Gyro", gyroSensor.getHeading());
        telemetry.addData("Version ", "1");



    }

    public void TurnRight(int degrees)throws InterruptedException {
        // Turn Right
        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(50);
        motorLeft.setPower(1);
        motorRight.setPower(-1);
        while(gyroSensor.getHeading() <= degrees) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    public void TurnLeft(int degrees) throws InterruptedException{
        // Turn Left
        gyroSensor.resetZAxisIntegrator();
        Thread.sleep(50);
        motorLeft.setPower(-1);
        motorRight.setPower(1);
        while (gyroSensor.getHeading() <= 360-degrees) {
        }
        while (gyroSensor.getHeading() >= 360-degrees) {
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
    //1220 = full rotation
    public void MoveForward(int rotations) throws InterruptedException{
        //Move forward
        Thread.sleep(50);
        motorLeft.setPower(-1);
        motorRight.setPower(-1);
        while(motorLeft.getCurrentPosition()>=rotations+encoderatstart) {

        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }
}


