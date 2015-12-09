package com.qualcomm.ftcrobotcontroller.opmodes;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * the Modern Robotics Gyro.
 *
 * The op mode assumes that the gyro sensor
 * is configured with a name of "gyro".
 *
 *
 *
 */


public class TESTGyro extends LinearOpMode {

    GyroSensor sensorGyro;
    DcMotor motorL;
    DcMotor motorR;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry.addData("Funct: ", "runOpMode()");

        int xVal, yVal, zVal = 0;
        int heading = 0;


        // write some device information (connection info, name and type)
        // to the log file.
        hardwareMap.logDevices();

        // get a reference to our GyroSensor object.
        sensorGyro = hardwareMap.gyroSensor.get("gyro");
        motorL = hardwareMap.dcMotor.get("motorL");
        motorR = hardwareMap.dcMotor.get("motorR");

        motorL.setDirection(DcMotor.Direction.REVERSE);

        //To Turn right -power right +power left
        //To turn left  +power left -power right

        // calibrate the gyro.
        sensorGyro.calibrate();

        // wait for the start button to be pressed.
        waitForStart();

        // make sure the gyro is calibrated.

        while (sensorGyro.isCalibrating()) {
            Thread.sleep(500);
        }
        //Turn Right 90
        while (opModeIsActive()) {
            heading = sensorGyro.getHeading();

            telemetry.addData("4. h", String.format("%03d", heading));


            turnRight(70, .15);
            Thread.sleep(3000);
           // turnLeft(70, .9);
            // get the heading info.
            // the Modern Robotics' gyro sensor keeps
            // track of the current heading for the Z axis only.


            //waitOneFullHardwareCycle();
        }

        motorL.setPower(0);
        motorR.setPower(0);
    }


    public void turnRight(int mag,double speed){
        int start =sensorGyro.getHeading();
        boolean ZeroFlag = start+ mag > 359;
        int End = start + mag % 360;


        //Start the motors turning right at speed.
        motorL.setPower(+speed);
        motorR.setPower(-speed);

        while (sensorGyro.getHeading()<End || ZeroFlag) {
            if (ZeroFlag) {
                while (sensorGyro.getHeading()>start){
                    //Motors should be turning right.
                }
                ZeroFlag=false;
            }

        }
        //Stop the motors.
        motorR.setPower(0);
        motorL.setPower(0);

    }
    //FIXME: this code does not function properly
    public void turnLeft(int degrees, double speed) {
        telemetry.addData("Funct: ", "turnLeft()");
        int currentHeading = sensorGyro.getHeading();
        sensorGyro.resetZAxisIntegrator();
        motorL.setPower(-speed);
        motorR.setPower(speed);

//        while (sensorGyro.getHeading() < 360 - degrees) {
//        }
//        while (sensorGyro.getHeading() > 360 - degrees) {
//        }
        while (360 - sensorGyro.getHeading() > degrees) {
            ;
        }
        motorL.setPower(0);
        motorR.setPower(0);

    }


}

