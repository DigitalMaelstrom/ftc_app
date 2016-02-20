package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Internet on 10/22/2015.
 */
public class AutoWhiteLine extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    ColorSensor colorGround;
    ColorSensor colorSide;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorRight = hardwareMap.dcMotor.get("motor_right");
        colorGround = hardwareMap.colorSensor.get("Color_ground");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();


        while (true) {
            motorRight = hardwareMap.dcMotor.get("motor_2");
            motorLeft = hardwareMap.dcMotor.get("motor_1");
            colorGround.enableLed(true);
            float hsvValues[] = {0, 0, 0};
            Color.RGBToHSV(colorGround.red() * 8, colorGround.green() * 8, colorGround.blue() * 8, hsvValues);

            telemetry.addData("Clear", colorGround.alpha());
            telemetry.addData("Red  ", colorGround.red());
            telemetry.addData("Green", colorGround.green());
            telemetry.addData("Blue ", colorGround.blue());
            telemetry.addData("Hue", hsvValues[0]);



      /*  if(color1.red() >=1) {
            motorLeft.setPower(.5);

        }

         if(color1.blue() >=1){
             motorRight.setPower(.5);
        } */


            waitOneFullHardwareCycle();
        }


    }
}