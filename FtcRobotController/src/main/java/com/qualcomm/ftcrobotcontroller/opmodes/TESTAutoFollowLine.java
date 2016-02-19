package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Internet on 2/18/2016.
 */
public class TESTAutoFollowLine extends LinearOpMode {
    ColorSensor sensorRGB2;
    ColorSensor sensorRGB1 ;
   // Servo servoa;

    public void runOpMode() throws InterruptedException {
        sensorRGB1 = hardwareMap.colorSensor.get("colorBottom");
        sensorRGB1.enableLed(true);
       // servoa = hardwareMap.servo.get("servoColor");
        waitOneFullHardwareCycle();
        waitForStart();





        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
     //   servoa.setPosition(.6);
        while (true) {
            sensorRGB1.enableLed(true);
            Color.RGBToHSV(sensorRGB1.red() * 8, sensorRGB1.green() * 8, sensorRGB1.blue() * 8, hsvValues);
            telemetry.addData("Clear", sensorRGB1.alpha());
            telemetry.addData("Red  ", sensorRGB1.red());
            telemetry.addData("Green", sensorRGB1.green());
            telemetry.addData("Blue ", sensorRGB1.blue());
            telemetry.addData("Hue", hsvValues[0]);

      waitOneFullHardwareCycle();


      //.6  is middle pos for servo
      //.85 is left turn

//      if ((sensorRGB1.red() > sensorRGB1.blue()) && sensorRGB2.red() < sensorRGB2.blue()) {
//        servoa.setPosition(0.6);
//      }
//      if ((sensorRGB1.red() < sensorRGB1.blue()) && sensorRGB2.red() > sensorRGB2.blue()) {
//        servoa.setPosition(.3);
      //}
        }
    }
}

