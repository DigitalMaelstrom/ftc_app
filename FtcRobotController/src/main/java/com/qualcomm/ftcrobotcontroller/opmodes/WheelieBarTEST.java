package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
/**
 * Created by Internet on 11/12/2015.
 */
public class WheelieBarTEST extends OpMode {

    Servo servofront;

    @Override
    public void init() {
        servofront = hardwareMap.servo.get("servo_front");
        servofront.setPosition(0);
    }

    @Override
    public void loop() {
        //sets WheelieBar down
        if (gamepad1.left_bumper) {
            servofront.setPosition(0);
            //set WheelieBar Up
        } else if (gamepad1.right_bumper) {
            servofront.setPosition(.95);
        }

    }
}