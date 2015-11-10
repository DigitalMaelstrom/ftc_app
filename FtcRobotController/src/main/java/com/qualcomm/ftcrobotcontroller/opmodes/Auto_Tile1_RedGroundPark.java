package com.qualcomm.ftcrobotcontroller.opmodes;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by Internet on 10/12/2015.
 */
public class Auto_Tile1_RedGroundPark extends LinearOpMode {
    DcMotor motorRight;
    DcMotor motorLeft;
    ColorSensor color1;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        color1 = hardwareMap.colorSensor.get("Color_2");

        boolean bEnabled = true;
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();


     while (true) {
         color1.enableLed(true);
         float hsvValues[] = {0, 0, 0};
         Color.RGBToHSV(color1.red() * 8, color1.green() * 8, color1.blue() * 8, hsvValues);

         telemetry.addData("Clear", color1.alpha());
         telemetry.addData("Red  ", color1.red());
         telemetry.addData("Green", color1.green());
         telemetry.addData("Blue ", color1.blue());
         telemetry.addData("Hue", hsvValues[0]);

        if(color1.red() >=1) {
            motorLeft.setPower(.5);

        }

         if(color1.blue() >=1){
             motorRight.setPower(.5);
         }



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
