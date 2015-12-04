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
        //To turn left  -power left +power right

        // calibrate the gyro.
        sensorGyro.calibrate();

        // wait for the start button to be pressed.
        waitForStart();

        // make sure the gyro is calibrated.

        while (sensorGyro.isCalibrating()) {
            Thread.sleep(50);
        }
                //Turn Right 90
              while (opModeIsActive()) {

                // if the A and B buttons are pressed, reset Z heading.
                if (gamepad1.a && gamepad1.b) {
                    // reset heading.
                    sensorGyro.resetZAxisIntegrator();
                }

                  turnRight(70, .9);
                  Thread.sleep(500);
                  turnLeft(70,.9);


                  // get the x, y, and z values (rate of change of angle).
                xVal = sensorGyro.rawX();
                yVal = sensorGyro.rawY();
                zVal = sensorGyro.rawZ();

                // get the heading info.
                // the Modern Robotics' gyro sensor keeps
                // track of the current heading for the Z axis only.
                heading = sensorGyro.getHeading();

                telemetry.addData("1. x", String.format("%03d", xVal));
                telemetry.addData("2. y", String.format("%03d", yVal));
                telemetry.addData("3. z", String.format("%03d", zVal));
                telemetry.addData("4. h", String.format("%03d", heading));


                //waitOneFullHardwareCycle();
            }

        motorL.setPower(0);
        motorR.setPower(0);
        }
        public  void turnRight(int degrees, double speed) {
            sensorGyro.resetZAxisIntegrator();
            motorL.setPower(speed);
            motorR.setPower(-speed);

        while (sensorGyro.getHeading() < degrees) {
        }
            motorL.setPower(0);
            motorR.setPower(0);
        }

    //FIXME: this code does not function properly
    public  void turnLeft(int degrees, double speed) {
        int currentHeading = sensorGyro.getHeading();
        sensorGyro.resetZAxisIntegrator();
        motorL.setPower(-speed);
        motorR.setPower(speed);

        while(Math.abs(360 - sensorGyro.getHeading()) < degrees) {
        }
        motorL.setPower(0);
        motorR.setPower(0);
    }


    }

