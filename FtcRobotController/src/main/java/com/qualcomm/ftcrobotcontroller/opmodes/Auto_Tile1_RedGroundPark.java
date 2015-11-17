package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


/**
 * Created by Internet on 10/12/2015.
 */
public class Auto_Tile1_RedGroundPark extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    ColorSensor colorSide;
    Servo Servofront;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorRight = hardwareMap.dcMotor.get("motor_right");
        colorSide = hardwareMap.colorSensor.get("color_front");
        Servofront = hardwareMap.servo.get("servo_front");


        boolean bEnabled = true;
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        Servofront.setPosition(0.5);

     while (true) {
         motorRight = hardwareMap.dcMotor.get("motor_right");
         motorLeft = hardwareMap.dcMotor.get("motor_left");
         colorSide = hardwareMap.colorSensor.get("color_front");
         Servofront = hardwareMap.servo.get("servo_front");


          colorSide.enableLed(false);
            float hsvValues[] = {0, 0, 0};
            Color.RGBToHSV(colorSide.red() * 8, colorSide.green() * 8, colorSide.blue() * 8, hsvValues);

         if(colorSide.red() >=2) {
             Servofront.setPosition(1);
         }
         else
         {
             Servofront.setPosition(0.5);
         }

            telemetry.addData("Clear", colorSide.alpha());
            telemetry.addData("Red  ", colorSide.red());
            telemetry.addData("Green", colorSide.green());
            telemetry.addData("Blue ", colorSide.blue());
            telemetry.addData("Hue", hsvValues[0]);



      /*  if(color1.red() >=1) {
            motorLeft.setPower(.5);

        }

         if(color1.blue() >=1){
             motorRight.setPower(.5);
        } */


         waitOneFullHardwareCycle();
         }






        /*waitForStart();

        ElapsedTime runtime = new ElapsedTime();

        motorRight.setPower(0.5);
        motorLeft.setPower(0.5);

        while (runtime.time()<4)
        {
            waitOneFullHardwareCycle();
        }
       */

    }

}
