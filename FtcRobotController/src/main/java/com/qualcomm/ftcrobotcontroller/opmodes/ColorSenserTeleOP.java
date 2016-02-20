package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Internet on 10/17/2015.
 */
public class ColorSenserTeleOP extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    //ColorSensor colorGround;
    ColorSensor colorSide;

    public ColorSenserTeleOP() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {


		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot and reversed.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
        motorRight = hardwareMap.dcMotor.get("motor_right");
        motorLeft = hardwareMap.dcMotor.get("motor_left");
        //colorGround = hardwareMap.colorSensor.get("color_ground");
        colorSide = hardwareMap.colorSensor.get("color_front");


        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        colorSide.enableLed(true);

    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {



        float hsvValues[] = {0, 0, 0};
        float hsvValues2[] = {0, 0, 0};
        colorSide.enableLed(true);
        //colorGround.enableLed(true);
        //Color.RGBToHSV(colorGround.red() * 8, colorGround.green() * 8, colorGround.blue() * 8, hsvValues);
        Color.RGBToHSV(colorSide.red() * 8, colorSide.green() * 8, colorSide.blue() * 8, hsvValues2);

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
        // 1 is full down()9*9
        // direction: left_stick_x ranges from -1 to 1, where -1 is full left
        // and 1 is full right
        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.left_stick_x;
        float right = throttle - direction;
        float left = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -1, 1);
        left = Range.clip(left, -1, 1);

        // scale the joystick value to make it easier to control
        // the robot more precisely at slower speeds.
        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        // write the values to the motors
        motorRight.setPower(right);
        motorLeft.setPower(left);







		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
        //telemetry.addData("Text", "*** Robot Data***");
        //telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
        //telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
        telemetry.addData("Motor - Left",  "left  pwr: " + String.format("%.2f", left));
        telemetry.addData("Motor - Right", "right pwr: " + String.format("%.2f", right));
        //telemetry.addData("Text1", "Sensor Ground");
        /*telemetry.addData("Ground - 5Clear", colorGround.alpha());
        telemetry.addData("Ground - 1Red  ", colorGround.red());
        telemetry.addData("Ground - 2Green", colorGround.green());
        telemetry.addData("Ground - 3Blue ", colorGround.blue());
        telemetry.addData("Ground - 4Hue", hsvValues[0]);
        //telemetry.addData("Text2" , "Sensor Side");*/
        telemetry.addData("Side - 5Clear", colorSide.alpha());
        telemetry.addData("Side - 1Red", colorSide.red());
        telemetry.addData("Side - 2Green", colorSide.green());
        telemetry.addData("Side - 3Blue", colorSide.blue());
        telemetry.addData("Side - 4Hue", hsvValues2[0]);




    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }


    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
